package com.leclowndu93150.create_armored_rails.compat.jade;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.block.HullBlockEntity;
import com.leclowndu93150.create_armored_rails.health.UpgradeHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum HullBlockComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CreateArmoredRails.MODID, "hull_block");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (!data.contains("HullUpgrade")) return;

        String upgradeName = data.getString("HullUpgrade");
        int upgradeHP = data.getInt("HullUpgradeHP");
        int modifierSlots = data.getInt("HullModifierSlots");

        if (!upgradeName.isEmpty()) {
            tooltip.add(Component.translatable("jade.create_armored_rails.upgrade")
                    .append(": ")
                    .append(Component.literal(upgradeName).withStyle(ChatFormatting.GREEN))
                    .append(Component.literal(" (+" + upgradeHP + " HP)").withStyle(ChatFormatting.GRAY)));
        } else {
            tooltip.add(Component.translatable("jade.create_armored_rails.no_upgrade").withStyle(ChatFormatting.DARK_GRAY));
        }

        tooltip.add(Component.translatable("jade.create_armored_rails.modifier_slots")
                .append(": ")
                .append(Component.literal(String.valueOf(modifierSlots)).withStyle(ChatFormatting.YELLOW)));

        int installedModifiers = data.getInt("HullInstalledModifiers");
        if (installedModifiers > 0) {
            for (int i = 0; i < installedModifiers; i++) {
                String modName = data.getString("HullMod" + i);
                tooltip.add(Component.literal("  - " + modName).withStyle(ChatFormatting.AQUA));
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag data, BlockAccessor accessor) {
        if (!(accessor.getBlockEntity() instanceof HullBlockEntity hull)) return;

        ItemStack upgrade = hull.inventory.getStackInSlot(0);
        if (!upgrade.isEmpty()) {
            data.putString("HullUpgrade", upgrade.getHoverName().getString());
            data.putInt("HullUpgradeHP", UpgradeHelper.getUpgradeHP(upgrade));
            data.putInt("HullModifierSlots", UpgradeHelper.getModifierSlotCount(upgrade));
        } else {
            data.putString("HullUpgrade", "");
            data.putInt("HullUpgradeHP", 0);
            data.putInt("HullModifierSlots", 0);
        }

        int unlockedSlots = UpgradeHelper.getModifierSlotCount(upgrade);
        int installed = 0;
        for (int i = 1; i <= Math.min(unlockedSlots, 3); i++) {
            ItemStack mod = hull.inventory.getStackInSlot(i);
            if (!mod.isEmpty()) {
                data.putString("HullMod" + installed, mod.getHoverName().getString());
                installed++;
            }
        }
        data.putInt("HullInstalledModifiers", installed);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}
