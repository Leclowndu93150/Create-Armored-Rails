package com.leclowndu93150.create_armored_rails.health;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class UpgradeHelper {

    public static boolean isUpgradeItem(ItemStack stack) {
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();
        if (item == ModItems.IRON_PLATING.get()) return true;
        if (item == ModItems.STEEL_PLATING.get()) return true;
        if (item == ModItems.TUNGSTEN_PLATING.get()) return true;
        return isCustomUpgrade(item);
    }

    public static boolean isModifierItem(ItemStack stack) {
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();
        if (item == ModItems.SHOCK_ABSORBER.get()) return true;
        if (item == ModItems.REINFORCED_FRAME.get()) return true;
        if (item == ModItems.HARDENED_PLATING.get()) return true;
        if (item == ModItems.AERODYNAMIC_CASING.get()) return true;
        return isCustomModifier(item);
    }

    public static int getUpgradeHP(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        Item item = stack.getItem();
        if (item == ModItems.IRON_PLATING.get()) return Config.IRON_PLATING_HP.get();
        if (item == ModItems.STEEL_PLATING.get()) return Config.STEEL_PLATING_HP.get();
        if (item == ModItems.TUNGSTEN_PLATING.get()) return Config.TUNGSTEN_PLATING_HP.get();
        return getCustomUpgradeHP(item);
    }

    public static int getModifierSlotCount(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        Item item = stack.getItem();
        if (item == ModItems.IRON_PLATING.get()) return Config.IRON_MODIFIER_SLOTS.get();
        if (item == ModItems.STEEL_PLATING.get()) return Config.STEEL_MODIFIER_SLOTS.get();
        if (item == ModItems.TUNGSTEN_PLATING.get()) return Config.TUNGSTEN_MODIFIER_SLOTS.get();
        return getCustomUpgradeModifierSlots(item);
    }

    public static float getDamageReduction(ItemStack stack) {
        if (stack.isEmpty()) return 0f;
        Item item = stack.getItem();
        if (item == ModItems.REINFORCED_FRAME.get()) return Config.REINFORCED_FRAME_DAMAGE_REDUCTION.get().floatValue();
        return getCustomModifierDamageReduction(item);
    }

    public static float getThresholdReduction(ItemStack stack) {
        if (stack.isEmpty()) return 0f;
        Item item = stack.getItem();
        if (item == ModItems.SHOCK_ABSORBER.get()) return Config.SHOCK_ABSORBER_THRESHOLD_REDUCTION.get().floatValue();
        return getCustomModifierThresholdReduction(item);
    }

    public static float getHPBonusPercent(ItemStack stack) {
        if (stack.isEmpty()) return 0f;
        Item item = stack.getItem();
        if (item == ModItems.HARDENED_PLATING.get()) return Config.HARDENED_PLATING_HP_BONUS.get().floatValue();
        return getCustomModifierHPBonus(item);
    }

    public static float getSpeedBoost(ItemStack stack) {
        if (stack.isEmpty()) return 0f;
        Item item = stack.getItem();
        if (item == ModItems.AERODYNAMIC_CASING.get()) return Config.AERODYNAMIC_CASING_SPEED_BOOST.get().floatValue();
        return 0f;
    }

    public static String getRepairMaterial(ItemStack stack) {
        if (stack.isEmpty()) return "";
        Item item = stack.getItem();
        if (item == ModItems.IRON_PLATING.get()) return Config.IRON_REPAIR_MATERIAL.get();
        if (item == ModItems.STEEL_PLATING.get()) return Config.STEEL_REPAIR_MATERIAL.get();
        if (item == ModItems.TUNGSTEN_PLATING.get()) return Config.TUNGSTEN_REPAIR_MATERIAL.get();
        return getCustomUpgradeRepairMaterial(item);
    }

    private static boolean isCustomUpgrade(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return false;
        String idStr = id.toString();
        List<? extends String> customs = Config.CUSTOM_UPGRADES.get();
        for (String entry : customs) {
            String[] parts = entry.split(",");
            if (parts.length >= 1 && parts[0].equals(idStr)) return true;
        }
        return false;
    }

    private static boolean isCustomModifier(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return false;
        String idStr = id.toString();
        List<? extends String> customs = Config.CUSTOM_MODIFIERS.get();
        for (String entry : customs) {
            String[] parts = entry.split(",");
            if (parts.length >= 1 && parts[0].equals(idStr)) return true;
        }
        return false;
    }

    private static int getCustomUpgradeHP(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return 0;
        String idStr = id.toString();
        List<? extends String> customs = Config.CUSTOM_UPGRADES.get();
        for (String entry : customs) {
            String[] parts = entry.split(",");
            if (parts.length >= 2 && parts[0].equals(idStr)) {
                try { return Integer.parseInt(parts[1]); } catch (NumberFormatException e) { return 0; }
            }
        }
        return 0;
    }

    private static int getCustomUpgradeModifierSlots(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return 0;
        String idStr = id.toString();
        List<? extends String> customs = Config.CUSTOM_UPGRADES.get();
        for (String entry : customs) {
            String[] parts = entry.split(",");
            if (parts.length >= 3 && parts[0].equals(idStr)) {
                try { return Integer.parseInt(parts[2]); } catch (NumberFormatException e) { return 0; }
            }
        }
        return 0;
    }

    private static String getCustomUpgradeRepairMaterial(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return "";
        String idStr = id.toString();
        List<? extends String> customs = Config.CUSTOM_UPGRADES.get();
        for (String entry : customs) {
            String[] parts = entry.split(",");
            if (parts.length >= 4 && parts[0].equals(idStr)) return parts[3];
        }
        return "";
    }

    private static float getCustomModifierDamageReduction(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return 0f;
        String idStr = id.toString();
        List<? extends String> customs = Config.CUSTOM_MODIFIERS.get();
        for (String entry : customs) {
            String[] parts = entry.split(",");
            if (parts.length >= 2 && parts[0].equals(idStr)) {
                try { return Float.parseFloat(parts[1]); } catch (NumberFormatException e) { return 0f; }
            }
        }
        return 0f;
    }

    private static float getCustomModifierThresholdReduction(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return 0f;
        String idStr = id.toString();
        List<? extends String> customs = Config.CUSTOM_MODIFIERS.get();
        for (String entry : customs) {
            String[] parts = entry.split(",");
            if (parts.length >= 3 && parts[0].equals(idStr)) {
                try { return Float.parseFloat(parts[2]); } catch (NumberFormatException e) { return 0f; }
            }
        }
        return 0f;
    }

    private static float getCustomModifierHPBonus(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return 0f;
        String idStr = id.toString();
        List<? extends String> customs = Config.CUSTOM_MODIFIERS.get();
        for (String entry : customs) {
            String[] parts = entry.split(",");
            if (parts.length >= 4 && parts[0].equals(idStr)) {
                try { return Float.parseFloat(parts[3]); } catch (NumberFormatException e) { return 0f; }
            }
        }
        return 0f;
    }
}
