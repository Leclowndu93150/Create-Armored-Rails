package com.leclowndu93150.create_armored_rails.mixin;

import com.leclowndu93150.create_armored_rails.behaviour.ContraptionMenuTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseContainerBlockEntity.class)
public class ContainerBlockEntityValidMixin {
    @Inject(method = "stillValid", at = @At("HEAD"), cancellable = true)
    private void armoredRails$bypassBlockEntityCheck(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (ContraptionMenuTracker.isOnContraption(player)) {
            cir.setReturnValue(true);
        }
    }
}
