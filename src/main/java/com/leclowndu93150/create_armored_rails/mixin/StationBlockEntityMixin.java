package com.leclowndu93150.create_armored_rails.mixin;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.registry.ModBlocks;
import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.trains.station.StationBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.UUID;

@Mixin(value = StationBlockEntity.class, remap = false)
public abstract class StationBlockEntityMixin {

    @Shadow
    abstract void exception(AssemblyException exception, int carriage);

    @Inject(method = "assemble", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/trains/station/StationBlockEntity;refreshAssemblyInfo()V", shift = At.Shift.AFTER), cancellable = true)
    private void armoredRails$requireHullAssembly(UUID playerUUID, CallbackInfo ci) {
        if (!Config.HULL_ASSEMBLY_MANDATORY.get()) return;

        StationBlockEntity self = (StationBlockEntity)(Object)this;
        Level level = self.getLevel();
        if (level == null) return;

        Map<BlockPos, BoundingBox> areas = StationBlockEntity.assemblyAreas.get(level);
        BoundingBox bb = areas.get(self.getBlockPos());
        if (bb == null) return;

        for (int x = bb.minX(); x <= bb.maxX(); x++) {
            for (int y = bb.minY(); y <= bb.maxY(); y++) {
                for (int z = bb.minZ(); z <= bb.maxZ(); z++) {
                    if (level.getBlockState(new BlockPos(x, y, z)).getBlock() == ModBlocks.HULL_BLOCK.get()) {
                        return;
                    }
                }
            }
        }

        exception(new AssemblyException(Component.translatable("train_assembly.create_armored_rails.no_hull")), -1);
        ci.cancel();
    }
}
