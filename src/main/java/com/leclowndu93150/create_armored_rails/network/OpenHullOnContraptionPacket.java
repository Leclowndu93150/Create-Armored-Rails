package com.leclowndu93150.create_armored_rails.network;

import com.leclowndu93150.create_armored_rails.block.HullMenu;
import com.leclowndu93150.create_armored_rails.client.HullScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.ItemStackHandler;
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
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null) return;
            ItemStackHandler handler = new ItemStackHandler(HullMenu.HULL_SLOT_COUNT);
            handler.deserializeNBT(pkt.inventoryNBT);
            Inventory playerInv = mc.player.getInventory();
            HullMenu menu = new HullMenu(pkt.containerId, playerInv, handler, pkt.entityId, pkt.localPos);
            mc.player.containerMenu = menu;
            mc.setScreen(new HullScreen(menu, playerInv, Component.translatable("block.create_armored_rails.hull")));
        });
        ctx.get().setPacketHandled(true);
    }
}
