package com.leclowndu93150.create_armored_rails.ai;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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

        double range = Config.MOB_TARGET_RANGE.get();
        AABB searchBox = mob.getBoundingBox().inflate(range);
        List<CarriageContraptionEntity> trains = mob.level()
                .getEntitiesOfClass(CarriageContraptionEntity.class, searchBox, entity -> {
                    var carriage = entity.getCarriage();
                    return carriage != null && TrainHealthManager.get(carriage.train.id) != null;
                });

        if (trains.isEmpty()) return false;

        target = null;
        double closest = Double.MAX_VALUE;
        for (CarriageContraptionEntity train : trains) {
            double dist = mob.distanceToSqr(train);
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
        double range = Config.MOB_TARGET_RANGE.get();
        if (mob.distanceToSqr(target) > range * range) return false;
        var carriage = target.getCarriage();
        return carriage != null && TrainHealthManager.get(carriage.train.id) != null;
    }

    @Override
    public void tick() {
        if (target == null) return;
        mob.getLookControl().setLookAt(target);

        Vec3 targetPos = target.position();
        double distSq = mob.distanceToSqr(target);

        if (distSq > 9.0) {
            mob.getNavigation().moveTo(targetPos.x, targetPos.y, targetPos.z, 1.2);
        } else {
            mob.getNavigation().stop();
            mob.getMoveControl().setWantedPosition(targetPos.x, targetPos.y, targetPos.z, 1.2);
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
