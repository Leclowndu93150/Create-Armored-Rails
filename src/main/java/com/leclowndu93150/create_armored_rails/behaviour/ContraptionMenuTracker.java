package com.leclowndu93150.create_armored_rails.behaviour;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ContraptionMenuTracker {
    private static final Map<UUID, ContraptionMenuContext> ACTIVE_MENUS = new ConcurrentHashMap<>();

    public static void mark(Player player) {
        ACTIVE_MENUS.put(player.getUUID(), new ContraptionMenuContext(-1, null, null));
    }

    public static void markWithContext(Player player, int entityId, BlockPos localPos, BlockEntity tempBlockEntity) {
        ACTIVE_MENUS.put(player.getUUID(), new ContraptionMenuContext(entityId, localPos, tempBlockEntity));
    }

    public static void clear(Player player) {
        ContraptionMenuContext ctx = ACTIVE_MENUS.remove(player.getUUID());
        if (ctx != null && ctx.tempBlockEntity != null) {
            syncBack(player, ctx);
        }
    }

    public static boolean isOnContraption(Player player) {
        return ACTIVE_MENUS.containsKey(player.getUUID());
    }

    private static void syncBack(Player player, ContraptionMenuContext ctx) {
        Entity entity = player.level().getEntity(ctx.entityId);
        if (!(entity instanceof AbstractContraptionEntity ace)) return;

        StructureBlockInfo oldInfo = ace.getContraption().getBlocks().get(ctx.localPos);
        if (oldInfo == null) return;

        CompoundTag newNbt = ctx.tempBlockEntity.saveWithFullMetadata();

        ace.getContraption().getBlocks().put(ctx.localPos,
                new StructureBlockInfo(oldInfo.pos(), oldInfo.state(), newNbt));
    }

    private record ContraptionMenuContext(int entityId, BlockPos localPos, BlockEntity tempBlockEntity) {}
}
