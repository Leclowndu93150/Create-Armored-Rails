package com.leclowndu93150.create_armored_rails.mixin;

import com.leclowndu93150.create_armored_rails.behaviour.ContraptionMenuTracker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerMenu.class)
public class ContainerStillValidMixin {
    @Inject(method = "stillValid(Lnet/minecraft/world/inventory/ContainerLevelAccess;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/block/Block;)Z",
            at = @At("HEAD"), cancellable = true)
    private static void armoredRails$bypassContraptionCheck(ContainerLevelAccess access, Player player, Block block,
                                                             CallbackInfoReturnable<Boolean> cir) {
        if (ContraptionMenuTracker.isOnContraption(player)) {
            cir.setReturnValue(true);
        }
    }
}
