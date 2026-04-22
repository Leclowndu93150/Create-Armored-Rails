package com.leclowndu93150.create_armored_rails.compat.vs;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.create_interactive.CreateInteractiveUtil;
import org.valkyrienskies.create_interactive.mixinducks.AbstractContraptionEntityDuck;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class VSTrainHelper {

    public static Vec3 getWorldPosition(CarriageContraptionEntity train) {
        Ship ship = getShipForEntity(train);
        if (ship != null) {
            return VSGameUtilsKt.toWorldCoordinates(ship, train.position());
        }
        return train.position();
    }

    public static double distanceToSqrVS(Entity from, CarriageContraptionEntity train) {
        Vec3 fromPos = getWorldPos(from);
        Vec3 trainPos = getWorldPosition(train);
        return fromPos.distanceToSqr(trainPos);
    }

    public static List<CarriageContraptionEntity> findTrainsOnShips(Level level, Vec3 searchCenter, double range, Predicate<CarriageContraptionEntity> filter) {
        List<CarriageContraptionEntity> result = new ArrayList<>();
        double rangeSq = range * range;

        Map<Long, WeakReference<AbstractContraptionEntity>> shipMap =
                CreateInteractiveUtil.INSTANCE.getShipIdToContraptionEntityServer();

        for (WeakReference<AbstractContraptionEntity> ref : shipMap.values()) {
            AbstractContraptionEntity entity = ref.get();
            if (!(entity instanceof CarriageContraptionEntity train)) continue;
            if (train.isRemoved()) continue;

            Vec3 worldPos = getWorldPosition(train);
            if (searchCenter.distanceToSqr(worldPos) > rangeSq) continue;

            if (filter.test(train)) {
                result.add(train);
            }
        }
        return result;
    }

    public static List<LivingEntity> findEntitiesNearTrainWorldSpace(Level level, CarriageContraptionEntity train, double inflate, Predicate<LivingEntity> filter) {
        Vec3 worldPos = getWorldPosition(train);
        AABB worldBox = new AABB(
                worldPos.x - inflate, worldPos.y - inflate, worldPos.z - inflate,
                worldPos.x + inflate, worldPos.y + inflate, worldPos.z + inflate
        );
        return level.getEntitiesOfClass(LivingEntity.class, worldBox, filter);
    }

    private static Ship getShipForEntity(AbstractContraptionEntity entity) {
        if (entity instanceof AbstractContraptionEntityDuck duck) {
            Long shipId = duck.ci$getShadowShipId();
            if (shipId != null) {
                return VSGameUtilsKt.getShipManagingPos(entity.level(),
                        entity.position().x, entity.position().y, entity.position().z);
            }
        }
        return null;
    }

    private static Vec3 getWorldPos(Entity entity) {
        Ship ship = VSGameUtilsKt.getShipManagingPos(entity.level(),
                entity.position().x, entity.position().y, entity.position().z);
        if (ship != null) {
            return VSGameUtilsKt.toWorldCoordinates(ship, entity.position());
        }
        return entity.position();
    }
}
