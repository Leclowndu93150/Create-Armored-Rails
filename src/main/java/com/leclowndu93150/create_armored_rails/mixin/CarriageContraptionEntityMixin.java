package com.leclowndu93150.create_armored_rails.mixin;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.compat.vs.VSCompat;
import com.leclowndu93150.create_armored_rails.compat.vs.VSTrainHelper;
import com.leclowndu93150.create_armored_rails.health.TrainHealthData;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Mixin(CarriageContraptionEntity.class)
public class CarriageContraptionEntityMixin {

    @Inject(method = "control", at = @At("HEAD"), cancellable = true, remap = false)
    private void armoredRails$blockCriticalControl(BlockPos controlsLocalPos, Collection<Integer> heldControls,
                                                    Player player, CallbackInfoReturnable<Boolean> cir) {
        CarriageContraptionEntity self = (CarriageContraptionEntity)(Object)this;
        Carriage carriage = self.getCarriage();
        if (carriage == null) return;

        TrainHealthData data = TrainHealthManager.get(carriage.train.id);
        if (data != null && data.isCriticalFailure(Config.CRITICAL_FAILURE_THRESHOLD.get())) {
            if (player instanceof ServerPlayer sp) {
                sp.displayClientMessage(
                        Component.translatable("message.create_armored_rails.critical_failure")
                                .withStyle(ChatFormatting.RED), true);
            }
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "startControlling", at = @At("HEAD"), cancellable = true, remap = false)
    private void armoredRails$blockCriticalStartControl(BlockPos controlsLocalPos, Player player,
                                                         CallbackInfoReturnable<Boolean> cir) {
        CarriageContraptionEntity self = (CarriageContraptionEntity)(Object)this;
        Carriage carriage = self.getCarriage();
        if (carriage == null) return;

        TrainHealthData data = TrainHealthManager.get(carriage.train.id);
        if (data != null && data.isCriticalFailure(Config.CRITICAL_FAILURE_THRESHOLD.get())) {
            if (player instanceof ServerPlayer sp) {
                sp.displayClientMessage(
                        Component.translatable("message.create_armored_rails.critical_failure")
                                .withStyle(ChatFormatting.RED), true);
            }
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void armoredRails$tickEffects(CallbackInfo ci) {
        CarriageContraptionEntity self = (CarriageContraptionEntity)(Object)this;
        Level level = self.level();

        Carriage carriage = self.getCarriage();
        if (carriage == null) return;

        TrainHealthData data = TrainHealthManager.get(carriage.train.id);
        if (data == null) return;

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            if (Config.COLLISION_DAMAGE_ENABLED.get() && self.tickCount % 10 == 0) {
                double speed = self.getDeltaMovement().length();
                if (speed > 0.1) {
                    java.util.function.Predicate<LivingEntity> collisionFilter =
                            e -> !self.hasPassenger(e) && !(e instanceof Player);
                    List<LivingEntity> entities;
                    if (VSCompat.isActive()) {
                        entities = VSTrainHelper.findEntitiesNearTrainWorldSpace(level, self, 0.5, collisionFilter::test);
                    } else {
                        AABB box = self.getBoundingBox().inflate(0.5);
                        entities = level.getEntitiesOfClass(LivingEntity.class, box, collisionFilter::test);
                    }
                    for (LivingEntity entity : entities) {
                        float collisionDmg = (float)(entity.getMaxHealth() * Config.COLLISION_DAMAGE_MULTIPLIER.get());
                        if (collisionDmg > 0) {
                            data.takeDamage(collisionDmg);
                        }
                    }
                }
            }

            if (Config.TEMPERATURE_DAMAGE_ENABLED.get()
                    && self.tickCount % Config.TEMPERATURE_DAMAGE_INTERVAL.get() == 0) {
                float biomeTemp = serverLevel.getBiome(self.blockPosition()).value().getBaseTemperature();
                float dmg = 0;
                if (biomeTemp < Config.TEMPERATURE_COLD_THRESHOLD.get().floatValue()) {
                    dmg = Config.TEMPERATURE_DAMAGE_AMOUNT.get().floatValue();
                } else if (biomeTemp > Config.TEMPERATURE_HOT_THRESHOLD.get().floatValue()) {
                    dmg = Config.TEMPERATURE_DAMAGE_AMOUNT.get().floatValue();
                }
                if (dmg > 0) {
                    if (serverLevel.isRaining() || serverLevel.isThundering()) {
                        dmg *= Config.TEMPERATURE_STORM_MULTIPLIER.get().floatValue();
                    }
                    data.takeDamage(dmg);
                }
            }
        }

        boolean critical = data.isCriticalFailure(Config.CRITICAL_FAILURE_THRESHOLD.get());
        if (!critical) return;

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            for (Entity passenger : new ArrayList<>(self.getPassengers())) {
                if (passenger instanceof ServerPlayer sp) {
                    sp.stopRiding();
                    sp.displayClientMessage(
                            Component.translatable("message.create_armored_rails.ejected_critical")
                                    .withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD), true);
                }
            }

            if (Config.CRITICAL_ALARM_ENABLED.get()) {
                int interval = Config.CRITICAL_ALARM_INTERVAL.get();
                if (self.tickCount % interval == 0) {
                    serverLevel.playSound(null, self.getX(), self.getY(), self.getZ(),
                            SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 2.0f, 0.5f);
                }
            }
        }

        if (level.isClientSide()) {
            Random random = new Random();
            if (random.nextInt(3) == 0) {
                double x = self.getX() + (random.nextDouble() - 0.5) * 2.0;
                double y = self.getY() + 1.5 + random.nextDouble() * 0.5;
                double z = self.getZ() + (random.nextDouble() - 0.5) * 2.0;
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 0.0, 0.07, 0.0);
            }
            if (random.nextInt(5) == 0) {
                double x = self.getX() + (random.nextDouble() - 0.5) * 1.5;
                double y = self.getY() + 1.0 + random.nextDouble();
                double z = self.getZ() + (random.nextDouble() - 0.5) * 1.5;
                level.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0, 0.04, 0.0);
            }
        }
    }
}
