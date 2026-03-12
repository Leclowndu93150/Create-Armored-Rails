package com.leclowndu93150.create_armored_rails.mixin;

import com.leclowndu93150.create_armored_rails.Config;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
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
    private void armoredRails$criticalFailureEffects(CallbackInfo ci) {
        CarriageContraptionEntity self = (CarriageContraptionEntity)(Object)this;
        Level level = self.level();

        Carriage carriage = self.getCarriage();
        if (carriage == null) return;

        TrainHealthData data = TrainHealthManager.get(carriage.train.id);
        if (data == null || !data.isCriticalFailure(Config.CRITICAL_FAILURE_THRESHOLD.get())) return;

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
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
