package com.leclowndu93150.create_armored_rails.health;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.registry.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class UpgradeHelper {
    private static final Map<Item, Supplier<HullFrameStats>> HULL_FRAMES = new HashMap<>();
    private static final Map<Item, Supplier<ModifierStats>> MODIFIERS = new HashMap<>();

    public static void init() {
        HULL_FRAMES.clear();
        MODIFIERS.clear();

        HULL_FRAMES.put(ModItems.ANDESITE_HULL_FRAME.get(), () -> new HullFrameStats(
                Config.ANDESITE_HP.get(), Config.ANDESITE_BLAST.get().floatValue(), Config.ANDESITE_PROJ.get().floatValue(),
                Config.ANDESITE_SPEED.get().floatValue(), Config.ANDESITE_DMG_RED.get().floatValue(), Config.ANDESITE_CRIT.get().floatValue(),
                Config.ANDESITE_MOB_RANGE.get(), Config.ANDESITE_REPAIR_COST.get().floatValue(), Config.ANDESITE_SPEED_PENALTY_RED.get().floatValue(),
                Config.ANDESITE_FIRE_RESIST.get(), Config.ANDESITE_MOD_SLOTS.get(), Config.ANDESITE_REPAIR_MAT.get()));

        HULL_FRAMES.put(ModItems.COPPER_HULL_FRAME.get(), () -> new HullFrameStats(
                Config.COPPER_HP.get(), Config.COPPER_BLAST.get().floatValue(), Config.COPPER_PROJ.get().floatValue(),
                Config.COPPER_SPEED.get().floatValue(), Config.COPPER_DMG_RED.get().floatValue(), Config.COPPER_CRIT.get().floatValue(),
                Config.COPPER_MOB_RANGE.get(), Config.COPPER_REPAIR_COST.get().floatValue(), Config.COPPER_SPEED_PENALTY_RED.get().floatValue(),
                Config.COPPER_FIRE_RESIST.get(), Config.COPPER_MOD_SLOTS.get(), Config.COPPER_REPAIR_MAT.get()));

        HULL_FRAMES.put(ModItems.BRASS_HULL_FRAME.get(), () -> new HullFrameStats(
                Config.BRASS_HP.get(), Config.BRASS_BLAST.get().floatValue(), Config.BRASS_PROJ.get().floatValue(),
                Config.BRASS_SPEED.get().floatValue(), Config.BRASS_DMG_RED.get().floatValue(), Config.BRASS_CRIT.get().floatValue(),
                Config.BRASS_MOB_RANGE.get(), Config.BRASS_REPAIR_COST.get().floatValue(), Config.BRASS_SPEED_PENALTY_RED.get().floatValue(),
                Config.BRASS_FIRE_RESIST.get(), Config.BRASS_MOD_SLOTS.get(), Config.BRASS_REPAIR_MAT.get()));

        HULL_FRAMES.put(ModItems.ALUMINIUM_HULL_FRAME.get(), () -> new HullFrameStats(
                Config.ALUMINIUM_HP.get(), Config.ALUMINIUM_BLAST.get().floatValue(), Config.ALUMINIUM_PROJ.get().floatValue(),
                Config.ALUMINIUM_SPEED.get().floatValue(), Config.ALUMINIUM_DMG_RED.get().floatValue(), Config.ALUMINIUM_CRIT.get().floatValue(),
                Config.ALUMINIUM_MOB_RANGE.get(), Config.ALUMINIUM_REPAIR_COST.get().floatValue(), Config.ALUMINIUM_SPEED_PENALTY_RED.get().floatValue(),
                Config.ALUMINIUM_FIRE_RESIST.get(), Config.ALUMINIUM_MOD_SLOTS.get(), Config.ALUMINIUM_REPAIR_MAT.get()));

        HULL_FRAMES.put(ModItems.IRON_HULL_FRAME.get(), () -> new HullFrameStats(
                Config.IRON_HP.get(), Config.IRON_BLAST.get().floatValue(), Config.IRON_PROJ.get().floatValue(),
                Config.IRON_SPEED.get().floatValue(), Config.IRON_DMG_RED.get().floatValue(), Config.IRON_CRIT.get().floatValue(),
                Config.IRON_MOB_RANGE.get(), Config.IRON_REPAIR_COST.get().floatValue(), Config.IRON_SPEED_PENALTY_RED.get().floatValue(),
                Config.IRON_FIRE_RESIST.get(), Config.IRON_MOD_SLOTS.get(), Config.IRON_REPAIR_MAT.get()));

        HULL_FRAMES.put(ModItems.STEEL_HULL_FRAME.get(), () -> new HullFrameStats(
                Config.STEEL_HP.get(), Config.STEEL_BLAST.get().floatValue(), Config.STEEL_PROJ.get().floatValue(),
                Config.STEEL_SPEED.get().floatValue(), Config.STEEL_DMG_RED.get().floatValue(), Config.STEEL_CRIT.get().floatValue(),
                Config.STEEL_MOB_RANGE.get(), Config.STEEL_REPAIR_COST.get().floatValue(), Config.STEEL_SPEED_PENALTY_RED.get().floatValue(),
                Config.STEEL_FIRE_RESIST.get(), Config.STEEL_MOD_SLOTS.get(), Config.STEEL_REPAIR_MAT.get()));

        HULL_FRAMES.put(ModItems.TUNGSTEN_HULL_FRAME.get(), () -> new HullFrameStats(
                Config.TUNGSTEN_HP.get(), Config.TUNGSTEN_BLAST.get().floatValue(), Config.TUNGSTEN_PROJ.get().floatValue(),
                Config.TUNGSTEN_SPEED.get().floatValue(), Config.TUNGSTEN_DMG_RED.get().floatValue(), Config.TUNGSTEN_CRIT.get().floatValue(),
                Config.TUNGSTEN_MOB_RANGE.get(), Config.TUNGSTEN_REPAIR_COST.get().floatValue(), Config.TUNGSTEN_SPEED_PENALTY_RED.get().floatValue(),
                Config.TUNGSTEN_FIRE_RESIST.get(), Config.TUNGSTEN_MOD_SLOTS.get(), Config.TUNGSTEN_REPAIR_MAT.get()));

        HULL_FRAMES.put(ModItems.NETHERITE_HULL_FRAME.get(), () -> new HullFrameStats(
                Config.NETHERITE_HP.get(), Config.NETHERITE_BLAST.get().floatValue(), Config.NETHERITE_PROJ.get().floatValue(),
                Config.NETHERITE_SPEED.get().floatValue(), Config.NETHERITE_DMG_RED.get().floatValue(), Config.NETHERITE_CRIT.get().floatValue(),
                Config.NETHERITE_MOB_RANGE.get(), Config.NETHERITE_REPAIR_COST.get().floatValue(), Config.NETHERITE_SPEED_PENALTY_RED.get().floatValue(),
                Config.NETHERITE_FIRE_RESIST.get(), Config.NETHERITE_MOD_SLOTS.get(), Config.NETHERITE_REPAIR_MAT.get()));

        HULL_FRAMES.put(ModItems.TUNGSTEN_ALLOY_HULL_FRAME.get(), () -> new HullFrameStats(
                Config.TUNGSTEN_ALLOY_HP.get(), Config.TUNGSTEN_ALLOY_BLAST.get().floatValue(), Config.TUNGSTEN_ALLOY_PROJ.get().floatValue(),
                Config.TUNGSTEN_ALLOY_SPEED.get().floatValue(), Config.TUNGSTEN_ALLOY_DMG_RED.get().floatValue(), Config.TUNGSTEN_ALLOY_CRIT.get().floatValue(),
                Config.TUNGSTEN_ALLOY_MOB_RANGE.get(), Config.TUNGSTEN_ALLOY_REPAIR_COST.get().floatValue(), Config.TUNGSTEN_ALLOY_SPEED_PENALTY_RED.get().floatValue(),
                Config.TUNGSTEN_ALLOY_FIRE_RESIST.get(), Config.TUNGSTEN_ALLOY_MOD_SLOTS.get(), Config.TUNGSTEN_ALLOY_REPAIR_MAT.get()));

        MODIFIERS.put(ModItems.OVERDRIVE_REGULATOR.get(), () -> new ModifierStats(
                0, 0, 0, 0, 0, Config.OVERDRIVE_SPEED_BOOST.get().floatValue(),
                Config.OVERDRIVE_COOLDOWN_TICKS.get(), 0, 0, 0));

        MODIFIERS.put(ModItems.EMERGENCY_OVERRIDE_SYSTEM.get(), () -> new ModifierStats(
                0, 0, Config.EMERGENCY_OVERRIDE_CRIT_REDUCTION.get().floatValue(),
                0, 0, 0, 0, 0, 0, 0));

        MODIFIERS.put(ModItems.OPTICAL_CAMOUFLAGE.get(), () -> new ModifierStats(
                0, 0, 0, 0, 0, 0, 0,
                Config.OPTICAL_CAMOUFLAGE_RANGE_REDUCTION.get(), 0, 0));

        MODIFIERS.put(ModItems.PRISTINE_CONDITION.get(), () -> new ModifierStats(
                0, 0, 0, 0, Config.PRISTINE_SPEED_BOOST.get().floatValue(),
                0, 0, 0, 0, 0));

        MODIFIERS.put(ModItems.SUPERHEATED_ENGINES.get(), () -> new ModifierStats(
                0, 0, 0, Config.SUPERHEATED_SPEED_BOOST.get().floatValue(),
                0, 0, 0, 0, 0, 0));

        MODIFIERS.put(ModItems.REINFORCED_BULKHEADS.get(), () -> new ModifierStats(
                Config.REINFORCED_BULKHEADS_DMG_REDUCTION.get().floatValue(),
                0, 0, 0, 0, 0, 0, 0, 0, 0));

        MODIFIERS.put(ModItems.SHOCK_ABSORBER.get(), () -> new ModifierStats(
                0, Config.SHOCK_ABSORBER_BLAST_PROTECTION.get().floatValue(),
                0, 0, 0, 0, 0, 0, 0, 0));

        MODIFIERS.put(ModItems.REACTIVE_ARMOR.get(), () -> new ModifierStats(
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                Config.REACTIVE_ARMOR_THORNS_DAMAGE.get().floatValue()));

        MODIFIERS.put(ModItems.ENGINEERS_MANUALS.get(), () -> new ModifierStats(
                0, 0, 0, 0, 0, 0, 0, 0,
                Config.ENGINEERS_MANUALS_REPAIR_REDUCTION.get().floatValue(), 0));
    }

    public static boolean isUpgradeItem(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return HULL_FRAMES.containsKey(stack.getItem());
    }

    public static boolean isModifierItem(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return MODIFIERS.containsKey(stack.getItem());
    }

    public static HullFrameStats getHullFrameStats(ItemStack stack) {
        if (stack.isEmpty()) return null;
        Supplier<HullFrameStats> supplier = HULL_FRAMES.get(stack.getItem());
        return supplier != null ? supplier.get() : null;
    }

    public static ModifierStats getModifierStats(ItemStack stack) {
        if (stack.isEmpty()) return null;
        Supplier<ModifierStats> supplier = MODIFIERS.get(stack.getItem());
        return supplier != null ? supplier.get() : null;
    }

    public static int getUpgradeHP(ItemStack stack) {
        HullFrameStats stats = getHullFrameStats(stack);
        return stats != null ? (int) stats.hpBonus() : 0;
    }

    public static int getModifierSlotCount(ItemStack stack) {
        HullFrameStats stats = getHullFrameStats(stack);
        return stats != null ? stats.modifierSlots() : 0;
    }

    public static String getRepairMaterial(ItemStack stack) {
        HullFrameStats stats = getHullFrameStats(stack);
        return stats != null ? stats.repairMaterial() : "";
    }
}
