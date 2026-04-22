package com.leclowndu93150.create_armored_rails.compat.jade;

import com.leclowndu93150.create_armored_rails.block.HullBlock;
import com.leclowndu93150.create_armored_rails.block.HullBlockEntity;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import java.util.List;

@WailaPlugin
public class ArmoredRailsJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(HullBlockComponentProvider.INSTANCE, HullBlockEntity.class);
        registration.registerEntityDataProvider(TrainEntityComponentProvider.INSTANCE, CarriageContraptionEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(HullBlockComponentProvider.INSTANCE, HullBlock.class);
        registration.registerEntityComponent(TrainEntityComponentProvider.INSTANCE, CarriageContraptionEntity.class);

        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null) return accessor;
            if (accessor != null && accessor.getTarget() instanceof CarriageContraptionEntity) {
                return accessor;
            }

            Vec3 eye = mc.player.getEyePosition(mc.getFrameTime());
            Vec3 look = mc.player.getViewVector(mc.getFrameTime());
            double reach = mc.player.getBlockReach();
            Vec3 end = eye.add(look.scale(reach));
            AABB searchBox = mc.player.getBoundingBox().expandTowards(look.scale(reach)).inflate(1);

            List<CarriageContraptionEntity> trains = mc.level.getEntitiesOfClass(
                    CarriageContraptionEntity.class, searchBox);

            CarriageContraptionEntity closest = null;
            double closestDist = Double.MAX_VALUE;

            for (CarriageContraptionEntity cce : trains) {
                AABB bb = cce.getBoundingBox();
                if (bb.getSize() < 0.01) continue;
                var clip = bb.clip(eye, end);
                if (clip.isEmpty()) continue;
                double dist = clip.get().distanceToSqr(eye);
                if (dist < closestDist) {
                    closestDist = dist;
                    closest = cce;
                }
            }

            if (closest == null) return accessor;

            Vec3 hitPos = closest.getBoundingBox().clip(eye, end).orElse(eye);
            EntityHitResult entityHit = new EntityHitResult(closest, hitPos);
            return registration.entityAccessor()
                    .level(mc.level)
                    .player(mc.player)
                    .serverConnected(registration.isServerConnected())
                    .showDetails(registration.isShowDetailsPressed())
                    .hit(entityHit)
                    .entity(closest)
                    .build();
        });
    }
}
