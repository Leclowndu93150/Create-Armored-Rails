package com.leclowndu93150.create_armored_rails.compat.vs;

import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class VSTrainHelper {
    public static Vec3 getWorldPosition(CarriageContraptionEntity train) {
        return train.position();
    }

    public static double distanceToSqrVS(Entity from, CarriageContraptionEntity train) {
        return from.distanceToSqr(train);
    }

    public static List<CarriageContraptionEntity> findTrainsOnShips(Level level, Vec3 searchCenter, double range, Predicate<CarriageContraptionEntity> filter) {
        return Collections.emptyList();
    }

    public static List<LivingEntity> findEntitiesNearTrainWorldSpace(Level level, CarriageContraptionEntity train, double inflate, Predicate<LivingEntity> filter) {
        return Collections.emptyList();
    }
}
