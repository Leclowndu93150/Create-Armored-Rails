package com.leclowndu93150.create_armored_rails;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = CreateArmoredRails.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue BASE_TRAIN_HP;
    public static final ForgeConfigSpec.IntValue IRON_PLATING_HP;
    public static final ForgeConfigSpec.IntValue STEEL_PLATING_HP;
    public static final ForgeConfigSpec.IntValue TUNGSTEN_PLATING_HP;
    public static final ForgeConfigSpec.IntValue IRON_MODIFIER_SLOTS;
    public static final ForgeConfigSpec.IntValue STEEL_MODIFIER_SLOTS;
    public static final ForgeConfigSpec.IntValue TUNGSTEN_MODIFIER_SLOTS;

    public static final ForgeConfigSpec.DoubleValue CRITICAL_FAILURE_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue SHOCK_ABSORBER_THRESHOLD_REDUCTION;
    public static final ForgeConfigSpec.DoubleValue REINFORCED_FRAME_DAMAGE_REDUCTION;
    public static final ForgeConfigSpec.DoubleValue HARDENED_PLATING_HP_BONUS;
    public static final ForgeConfigSpec.DoubleValue AERODYNAMIC_CASING_SPEED_BOOST;

    public static final ForgeConfigSpec.IntValue REPAIR_AMOUNT_PER_HIT;
    public static final ForgeConfigSpec.ConfigValue<String> BASE_REPAIR_MATERIAL;
    public static final ForgeConfigSpec.ConfigValue<String> IRON_REPAIR_MATERIAL;
    public static final ForgeConfigSpec.ConfigValue<String> STEEL_REPAIR_MATERIAL;
    public static final ForgeConfigSpec.ConfigValue<String> TUNGSTEN_REPAIR_MATERIAL;

    public static final ForgeConfigSpec.BooleanValue LINEAR_SPEED_MALUS;

    public static final ForgeConfigSpec.BooleanValue EXPLODE_ON_DEATH;
    public static final ForgeConfigSpec.DoubleValue EXPLOSION_POWER_PER_BLOCK;
    public static final ForgeConfigSpec.BooleanValue EXPLOSION_NON_DESTRUCTIVE;

    public static final ForgeConfigSpec.BooleanValue CRITICAL_ALARM_ENABLED;
    public static final ForgeConfigSpec.IntValue CRITICAL_ALARM_INTERVAL;

    public static final ForgeConfigSpec.BooleanValue MOB_TARGETING_ENABLED;
    public static final ForgeConfigSpec.DoubleValue MOB_TARGET_RANGE;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> CUSTOM_UPGRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> CUSTOM_MODIFIERS;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> INTERACTIVE_BLOCKS;

    static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("health");
        BASE_TRAIN_HP = BUILDER.defineInRange("baseTrainHP", 100, 1, 10000);
        IRON_PLATING_HP = BUILDER.defineInRange("ironPlatingHP", 50, 0, 10000);
        STEEL_PLATING_HP = BUILDER.defineInRange("steelPlatingHP", 100, 0, 10000);
        TUNGSTEN_PLATING_HP = BUILDER.defineInRange("tungstenPlatingHP", 150, 0, 10000);
        IRON_MODIFIER_SLOTS = BUILDER.defineInRange("ironModifierSlots", 1, 0, 3);
        STEEL_MODIFIER_SLOTS = BUILDER.defineInRange("steelModifierSlots", 2, 0, 3);
        TUNGSTEN_MODIFIER_SLOTS = BUILDER.defineInRange("tungstenModifierSlots", 3, 0, 3);
        BUILDER.pop();

        BUILDER.push("criticalFailure");
        CRITICAL_FAILURE_THRESHOLD = BUILDER.defineInRange("threshold", 0.25, 0.0, 1.0);
        SHOCK_ABSORBER_THRESHOLD_REDUCTION = BUILDER.defineInRange("shockAbsorberReduction", 0.10, 0.0, 1.0);
        REINFORCED_FRAME_DAMAGE_REDUCTION = BUILDER.defineInRange("reinforcedFrameReduction", 0.20, 0.0, 1.0);
        HARDENED_PLATING_HP_BONUS = BUILDER.defineInRange("hardenedPlatingHPBonus", 0.25, 0.0, 10.0);
        AERODYNAMIC_CASING_SPEED_BOOST = BUILDER.defineInRange("aerodynamicCasingSpeedBoost", 0.15, 0.0, 2.0);
        BUILDER.pop();

        BUILDER.push("repair");
        REPAIR_AMOUNT_PER_HIT = BUILDER.defineInRange("repairPerHit", 10, 1, 10000);
        BASE_REPAIR_MATERIAL = BUILDER.comment("Repair material for trains without an upgrade installed")
                .define("baseRepairMaterial", "minecraft:iron_nugget");
        IRON_REPAIR_MATERIAL = BUILDER.define("ironRepairMaterial", "minecraft:iron_ingot");
        STEEL_REPAIR_MATERIAL = BUILDER.define("steelRepairMaterial", "create:steel_ingot");
        TUNGSTEN_REPAIR_MATERIAL = BUILDER.define("tungstenRepairMaterial", "create_armored_rails:tungsten_ingot");
        BUILDER.pop();

        BUILDER.push("speed");
        LINEAR_SPEED_MALUS = BUILDER.define("linearSpeedMalus", true);
        BUILDER.pop();

        BUILDER.push("destruction");
        EXPLODE_ON_DEATH = BUILDER.comment("If true, trains explode and disassemble when health reaches 0")
                .define("explodeOnDeath", true);
        EXPLOSION_POWER_PER_BLOCK = BUILDER.comment("Explosion power per block in the contraption (vanilla TNT = 4.0). Scaled by train size.")
                .defineInRange("explosionPowerPerBlock", 0.02, 0.0, 1.0);
        EXPLOSION_NON_DESTRUCTIVE = BUILDER.comment("If true, explosions only produce particles and knockback without destroying blocks")
                .define("nonDestructiveExplosion", true);
        BUILDER.pop();

        BUILDER.push("criticalAlarm");
        CRITICAL_ALARM_ENABLED = BUILDER.comment("If true, trains in critical failure play an alarm sound")
                .define("enabled", true);
        CRITICAL_ALARM_INTERVAL = BUILDER.comment("Ticks between alarm sounds (20 ticks = 1 second)")
                .defineInRange("intervalTicks", 40, 10, 200);
        BUILDER.pop();

        BUILDER.push("mobTargeting");
        MOB_TARGETING_ENABLED = BUILDER.define("enabled", true);
        MOB_TARGET_RANGE = BUILDER.defineInRange("range", 32.0, 1.0, 128.0);
        BUILDER.pop();

        BUILDER.push("customItems");
        CUSTOM_UPGRADES = BUILDER.comment("Additional upgrade items. Format: modid:item,hp,modifier_slots,repair_material_id")
                .defineListAllowEmpty("customUpgrades", List.of(), o -> o instanceof String);
        CUSTOM_MODIFIERS = BUILDER.comment("Additional modifier items. Format: modid:item,damage_reduction,threshold_reduction,hp_bonus_percent")
                .defineListAllowEmpty("customModifiers", List.of(), o -> o instanceof String);
        BUILDER.pop();

        BUILDER.push("interactiveBlocks");
        INTERACTIVE_BLOCKS = BUILDER.comment("Blocks that can be interacted with on assembled contraptions.",
                        "Supports both stateless blocks (crafting table, anvil) and container blocks (chest, furnace, barrel).",
                        "Format: modid:block_id")
                .defineListAllowEmpty("blocks", List.of(
                        "minecraft:crafting_table",
                        "minecraft:anvil",
                        "minecraft:chipped_anvil",
                        "minecraft:damaged_anvil",
                        "minecraft:stonecutter",
                        "minecraft:grindstone",
                        "minecraft:smithing_table",
                        "minecraft:cartography_table",
                        "minecraft:loom",
                        "minecraft:chest",
                        "minecraft:trapped_chest",
                        "minecraft:barrel",
                        "minecraft:furnace",
                        "minecraft:blast_furnace",
                        "minecraft:smoker",
                        "minecraft:dispenser",
                        "minecraft:dropper",
                        "minecraft:hopper",
                        "minecraft:shulker_box"
                ), o -> o instanceof String);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
    }
}
