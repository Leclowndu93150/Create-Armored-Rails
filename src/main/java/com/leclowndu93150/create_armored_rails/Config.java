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
    public static final ForgeConfigSpec.DoubleValue CRITICAL_FAILURE_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue BASE_MOB_RANGE;

    // Hull Frame configs
    public static final ForgeConfigSpec.IntValue ANDESITE_HP, COPPER_HP, BRASS_HP, ALUMINIUM_HP, IRON_HP, STEEL_HP, TUNGSTEN_HP, NETHERITE_HP, TUNGSTEN_ALLOY_HP;
    public static final ForgeConfigSpec.DoubleValue ANDESITE_BLAST, COPPER_BLAST, BRASS_BLAST, ALUMINIUM_BLAST, IRON_BLAST, STEEL_BLAST, TUNGSTEN_BLAST, NETHERITE_BLAST, TUNGSTEN_ALLOY_BLAST;
    public static final ForgeConfigSpec.DoubleValue ANDESITE_PROJ, COPPER_PROJ, BRASS_PROJ, ALUMINIUM_PROJ, IRON_PROJ, STEEL_PROJ, TUNGSTEN_PROJ, NETHERITE_PROJ, TUNGSTEN_ALLOY_PROJ;
    public static final ForgeConfigSpec.DoubleValue ANDESITE_SPEED, COPPER_SPEED, BRASS_SPEED, ALUMINIUM_SPEED, IRON_SPEED, STEEL_SPEED, TUNGSTEN_SPEED, NETHERITE_SPEED, TUNGSTEN_ALLOY_SPEED;
    public static final ForgeConfigSpec.DoubleValue ANDESITE_DMG_RED, COPPER_DMG_RED, BRASS_DMG_RED, ALUMINIUM_DMG_RED, IRON_DMG_RED, STEEL_DMG_RED, TUNGSTEN_DMG_RED, NETHERITE_DMG_RED, TUNGSTEN_ALLOY_DMG_RED;
    public static final ForgeConfigSpec.DoubleValue ANDESITE_CRIT, COPPER_CRIT, BRASS_CRIT, ALUMINIUM_CRIT, IRON_CRIT, STEEL_CRIT, TUNGSTEN_CRIT, NETHERITE_CRIT, TUNGSTEN_ALLOY_CRIT;
    public static final ForgeConfigSpec.IntValue ANDESITE_MOB_RANGE, COPPER_MOB_RANGE, BRASS_MOB_RANGE, ALUMINIUM_MOB_RANGE, IRON_MOB_RANGE, STEEL_MOB_RANGE, TUNGSTEN_MOB_RANGE, NETHERITE_MOB_RANGE, TUNGSTEN_ALLOY_MOB_RANGE;
    public static final ForgeConfigSpec.DoubleValue ANDESITE_REPAIR_COST, COPPER_REPAIR_COST, BRASS_REPAIR_COST, ALUMINIUM_REPAIR_COST, IRON_REPAIR_COST, STEEL_REPAIR_COST, TUNGSTEN_REPAIR_COST, NETHERITE_REPAIR_COST, TUNGSTEN_ALLOY_REPAIR_COST;
    public static final ForgeConfigSpec.DoubleValue ANDESITE_SPEED_PENALTY_RED, COPPER_SPEED_PENALTY_RED, BRASS_SPEED_PENALTY_RED, ALUMINIUM_SPEED_PENALTY_RED, IRON_SPEED_PENALTY_RED, STEEL_SPEED_PENALTY_RED, TUNGSTEN_SPEED_PENALTY_RED, NETHERITE_SPEED_PENALTY_RED, TUNGSTEN_ALLOY_SPEED_PENALTY_RED;
    public static final ForgeConfigSpec.BooleanValue ANDESITE_FIRE_RESIST, COPPER_FIRE_RESIST, BRASS_FIRE_RESIST, ALUMINIUM_FIRE_RESIST, IRON_FIRE_RESIST, STEEL_FIRE_RESIST, TUNGSTEN_FIRE_RESIST, NETHERITE_FIRE_RESIST, TUNGSTEN_ALLOY_FIRE_RESIST;
    public static final ForgeConfigSpec.IntValue ANDESITE_MOD_SLOTS, COPPER_MOD_SLOTS, BRASS_MOD_SLOTS, ALUMINIUM_MOD_SLOTS, IRON_MOD_SLOTS, STEEL_MOD_SLOTS, TUNGSTEN_MOD_SLOTS, NETHERITE_MOD_SLOTS, TUNGSTEN_ALLOY_MOD_SLOTS;
    public static final ForgeConfigSpec.ConfigValue<String> ANDESITE_REPAIR_MAT, COPPER_REPAIR_MAT, BRASS_REPAIR_MAT, ALUMINIUM_REPAIR_MAT, IRON_REPAIR_MAT, STEEL_REPAIR_MAT, TUNGSTEN_REPAIR_MAT, NETHERITE_REPAIR_MAT, TUNGSTEN_ALLOY_REPAIR_MAT;

    // Modifier configs
    public static final ForgeConfigSpec.DoubleValue OVERDRIVE_SPEED_BOOST;
    public static final ForgeConfigSpec.IntValue OVERDRIVE_COOLDOWN_TICKS;
    public static final ForgeConfigSpec.DoubleValue EMERGENCY_OVERRIDE_CRIT_REDUCTION;
    public static final ForgeConfigSpec.IntValue OPTICAL_CAMOUFLAGE_RANGE_REDUCTION;
    public static final ForgeConfigSpec.DoubleValue PRISTINE_SPEED_BOOST;
    public static final ForgeConfigSpec.DoubleValue SUPERHEATED_SPEED_BOOST;
    public static final ForgeConfigSpec.DoubleValue REINFORCED_BULKHEADS_DMG_REDUCTION;
    public static final ForgeConfigSpec.DoubleValue SHOCK_ABSORBER_BLAST_PROTECTION;
    public static final ForgeConfigSpec.DoubleValue REACTIVE_ARMOR_THORNS_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue ENGINEERS_MANUALS_REPAIR_REDUCTION;

    // Repair
    public static final ForgeConfigSpec.IntValue REPAIR_AMOUNT_PER_HIT;
    public static final ForgeConfigSpec.ConfigValue<String> BASE_REPAIR_MATERIAL;

    // Speed
    public static final ForgeConfigSpec.BooleanValue LINEAR_SPEED_MALUS;

    // Destruction
    public static final ForgeConfigSpec.BooleanValue EXPLODE_ON_DEATH;
    public static final ForgeConfigSpec.DoubleValue EXPLOSION_POWER_PER_BLOCK;
    public static final ForgeConfigSpec.BooleanValue EXPLOSION_NON_DESTRUCTIVE;

    // Critical alarm
    public static final ForgeConfigSpec.BooleanValue CRITICAL_ALARM_ENABLED;
    public static final ForgeConfigSpec.IntValue CRITICAL_ALARM_INTERVAL;

    // Mob targeting
    public static final ForgeConfigSpec.BooleanValue MOB_TARGETING_ENABLED;
    public static final ForgeConfigSpec.DoubleValue MOB_PROXIMITY_RANGE;

    // Features
    public static final ForgeConfigSpec.BooleanValue HULL_ASSEMBLY_MANDATORY;
    public static final ForgeConfigSpec.BooleanValue COLLISION_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.DoubleValue COLLISION_DAMAGE_MULTIPLIER;

    // Temperature damage
    public static final ForgeConfigSpec.BooleanValue TEMPERATURE_DAMAGE_ENABLED;
    public static final ForgeConfigSpec.IntValue TEMPERATURE_DAMAGE_INTERVAL;
    public static final ForgeConfigSpec.DoubleValue TEMPERATURE_DAMAGE_AMOUNT;
    public static final ForgeConfigSpec.DoubleValue TEMPERATURE_COLD_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue TEMPERATURE_HOT_THRESHOLD;
    public static final ForgeConfigSpec.DoubleValue TEMPERATURE_STORM_MULTIPLIER;

    // Custom items
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> CUSTOM_UPGRADES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> CUSTOM_MODIFIERS;

    // Interactive blocks
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> INTERACTIVE_BLOCKS;

    static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("health");
        BASE_TRAIN_HP = BUILDER.comment("Base HP for all trains (without hull frame)")
                .defineInRange("baseTrainHP", 50, 1, 10000);
        CRITICAL_FAILURE_THRESHOLD = BUILDER.comment("HP percentage below which train enters critical failure")
                .defineInRange("criticalFailureThreshold", 0.30, 0.0, 1.0);
        BASE_MOB_RANGE = BUILDER.comment("Base mob detection range for trains")
                .defineInRange("baseMobRange", 16.0, 0.0, 128.0);
        BUILDER.pop();

        // Hull Frames
        BUILDER.push("hullFrames");

        BUILDER.push("andesite");
        ANDESITE_HP = BUILDER.defineInRange("hp", 15, 0, 10000);
        ANDESITE_BLAST = BUILDER.defineInRange("blastProtection", 0.0, -1.0, 1.0);
        ANDESITE_PROJ = BUILDER.defineInRange("projectileProtection", 0.0, -1.0, 1.0);
        ANDESITE_SPEED = BUILDER.defineInRange("speedModifier", 0.0, -1.0, 1.0);
        ANDESITE_DMG_RED = BUILDER.defineInRange("damageReduction", 0.0, -1.0, 1.0);
        ANDESITE_CRIT = BUILDER.defineInRange("criticalThresholdModifier", 0.0, -1.0, 1.0);
        ANDESITE_MOB_RANGE = BUILDER.defineInRange("mobRangeModifier", 0, -10, 10);
        ANDESITE_REPAIR_COST = BUILDER.defineInRange("repairCostReduction", 0.0, 0.0, 1.0);
        ANDESITE_SPEED_PENALTY_RED = BUILDER.defineInRange("reducedSpeedPenalty", 0.0, 0.0, 1.0);
        ANDESITE_FIRE_RESIST = BUILDER.define("fireResistant", false);
        ANDESITE_MOD_SLOTS = BUILDER.defineInRange("modifierSlots", 0, 0, 4);
        ANDESITE_REPAIR_MAT = BUILDER.define("repairMaterial", "create:andesite_alloy");
        BUILDER.pop();

        BUILDER.push("copper");
        COPPER_HP = BUILDER.defineInRange("hp", 10, 0, 10000);
        COPPER_BLAST = BUILDER.defineInRange("blastProtection", 0.0, -1.0, 1.0);
        COPPER_PROJ = BUILDER.defineInRange("projectileProtection", 0.0, -1.0, 1.0);
        COPPER_SPEED = BUILDER.defineInRange("speedModifier", 0.0, -1.0, 1.0);
        COPPER_DMG_RED = BUILDER.defineInRange("damageReduction", 0.0, -1.0, 1.0);
        COPPER_CRIT = BUILDER.defineInRange("criticalThresholdModifier", -0.05, -1.0, 1.0);
        COPPER_MOB_RANGE = BUILDER.defineInRange("mobRangeModifier", 0, -10, 10);
        COPPER_REPAIR_COST = BUILDER.defineInRange("repairCostReduction", 0.0, 0.0, 1.0);
        COPPER_SPEED_PENALTY_RED = BUILDER.defineInRange("reducedSpeedPenalty", 0.0, 0.0, 1.0);
        COPPER_FIRE_RESIST = BUILDER.define("fireResistant", false);
        COPPER_MOD_SLOTS = BUILDER.defineInRange("modifierSlots", 0, 0, 4);
        COPPER_REPAIR_MAT = BUILDER.define("repairMaterial", "minecraft:copper_ingot");
        BUILDER.pop();

        BUILDER.push("brass");
        BRASS_HP = BUILDER.defineInRange("hp", 35, 0, 10000);
        BRASS_BLAST = BUILDER.defineInRange("blastProtection", 0.0, -1.0, 1.0);
        BRASS_PROJ = BUILDER.defineInRange("projectileProtection", 0.0, -1.0, 1.0);
        BRASS_SPEED = BUILDER.defineInRange("speedModifier", 0.0, -1.0, 1.0);
        BRASS_DMG_RED = BUILDER.defineInRange("damageReduction", 0.0, -1.0, 1.0);
        BRASS_CRIT = BUILDER.defineInRange("criticalThresholdModifier", -0.10, -1.0, 1.0);
        BRASS_MOB_RANGE = BUILDER.defineInRange("mobRangeModifier", 0, -10, 10);
        BRASS_REPAIR_COST = BUILDER.defineInRange("repairCostReduction", 0.05, 0.0, 1.0);
        BRASS_SPEED_PENALTY_RED = BUILDER.defineInRange("reducedSpeedPenalty", 0.0, 0.0, 1.0);
        BRASS_FIRE_RESIST = BUILDER.define("fireResistant", false);
        BRASS_MOD_SLOTS = BUILDER.defineInRange("modifierSlots", 1, 0, 4);
        BRASS_REPAIR_MAT = BUILDER.define("repairMaterial", "create:brass_ingot");
        BUILDER.pop();

        BUILDER.push("aluminium");
        ALUMINIUM_HP = BUILDER.defineInRange("hp", 25, 0, 10000);
        ALUMINIUM_BLAST = BUILDER.defineInRange("blastProtection", 0.0, -1.0, 1.0);
        ALUMINIUM_PROJ = BUILDER.defineInRange("projectileProtection", 0.0, -1.0, 1.0);
        ALUMINIUM_SPEED = BUILDER.defineInRange("speedModifier", 0.10, -1.0, 1.0);
        ALUMINIUM_DMG_RED = BUILDER.defineInRange("damageReduction", 0.0, -1.0, 1.0);
        ALUMINIUM_CRIT = BUILDER.defineInRange("criticalThresholdModifier", 0.05, -1.0, 1.0);
        ALUMINIUM_MOB_RANGE = BUILDER.defineInRange("mobRangeModifier", 0, -10, 10);
        ALUMINIUM_REPAIR_COST = BUILDER.defineInRange("repairCostReduction", 0.0, 0.0, 1.0);
        ALUMINIUM_SPEED_PENALTY_RED = BUILDER.defineInRange("reducedSpeedPenalty", 0.0, 0.0, 1.0);
        ALUMINIUM_FIRE_RESIST = BUILDER.define("fireResistant", false);
        ALUMINIUM_MOD_SLOTS = BUILDER.defineInRange("modifierSlots", 0, 0, 4);
        ALUMINIUM_REPAIR_MAT = BUILDER.define("repairMaterial", "forge:ingots/aluminium");
        BUILDER.pop();

        BUILDER.push("iron");
        IRON_HP = BUILDER.defineInRange("hp", 50, 0, 10000);
        IRON_BLAST = BUILDER.defineInRange("blastProtection", 0.05, -1.0, 1.0);
        IRON_PROJ = BUILDER.defineInRange("projectileProtection", 0.15, -1.0, 1.0);
        IRON_SPEED = BUILDER.defineInRange("speedModifier", -0.05, -1.0, 1.0);
        IRON_DMG_RED = BUILDER.defineInRange("damageReduction", 0.0, -1.0, 1.0);
        IRON_CRIT = BUILDER.defineInRange("criticalThresholdModifier", -0.05, -1.0, 1.0);
        IRON_MOB_RANGE = BUILDER.defineInRange("mobRangeModifier", 0, -10, 10);
        IRON_REPAIR_COST = BUILDER.defineInRange("repairCostReduction", 0.0, 0.0, 1.0);
        IRON_SPEED_PENALTY_RED = BUILDER.defineInRange("reducedSpeedPenalty", 0.0, 0.0, 1.0);
        IRON_FIRE_RESIST = BUILDER.define("fireResistant", false);
        IRON_MOD_SLOTS = BUILDER.defineInRange("modifierSlots", 1, 0, 4);
        IRON_REPAIR_MAT = BUILDER.define("repairMaterial", "minecraft:iron_ingot");
        BUILDER.pop();

        BUILDER.push("steel");
        STEEL_HP = BUILDER.defineInRange("hp", 60, 0, 10000);
        STEEL_BLAST = BUILDER.defineInRange("blastProtection", 0.15, -1.0, 1.0);
        STEEL_PROJ = BUILDER.defineInRange("projectileProtection", 0.30, -1.0, 1.0);
        STEEL_SPEED = BUILDER.defineInRange("speedModifier", -0.10, -1.0, 1.0);
        STEEL_DMG_RED = BUILDER.defineInRange("damageReduction", 0.0, -1.0, 1.0);
        STEEL_CRIT = BUILDER.defineInRange("criticalThresholdModifier", -0.07, -1.0, 1.0);
        STEEL_MOB_RANGE = BUILDER.defineInRange("mobRangeModifier", 1, -10, 10);
        STEEL_REPAIR_COST = BUILDER.defineInRange("repairCostReduction", 0.0, 0.0, 1.0);
        STEEL_SPEED_PENALTY_RED = BUILDER.defineInRange("reducedSpeedPenalty", 0.0, 0.0, 1.0);
        STEEL_FIRE_RESIST = BUILDER.define("fireResistant", false);
        STEEL_MOD_SLOTS = BUILDER.defineInRange("modifierSlots", 2, 0, 4);
        STEEL_REPAIR_MAT = BUILDER.define("repairMaterial", "create:steel_ingot");
        BUILDER.pop();

        BUILDER.push("tungsten");
        TUNGSTEN_HP = BUILDER.defineInRange("hp", 75, 0, 10000);
        TUNGSTEN_BLAST = BUILDER.defineInRange("blastProtection", 0.25, -1.0, 1.0);
        TUNGSTEN_PROJ = BUILDER.defineInRange("projectileProtection", 0.45, -1.0, 1.0);
        TUNGSTEN_SPEED = BUILDER.defineInRange("speedModifier", -0.15, -1.0, 1.0);
        TUNGSTEN_DMG_RED = BUILDER.defineInRange("damageReduction", -0.05, -1.0, 1.0);
        TUNGSTEN_CRIT = BUILDER.defineInRange("criticalThresholdModifier", -0.10, -1.0, 1.0);
        TUNGSTEN_MOB_RANGE = BUILDER.defineInRange("mobRangeModifier", 1, -10, 10);
        TUNGSTEN_REPAIR_COST = BUILDER.defineInRange("repairCostReduction", 0.0, 0.0, 1.0);
        TUNGSTEN_SPEED_PENALTY_RED = BUILDER.defineInRange("reducedSpeedPenalty", 0.0, 0.0, 1.0);
        TUNGSTEN_FIRE_RESIST = BUILDER.define("fireResistant", false);
        TUNGSTEN_MOD_SLOTS = BUILDER.defineInRange("modifierSlots", 2, 0, 4);
        TUNGSTEN_REPAIR_MAT = BUILDER.define("repairMaterial", "create_armored_rails:tungsten_ingot");
        BUILDER.pop();

        BUILDER.push("netherite");
        NETHERITE_HP = BUILDER.defineInRange("hp", 100, 0, 10000);
        NETHERITE_BLAST = BUILDER.defineInRange("blastProtection", 0.35, -1.0, 1.0);
        NETHERITE_PROJ = BUILDER.defineInRange("projectileProtection", 0.60, -1.0, 1.0);
        NETHERITE_SPEED = BUILDER.defineInRange("speedModifier", -0.15, -1.0, 1.0);
        NETHERITE_DMG_RED = BUILDER.defineInRange("damageReduction", 0.0, -1.0, 1.0);
        NETHERITE_CRIT = BUILDER.defineInRange("criticalThresholdModifier", -0.12, -1.0, 1.0);
        NETHERITE_MOB_RANGE = BUILDER.defineInRange("mobRangeModifier", 2, -10, 10);
        NETHERITE_REPAIR_COST = BUILDER.defineInRange("repairCostReduction", 0.0, 0.0, 1.0);
        NETHERITE_SPEED_PENALTY_RED = BUILDER.defineInRange("reducedSpeedPenalty", 0.05, 0.0, 1.0);
        NETHERITE_FIRE_RESIST = BUILDER.define("fireResistant", true);
        NETHERITE_MOD_SLOTS = BUILDER.defineInRange("modifierSlots", 3, 0, 4);
        NETHERITE_REPAIR_MAT = BUILDER.define("repairMaterial", "minecraft:netherite_scrap");
        BUILDER.pop();

        BUILDER.push("tungstenAlloy");
        TUNGSTEN_ALLOY_HP = BUILDER.defineInRange("hp", 120, 0, 10000);
        TUNGSTEN_ALLOY_BLAST = BUILDER.defineInRange("blastProtection", 0.50, -1.0, 1.0);
        TUNGSTEN_ALLOY_PROJ = BUILDER.defineInRange("projectileProtection", 0.75, -1.0, 1.0);
        TUNGSTEN_ALLOY_SPEED = BUILDER.defineInRange("speedModifier", -0.05, -1.0, 1.0);
        TUNGSTEN_ALLOY_DMG_RED = BUILDER.defineInRange("damageReduction", 0.0, -1.0, 1.0);
        TUNGSTEN_ALLOY_CRIT = BUILDER.defineInRange("criticalThresholdModifier", -0.15, -1.0, 1.0);
        TUNGSTEN_ALLOY_MOB_RANGE = BUILDER.defineInRange("mobRangeModifier", 2, -10, 10);
        TUNGSTEN_ALLOY_REPAIR_COST = BUILDER.defineInRange("repairCostReduction", 0.0, 0.0, 1.0);
        TUNGSTEN_ALLOY_SPEED_PENALTY_RED = BUILDER.defineInRange("reducedSpeedPenalty", 0.05, 0.0, 1.0);
        TUNGSTEN_ALLOY_FIRE_RESIST = BUILDER.define("fireResistant", true);
        TUNGSTEN_ALLOY_MOD_SLOTS = BUILDER.defineInRange("modifierSlots", 4, 0, 4);
        TUNGSTEN_ALLOY_REPAIR_MAT = BUILDER.define("repairMaterial", "create_armored_rails:tungsten_alloy_ingot");
        BUILDER.pop();

        BUILDER.pop(); // hullFrames

        // Modifiers
        BUILDER.push("modifiers");

        BUILDER.push("overdriveRegulator");
        OVERDRIVE_SPEED_BOOST = BUILDER.defineInRange("speedBoost", 0.05, 0.0, 1.0);
        OVERDRIVE_COOLDOWN_TICKS = BUILDER.defineInRange("damageCooldownTicks", 60, 1, 600);
        BUILDER.pop();

        BUILDER.push("emergencyOverrideSystem");
        EMERGENCY_OVERRIDE_CRIT_REDUCTION = BUILDER.defineInRange("criticalThresholdReduction", 0.02, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("opticalCamouflage");
        OPTICAL_CAMOUFLAGE_RANGE_REDUCTION = BUILDER.defineInRange("mobRangeReduction", 1, 0, 10);
        BUILDER.pop();

        BUILDER.push("pristineCondition");
        PRISTINE_SPEED_BOOST = BUILDER.defineInRange("speedBoostAtFullHP", 0.05, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("superheatedEngines");
        SUPERHEATED_SPEED_BOOST = BUILDER.defineInRange("speedBoost", 0.05, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("reinforcedBulkheads");
        REINFORCED_BULKHEADS_DMG_REDUCTION = BUILDER.defineInRange("damageReduction", 0.10, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("shockAbsorber");
        SHOCK_ABSORBER_BLAST_PROTECTION = BUILDER.defineInRange("blastProtection", 0.05, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.push("reactiveArmor");
        REACTIVE_ARMOR_THORNS_DAMAGE = BUILDER.defineInRange("thornsDamage", 3.0, 0.0, 100.0);
        BUILDER.pop();

        BUILDER.push("engineersManuals");
        ENGINEERS_MANUALS_REPAIR_REDUCTION = BUILDER.defineInRange("repairCostReduction", 0.05, 0.0, 1.0);
        BUILDER.pop();

        BUILDER.pop(); // modifiers

        BUILDER.push("repair");
        REPAIR_AMOUNT_PER_HIT = BUILDER.defineInRange("repairPerHit", 10, 1, 10000);
        BASE_REPAIR_MATERIAL = BUILDER.comment("Repair material for trains without a hull frame installed")
                .define("baseRepairMaterial", "minecraft:iron_nugget");
        BUILDER.pop();

        BUILDER.push("speed");
        LINEAR_SPEED_MALUS = BUILDER.define("linearSpeedMalus", true);
        BUILDER.pop();

        BUILDER.push("destruction");
        EXPLODE_ON_DEATH = BUILDER.comment("If true, trains explode and disassemble when health reaches 0")
                .define("explodeOnDeath", true);
        EXPLOSION_POWER_PER_BLOCK = BUILDER.defineInRange("explosionPowerPerBlock", 0.02, 0.0, 1.0);
        EXPLOSION_NON_DESTRUCTIVE = BUILDER.comment("If true, explosions only produce particles and knockback")
                .define("nonDestructiveExplosion", true);
        BUILDER.pop();

        BUILDER.push("criticalAlarm");
        CRITICAL_ALARM_ENABLED = BUILDER.define("enabled", true);
        CRITICAL_ALARM_INTERVAL = BUILDER.defineInRange("intervalTicks", 40, 10, 200);
        BUILDER.pop();

        BUILDER.push("mobTargeting");
        MOB_TARGETING_ENABLED = BUILDER.define("enabled", true);
        MOB_PROXIMITY_RANGE = BUILDER.comment("Mobs only attack trains if a player is within this range (0 = always attack)")
                .defineInRange("playerProximityRange", 0.0, 0.0, 256.0);
        BUILDER.pop();

        BUILDER.push("features");
        HULL_ASSEMBLY_MANDATORY = BUILDER.comment("If true, trains require a Hull Assembly block to be assembled")
                .define("hullAssemblyMandatory", true);
        COLLISION_DAMAGE_ENABLED = BUILDER.comment("If true, trains take damage when colliding with entities")
                .define("collisionDamageEnabled", true);
        COLLISION_DAMAGE_MULTIPLIER = BUILDER.defineInRange("collisionDamageMultiplier", 0.5, 0.0, 10.0);
        BUILDER.pop();

        BUILDER.push("temperatureDamage");
        TEMPERATURE_DAMAGE_ENABLED = BUILDER.comment("If true, extreme biome temperatures damage trains over time (default off)")
                .define("enabled", false);
        TEMPERATURE_DAMAGE_INTERVAL = BUILDER.comment("Ticks between temperature damage ticks")
                .defineInRange("intervalTicks", 100, 20, 1200);
        TEMPERATURE_DAMAGE_AMOUNT = BUILDER.comment("Base damage per tick from extreme temperatures")
                .defineInRange("damageAmount", 1.0, 0.1, 100.0);
        TEMPERATURE_COLD_THRESHOLD = BUILDER.comment("Biome temperature below which cold damage applies (vanilla: snowy biomes ~0.0)")
                .defineInRange("coldThreshold", 0.05, -2.0, 2.0);
        TEMPERATURE_HOT_THRESHOLD = BUILDER.comment("Biome temperature above which heat damage applies (vanilla: desert ~2.0)")
                .defineInRange("hotThreshold", 1.5, -2.0, 3.0);
        TEMPERATURE_STORM_MULTIPLIER = BUILDER.comment("Damage multiplier during rain/thunder storms")
                .defineInRange("stormMultiplier", 2.0, 1.0, 10.0);
        BUILDER.pop();

        BUILDER.push("customItems");
        CUSTOM_UPGRADES = BUILDER.comment("Format: modid:item,hp,blast,proj,speed,dmgRed,critMod,mobRange,repairCost,speedPenaltyRed,fireResist,modSlots,repairMaterial")
                .defineListAllowEmpty("customUpgrades", List.of(), o -> o instanceof String);
        CUSTOM_MODIFIERS = BUILDER.comment("Format: modid:item,damageReduction,blastProt,critReduction,speedBoost,speedBoostFullHP,speedBoostDamaged,damageCooldown,mobRangeRed,repairCostRed,thornsDmg")
                .defineListAllowEmpty("customModifiers", List.of(), o -> o instanceof String);
        BUILDER.pop();

        BUILDER.push("interactiveBlocks");
        INTERACTIVE_BLOCKS = BUILDER.comment("Blocks that can be interacted with on assembled contraptions.", "Format: modid:block_id")
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
