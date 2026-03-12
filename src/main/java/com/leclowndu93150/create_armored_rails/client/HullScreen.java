package com.leclowndu93150.create_armored_rails.client;

import com.leclowndu93150.create_armored_rails.block.HullMenu;
import com.leclowndu93150.create_armored_rails.network.ModPackets;
import com.leclowndu93150.create_armored_rails.network.UpdateHullOnContraptionPacket;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.simibubi.create.foundation.gui.AllGuiTextures.PLAYER_INVENTORY;

public class HullScreen extends AbstractSimiContainerScreen<HullMenu> {
    private static final ResourceLocation BG = new ResourceLocation("create_armored_rails", "textures/gui/hull.png");
    private static final int BG_WIDTH = 200;
    private static final int BG_HEIGHT = 74;

    public HullScreen(HullMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
    }

    @Override
    protected void init() {
        setWindowSize(BG_WIDTH, BG_HEIGHT + 4 + PLAYER_INVENTORY.getHeight());
        setWindowOffset(0, 0);
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int invX = getLeftOfCentered(PLAYER_INVENTORY.getWidth());
        int invY = topPos + BG_HEIGHT + 4;
        renderPlayerInventory(graphics, invX, invY);

        int x = leftPos;
        int y = topPos;
        graphics.blit(BG, x, y, 0, 0, BG_WIDTH, BG_HEIGHT);

        graphics.drawString(font, title, x + 8, y + 5, AllGuiTextures.FONT_COLOR, false);

        graphics.drawString(font, Component.translatable("gui.create_armored_rails.upgrade"),
                x + 8, y + 29, AllGuiTextures.FONT_COLOR, false);
        graphics.drawString(font, Component.translatable("gui.create_armored_rails.modifiers"),
                x + 50, y + 29, AllGuiTextures.FONT_COLOR, false);

        for (int i = 0; i < 3; i++) {
            if (!menu.isModifierSlotUnlocked(i + 1)) {
                int slotX = x + 54 + i * 22;
                int slotY = y + 40;
                graphics.fill(slotX, slotY, slotX + 18, slotY + 18, 0x80000000);
            }
        }
    }

    @Override
    public void removed() {
        super.removed();
        if (menu.isContraptionMode()) {
            ModPackets.sendToServer(new UpdateHullOnContraptionPacket(
                    menu.getEntityId(), menu.getLocalPos(),
                    menu.getHullInventory().serializeNBT()));
        }
    }
}
