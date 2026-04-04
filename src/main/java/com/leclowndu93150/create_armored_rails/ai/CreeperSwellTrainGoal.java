package com.leclowndu93150.create_armored_rails.ai;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.compat.vs.VSCompat;
import com.leclowndu93150.create_armored_rails.compat.vs.VSTrainHelper;
import com.leclowndu93150.create_armored_rails.health.TrainHealthData;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CreeperSwellTrainGoal extends Goal {
    private final Creeper creeper;
    private CarriageContraptionEntity target;

    public CreeperSwellTrainGoal(Creeper creeper) {
        this.creeper = creeper;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        double range = Config.BASE_MOB_RANGE.get() + 5;
        AABB searchBox = creeper.getBoundingBox().inflate(range);
        java.util.function.Predicate<CarriageContraptionEntity> filter = entity -> {
            var carriage = entity.getCarriage();
            return carriage != null && TrainHealthManager.get(carriage.train.id) != null;
        };
        List<CarriageContraptionEntity> trains = new ArrayList<>(creeper.level()
                .getEntitiesOfClass(CarriageContraptionEntity.class, searchBox, filter::test));
        if (VSCompat.isActive()) {
            trains.addAll(VSTrainHelper.findTrainsOnShips(creeper.level(), creeper.position(), range, filter::test));
        }

        target = null;
        double closest = Double.MAX_VALUE;
        for (CarriageContraptionEntity train : trains) {
            double dist = VSCompat.isActive() ? VSTrainHelper.distanceToSqrVS(creeper, train) : creeper.distanceToSqr(train);
            if (dist < closest) {
                closest = dist;
                target = train;
            }
        }
        return target != null && closest < 9.0;
    }

    @Override
    public boolean canContinueToUse() {
        if (target == null || target.isRemoved()) return false;
        double dist = VSCompat.isActive() ? VSTrainHelper.distanceToSqrVS(creeper, target) : creeper.distanceToSqr(target);
        return dist < 25.0;
    }

    @Override
    public void tick() {
        if (target == null) return;
        Vec3 targetPos = VSCompat.isActive() ? VSTrainHelper.getWorldPosition(target) : target.position();
        double dist = creeper.getBoundingBox().distanceToSqr(targetPos);
        if (dist < 6.25) {
            creeper.setSwellDir(1);
        } else {
            creeper.setSwellDir(-1);
        }
    }

    @Override
    public void stop() {
        target = null;
        creeper.setSwellDir(-1);
    }
}
