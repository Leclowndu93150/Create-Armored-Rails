package com.leclowndu93150.create_armored_rails.item;

import com.leclowndu93150.create_armored_rails.health.HullFrameStats;
import com.leclowndu93150.create_armored_rails.health.ModifierStats;
import com.leclowndu93150.create_armored_rails.health.UpgradeHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class StatTooltipItem extends Item {

    public StatTooltipItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        HullFrameStats frame = UpgradeHelper.getHullFrameStats(stack);
        if (frame != null) {
            addFrameTooltip(frame, tooltip);
            return;
        }

        ModifierStats modifier = UpgradeHelper.getModifierStats(stack);
        if (modifier != null) {
            addModifierTooltip(modifier, tooltip);
        }
    }

    private void addFrameTooltip(HullFrameStats s, List<Component> tooltip) {
        if (s.hpBonus() != 0)
            tooltip.add(stat("+" + (int)s.hpBonus() + " HP", ChatFormatting.GREEN));
        if (s.blastProtection() != 0)
            tooltip.add(stat(formatPercent(s.blastProtection()) + " Blast Protection", s.blastProtection() > 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
        if (s.projectileProtection() != 0)
            tooltip.add(stat(formatPercent(s.projectileProtection()) + " Projectile Protection", s.projectileProtection() > 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
        if (s.speedModifier() != 0)
            tooltip.add(stat(formatPercent(s.speedModifier()) + " Speed", s.speedModifier() > 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
        if (s.damageReduction() != 0)
            tooltip.add(stat(formatPercent(s.damageReduction()) + " Damage Reduction", s.damageReduction() > 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
        if (s.criticalThresholdModifier() != 0)
            tooltip.add(stat(formatPercent(s.criticalThresholdModifier()) + " Critical Failure Threshold", s.criticalThresholdModifier() < 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
        if (s.mobRangeModifier() != 0)
            tooltip.add(stat((s.mobRangeModifier() > 0 ? "+" : "") + s.mobRangeModifier() + " Mob Detection Range", ChatFormatting.YELLOW));
        if (s.repairCostReduction() != 0)
            tooltip.add(stat(pct(s.repairCostReduction()) + " Reduced Repair Cost", ChatFormatting.GREEN));
        if (s.reducedSpeedPenalty() != 0)
            tooltip.add(stat("+" + pct(s.reducedSpeedPenalty()) + " Reduced Speed Penalty", ChatFormatting.GREEN));
        if (s.fireResistant())
            tooltip.add(stat("Fire Resistant", ChatFormatting.GOLD));
        if (s.modifierSlots() > 0)
            tooltip.add(stat(s.modifierSlots() + " Modifier Slot(s)", ChatFormatting.AQUA));
    }

    private void addModifierTooltip(ModifierStats s, List<Component> tooltip) {
        if (s.damageReduction() != 0)
            tooltip.add(stat("+" + pct(s.damageReduction()) + " Damage Reduction", ChatFormatting.GREEN));
        if (s.blastProtection() != 0)
            tooltip.add(stat("+" + pct(s.blastProtection()) + " Blast Protection", ChatFormatting.GREEN));
        if (s.criticalThresholdReduction() != 0)
            tooltip.add(stat("-" + pct(s.criticalThresholdReduction()) + " Critical Failure Threshold", ChatFormatting.GREEN));
        if (s.speedBoost() != 0)
            tooltip.add(stat("+" + pct(s.speedBoost()) + " Speed Boost", ChatFormatting.GREEN));
        if (s.speedBoostAtFullHP() != 0)
            tooltip.add(stat("+" + pct(s.speedBoostAtFullHP()) + " Speed at full HP", ChatFormatting.GREEN));
        if (s.speedBoostWhenDamaged() != 0)
            tooltip.add(stat("+" + pct(s.speedBoostWhenDamaged()) + " Speed when recently damaged", ChatFormatting.GREEN));
        if (s.mobRangeReduction() != 0)
            tooltip.add(stat("-" + s.mobRangeReduction() + " Mob Detection Range", ChatFormatting.GREEN));
        if (s.repairCostReduction() != 0)
            tooltip.add(stat(pct(s.repairCostReduction()) + " Reduced Repair Cost", ChatFormatting.GREEN));
        if (s.thornsDamage() != 0)
            tooltip.add(stat("Reflects " + String.format("%.0f", s.thornsDamage()) + " damage to attackers", ChatFormatting.GOLD));
    }

    private Component stat(String text, ChatFormatting color) {
        return Component.literal(" " + text).withStyle(color);
    }

    private String formatPercent(float value) {
        int pct = Math.round(value * 100);
        return (pct > 0 ? "+" : "") + pct + "%";
    }

    private String pct(float value) {
        return Math.round(value * 100) + "%";
    }
}
