package com.leclowndu93150.create_armored_rails.network;

import com.leclowndu93150.create_armored_rails.client.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenHullOnContraptionPacket {
    private final int entityId;
    private final BlockPos localPos;
    private final CompoundTag inventoryNBT;
    private final int containerId;

    public OpenHullOnContraptionPacket(int entityId, BlockPos localPos, CompoundTag inventoryNBT, int containerId) {
        this.entityId = entityId;
        this.localPos = localPos;
        this.inventoryNBT = inventoryNBT;
        this.containerId = containerId;
    }

    public static void encode(OpenHullOnContraptionPacket pkt, FriendlyByteBuf buf) {
        buf.writeInt(pkt.entityId);
        buf.writeBlockPos(pkt.localPos);
        buf.writeNbt(pkt.inventoryNBT);
        buf.writeInt(pkt.containerId);
    }

    public static OpenHullOnContraptionPacket decode(FriendlyByteBuf buf) {
        return new OpenHullOnContraptionPacket(buf.readInt(), buf.readBlockPos(), buf.readNbt(), buf.readInt());
    }

    public static void handle(OpenHullOnContraptionPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    ClientPacketHandler.handleOpenHull(pkt.entityId, pkt.localPos, pkt.inventoryNBT, pkt.containerId));
        });
        ctx.get().setPacketHandled(true);
    }
}
