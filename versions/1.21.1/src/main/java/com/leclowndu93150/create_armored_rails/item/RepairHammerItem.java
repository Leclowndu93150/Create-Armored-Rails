package com.leclowndu93150.create_armored_rails.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class RepairHammerItem extends Item {
    public RepairHammerItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.create_armored_rails.repair_hammer.tooltip1")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.create_armored_rails.repair_hammer.tooltip2")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.create_armored_rails.repair_hammer.tooltip3")
                .withStyle(ChatFormatting.DARK_GRAY));
    }
}
