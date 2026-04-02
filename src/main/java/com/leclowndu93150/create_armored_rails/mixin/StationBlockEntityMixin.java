package com.leclowndu93150.create_armored_rails.mixin;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.registry.ModBlocks;
import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.station.StationBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(value = StationBlockEntity.class, remap = false)
public abstract class StationBlockEntityMixin {

    @Shadow
    abstract void exception(AssemblyException exception, int carriage);

    @Unique
    private boolean armoredRails$foundHull = false;

    @Inject(method = "assemble", at = @At("HEAD"))
    private void armoredRails$resetHullCheck(UUID playerUUID, CallbackInfo ci) {
        armoredRails$foundHull = false;
    }

    @Redirect(method = "assemble",
            at = @At(value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/CarriageContraption;assemble(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean armoredRails$scanHullDuringAssemble(CarriageContraption contraption, Level level, BlockPos pos) throws AssemblyException {
        boolean result = contraption.assemble(level, pos);
        if (result && !armoredRails$foundHull) {
            for (StructureBlockInfo info : contraption.getBlocks().values()) {
                if (info.state().getBlock() == ModBlocks.HULL_BLOCK.get()) {
                    armoredRails$foundHull = true;
                    break;
                }
            }
        }
        return result;
    }

    @Inject(method = "assemble",
            at = @At(value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/CarriageContraption;removeBlocksFromWorld(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V",
                    ordinal = 0),
            cancellable = true)
    private void armoredRails$enforceHullBeforeRemoval(UUID playerUUID, CallbackInfo ci) {
        if (!Config.HULL_ASSEMBLY_MANDATORY.get()) return;
        if (armoredRails$foundHull) return;

        exception(new AssemblyException(Component.translatable("train_assembly.create_armored_rails.no_hull")), -1);
        ci.cancel();
    }
}
