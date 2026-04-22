package com.leclowndu93150.create_armored_rails.mixin;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.health.TrainHealthData;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.simibubi.create.content.trains.entity.Train;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Train.class)
public class TrainMixin {
    @Shadow(remap = false)
    public UUID id;

    @Inject(method = "maxSpeed", at = @At("RETURN"), cancellable = true, remap = false)
    private void armoredRails$applySpeedMalus(CallbackInfoReturnable<Float> cir) {
        TrainHealthData data = TrainHealthManager.get(id);
        if (data != null && data.getMaxHP() > 0) {
            cir.setReturnValue(cir.getReturnValue() * data.getSpeedMultiplier());
        }
    }

    @Inject(method = "maxTurnSpeed", at = @At("RETURN"), cancellable = true, remap = false)
    private void armoredRails$applyTurnSpeedMalus(CallbackInfoReturnable<Float> cir) {
        TrainHealthData data = TrainHealthManager.get(id);
        if (data != null && data.getMaxHP() > 0) {
            cir.setReturnValue(cir.getReturnValue() * data.getSpeedMultiplier());
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void armoredRails$tickHealthCheck(Level level, CallbackInfo ci) {
        if (level.isClientSide()) return;
        if (TrainHealthManager.isTracked(id)) {
            if (TrainHealthManager.isChecked(id)) {
                if (level.getGameTime() % 100 != 0) return;
                TrainHealthManager.clearChecked(id);
            } else {
                return;
            }
        }

        Train self = (Train)(Object)this;
        if (TrainHealthManager.hasHullBlocks(self)) {
            TrainHealthManager.recalculateFromContraption(self);
        } else {
            TrainHealthManager.ensureBaseHealth(self);
        }
    }
}
