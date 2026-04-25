package com.leclowndu93150.create_armored_rails.client;

import com.leclowndu93150.create_armored_rails.block.HullMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ClientPacketHandler {

    public static void handleOpenHull(int entityId, BlockPos localPos, CompoundTag inventoryNBT, int containerId) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        ItemStackHandler handler = HullMenu.loadInventory(mc.player.registryAccess(), inventoryNBT);
        Inventory playerInv = mc.player.getInventory();
        HullMenu menu = new HullMenu(containerId, playerInv, handler, entityId, localPos);
        mc.player.containerMenu = menu;
        mc.setScreen(new HullScreen(menu, playerInv, Component.translatable("block.create_armored_rails.hull")));
    }
}
