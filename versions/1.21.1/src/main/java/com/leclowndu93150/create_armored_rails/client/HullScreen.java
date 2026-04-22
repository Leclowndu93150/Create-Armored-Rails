package com.leclowndu93150.create_armored_rails.client;

import com.leclowndu93150.create_armored_rails.block.HullMenu;
import com.leclowndu93150.create_armored_rails.health.HullFrameStats;
import com.leclowndu93150.create_armored_rails.health.UpgradeHelper;
import com.leclowndu93150.create_armored_rails.network.ModPackets;
import com.leclowndu93150.create_armored_rails.network.UpdateHullOnContraptionPayload;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.simibubi.create.foundation.gui.AllGuiTextures.PLAYER_INVENTORY;

public class HullScreen extends AbstractSimiContainerScreen<HullMenu> {
    private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath("create_armored_rails", "textures/gui/hull_assembly.png");
    private static final ResourceLocation WIDGET_TEX = ResourceLocation.fromNamespaceAndPath("create_armored_rails", "textures/gui/stats_widget.png");
    private static final ResourceLocation HEALTH_FULL = ResourceLocation.fromNamespaceAndPath("create_armored_rails", "textures/gui/health_bar_full.png");
    private static final ResourceLocation HEALTH_EMPTY = ResourceLocation.fromNamespaceAndPath("create_armored_rails", "textures/gui/health_bar_empty.png");

    private static final int BG_WIDTH = 178;
    private static final int BG_HEIGHT = 142;
    private static final int WIDGET_W = 76;
    private static final int WIDGET_H = 56;
    private static final int HEALTH_BAR_W = 30;
    private static final int HEALTH_BAR_H = 10;
    private static final int BLUE_BOX_X = 1;
    private static final int BLUE_BOX_Y = 1;
    private static final int BLUE_BOX_W = 74;
    private static final int BLUE_BOX_H = 13;

    public HullScreen(HullMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
    }

    @Override
    protected void init() {
        setWindowSize(BG_WIDTH, BG_HEIGHT + 2 + PLAYER_INVENTORY.getHeight());
        setWindowOffset(0, 0);
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        int invX = leftPos + (BG_WIDTH - PLAYER_INVENTORY.getWidth()) / 2;
        int invY = topPos + BG_HEIGHT + 2;
        renderPlayerInventory(graphics, invX, invY);

        int x = leftPos;
        int y = topPos;
        graphics.blit(BG, x, y, 0, 0, BG_WIDTH, BG_HEIGHT, BG_WIDTH, BG_HEIGHT);

        for (int i = 0; i < 4; i++) {
            if (!menu.isModifierSlotUnlocked(i + 1)) {
                int sx = x + menu.slots.get(i + 1).x - 1;
                int sy = y + menu.slots.get(i + 1).y - 1;
                graphics.fill(sx, sy, sx + 18, sy + 18, 0x80000000);
            }
        }

        int wx = x + BG_WIDTH + 3;
        int wy = y;
        graphics.blit(WIDGET_TEX, wx, wy, 0, 0, WIDGET_W, WIDGET_H, WIDGET_W, WIDGET_H);

        renderHealthBar(graphics, wx, wy);
        renderStatText(graphics, wx, wy);
    }

    private void renderHealthBar(GuiGraphics graphics, int wx, int wy) {
        int blueX = wx + BLUE_BOX_X;
        int blueY = wy + BLUE_BOX_Y;
        int barX = blueX + (BLUE_BOX_W - HEALTH_BAR_W) / 2;
        int barY = blueY + (BLUE_BOX_H - HEALTH_BAR_H) / 2;

        graphics.blit(HEALTH_EMPTY, barX, barY, 0, 0, HEALTH_BAR_W, HEALTH_BAR_H, HEALTH_BAR_W, HEALTH_BAR_H);

        float pct = getHealthPercent();
        if (pct > 0) {
            int filledW = Math.max(1, Math.round(HEALTH_BAR_W * pct));
            graphics.blit(HEALTH_FULL, barX, barY, 0, 0, filledW, HEALTH_BAR_H, HEALTH_BAR_W, HEALTH_BAR_H);
        }
    }

    private void renderStatText(GuiGraphics graphics, int wx, int wy) {
        ItemStack upgrade = menu.getHullInventory().getStackInSlot(0);
        HullFrameStats stats = UpgradeHelper.getHullFrameStats(upgrade);

        int textX = wx + 4;
        int lineY = wy + 18;
        int maxY = wy + 50;

        if (stats == null) {
            graphics.drawString(font, "No Frame", textX, lineY, 0x888888, true);
            return;
        }

        if (stats.hpBonus() > 0 && lineY < maxY) {
            graphics.drawString(font, "+" + (int) stats.hpBonus() + " HP", textX, lineY, 0x55FF55, true);
            lineY += 9;
        }
        if (stats.blastProtection() != 0 && lineY < maxY) {
            graphics.drawString(font, pct(stats.blastProtection()) + " Blast", textX, lineY,
                    stats.blastProtection() > 0 ? 0x55FF55 : 0xFF5555, true);
            lineY += 9;
        }
        if (stats.projectileProtection() != 0 && lineY < maxY) {
            graphics.drawString(font, pct(stats.projectileProtection()) + " Proj", textX, lineY,
                    stats.projectileProtection() > 0 ? 0x55FF55 : 0xFF5555, true);
            lineY += 9;
        }
        if (stats.speedModifier() != 0 && lineY < maxY) {
            graphics.drawString(font, pct(stats.speedModifier()) + " Speed", textX, lineY,
                    stats.speedModifier() > 0 ? 0x55FF55 : 0xFF5555, true);
            lineY += 9;
        }
        if (stats.fireResistant() && lineY < maxY) {
            graphics.drawString(font, "Fire Res", textX, lineY, 0xFFAA00, true);
            lineY += 9;
        }
        if (stats.modifierSlots() > 0 && lineY < maxY) {
            graphics.drawString(font, stats.modifierSlots() + " Slots", textX, lineY, 0x55FFFF, true);
        }
    }

    private String pct(float value) {
        int p = Math.round(value * 100);
        return (p > 0 ? "+" : "") + p + "%";
    }

    private float getHealthPercent() {
        return 1.0f;
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderTooltip(graphics, mouseX, mouseY);

        int wx = leftPos + BG_WIDTH + 3;
        int wy = topPos;
        int blueX = wx + BLUE_BOX_X;
        int blueY = wy + BLUE_BOX_Y;

        if (mouseX >= blueX && mouseX <= blueX + BLUE_BOX_W && mouseY >= blueY && mouseY <= blueY + BLUE_BOX_H) {
            List<Component> tip = new ArrayList<>();
            tip.add(Component.literal("Train Health").withStyle(ChatFormatting.WHITE));
            ItemStack frame = menu.getHullInventory().getStackInSlot(0);
            HullFrameStats stats = UpgradeHelper.getHullFrameStats(frame);
            if (stats != null) {
                tip.add(Component.literal("+" + (int) stats.hpBonus() + " HP from frame").withStyle(ChatFormatting.GRAY));
            }
            graphics.renderTooltip(font, tip, Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    public void removed() {
        super.removed();
        if (menu.isContraptionMode()) {
            Minecraft mc = Minecraft.getInstance();
            PacketDistributor.sendToServer(new UpdateHullOnContraptionPayload(
                    menu.getEntityId(), menu.getLocalPos(),
                    menu.getHullInventory().serializeNBT(mc.player.registryAccess())));
        }
    }
}
