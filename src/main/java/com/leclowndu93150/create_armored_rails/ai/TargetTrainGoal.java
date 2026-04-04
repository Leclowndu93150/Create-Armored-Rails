package com.leclowndu93150.create_armored_rails.ai;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.compat.vs.VSCompat;
import com.leclowndu93150.create_armored_rails.compat.vs.VSTrainHelper;
import com.leclowndu93150.create_armored_rails.health.TrainHealthData;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class TargetTrainGoal extends Goal {
    private final Mob mob;
    private CarriageContraptionEntity target;
    private int attackCooldown;
    private int searchCooldown;

    public TargetTrainGoal(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (searchCooldown > 0) {
            searchCooldown--;
            return false;
        }
        searchCooldown = 20;

        if (mob.getTarget() != null) return false;

        double maxSearchRange = Config.BASE_MOB_RANGE.get() + 10;
        AABB searchBox = mob.getBoundingBox().inflate(maxSearchRange);
        java.util.function.Predicate<CarriageContraptionEntity> filter = entity -> {
            var carriage = entity.getCarriage();
            return carriage != null && TrainHealthManager.get(carriage.train.id) != null;
        };
        List<CarriageContraptionEntity> trains = new ArrayList<>(mob.level()
                .getEntitiesOfClass(CarriageContraptionEntity.class, searchBox, filter::test));
        if (VSCompat.isActive()) {
            trains.addAll(VSTrainHelper.findTrainsOnShips(mob.level(), mob.position(), maxSearchRange, filter::test));
        }

        if (trains.isEmpty()) return false;

        double proximityRange = Config.MOB_PROXIMITY_RANGE.get();

        target = null;
        double closest = Double.MAX_VALUE;
        for (CarriageContraptionEntity train : trains) {
            var carriage = train.getCarriage();
            if (carriage == null) continue;

            TrainHealthData data = TrainHealthManager.get(carriage.train.id);
            if (data == null) continue;

            double effectiveRange = Config.BASE_MOB_RANGE.get() + data.getMobRangeModifier();
            if (effectiveRange <= 0) continue;

            double dist = VSCompat.isActive() ? VSTrainHelper.distanceToSqrVS(mob, train) : mob.distanceToSqr(train);
            if (dist > effectiveRange * effectiveRange) continue;

            if (proximityRange > 0) {
                boolean playerNearby = false;
                for (Player p : mob.level().players()) {
                    double playerDist = VSCompat.isActive() ? VSTrainHelper.distanceToSqrVS(p, train) : p.distanceToSqr(train);
                    if (playerDist < proximityRange * proximityRange) {
                        playerNearby = true;
                        break;
                    }
                }
                if (!playerNearby) continue;
            }

            if (dist < closest) {
                closest = dist;
                target = train;
            }
        }
        return target != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (target == null || target.isRemoved()) return false;
        var carriage = target.getCarriage();
        if (carriage == null) return false;
        TrainHealthData data = TrainHealthManager.get(carriage.train.id);
        if (data == null) return false;
        double effectiveRange = Config.BASE_MOB_RANGE.get() + data.getMobRangeModifier();
        double dist = VSCompat.isActive() ? VSTrainHelper.distanceToSqrVS(mob, target) : mob.distanceToSqr(target);
        return dist <= effectiveRange * effectiveRange * 1.5;
    }

    @Override
    public void tick() {
        if (target == null) return;

        Vec3 targetPos = VSCompat.isActive() ? VSTrainHelper.getWorldPosition(target) : target.position();
        mob.getLookControl().setLookAt(targetPos.x, targetPos.y, targetPos.z);

        double distSq = VSCompat.isActive() ? VSTrainHelper.distanceToSqrVS(mob, target) : mob.distanceToSqr(target);

        double bbDist = mob.getBoundingBox().distanceToSqr(targetPos);

        if (bbDist > 4.0) {
            mob.getNavigation().moveTo(targetPos.x, targetPos.y, targetPos.z, 1.2);
            if (distSq < 25.0) {
                mob.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 1.2);
            }
        } else {
            mob.getNavigation().stop();
            if (attackCooldown <= 0) {
                target.hurt(mob.damageSources().mobAttack(mob),
                        (float) mob.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
                attackCooldown = 20;
            }
        }
        if (attackCooldown > 0) attackCooldown--;
    }

    @Override
    public void start() {
        attackCooldown = 0;
    }

    @Override
    public void stop() {
        target = null;
        mob.getNavigation().stop();
    }
}
