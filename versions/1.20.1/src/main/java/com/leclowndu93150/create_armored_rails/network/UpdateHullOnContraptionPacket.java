package com.leclowndu93150.create_armored_rails.network;

import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.leclowndu93150.create_armored_rails.health.TrainHealthSavedData;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateHullOnContraptionPacket {
    private final int entityId;
    private final BlockPos localPos;
    private final CompoundTag inventoryNBT;

    public UpdateHullOnContraptionPacket(int entityId, BlockPos localPos, CompoundTag inventoryNBT) {
        this.entityId = entityId;
        this.localPos = localPos;
        this.inventoryNBT = inventoryNBT;
    }

    public static void encode(UpdateHullOnContraptionPacket pkt, FriendlyByteBuf buf) {
        buf.writeInt(pkt.entityId);
        buf.writeBlockPos(pkt.localPos);
        buf.writeNbt(pkt.inventoryNBT);
    }

    public static UpdateHullOnContraptionPacket decode(FriendlyByteBuf buf) {
        return new UpdateHullOnContraptionPacket(buf.readInt(), buf.readBlockPos(), buf.readNbt());
    }

    public static void handle(UpdateHullOnContraptionPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;
            Entity entity = player.level().getEntity(pkt.entityId);
            if (!(entity instanceof AbstractContraptionEntity ace)) return;

            StructureBlockInfo oldInfo = ace.getContraption().getBlocks().get(pkt.localPos);
            if (oldInfo == null) return;

            CompoundTag newNbt = oldInfo.nbt() != null ? oldInfo.nbt().copy() : new CompoundTag();
            newNbt.put("Inventory", pkt.inventoryNBT);

            StructureBlockInfo newInfo = new StructureBlockInfo(oldInfo.pos(), oldInfo.state(), newNbt);
            ace.getContraption().getBlocks().put(pkt.localPos, newInfo);

            if (entity instanceof CarriageContraptionEntity cce) {
                var carriage = cce.getCarriage();
                if (carriage != null) {
                    TrainHealthManager.recalculateFromContraption(carriage.train);
                    TrainHealthSavedData.markDirty(player.server);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
