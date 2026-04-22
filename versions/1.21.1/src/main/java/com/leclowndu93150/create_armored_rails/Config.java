package com.leclowndu93150.create_armored_rails;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;

import java.util.List;

public class Config {
    public static final ConfigClassHandler<ConfigData> HANDLER = ConfigClassHandler.createBuilder(ConfigData.class)
            .id(ResourceLocation.fromNamespaceAndPath(CreateArmoredRails.MODID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FMLPaths.CONFIGDIR.get().resolve("create_armored_rails.json5"))
                    .setJson5(true)
                    .build())
            .build();

    private static ConfigData d() { return HANDLER.instance(); }

    public static void load() { HANDLER.load(); }
    public static void save() { HANDLER.save(); }

    public static final ConfigValue<Integer> BASE_TRAIN_HP = new ConfigValue<>(() -> d().baseTrainHP);
    public static final ConfigValue<Double> CRITICAL_FAILURE_THRESHOLD = new ConfigValue<>(() -> d().criticalFailureThreshold);
    public static final ConfigValue<Double> BASE_MOB_RANGE = new ConfigValue<>(() -> d().baseMobRange);

    public static final ConfigValue<Integer> ANDESITE_HP = new ConfigValue<>(() -> d().andesiteHp);
    public static final ConfigValue<Double> ANDESITE_BLAST = new ConfigValue<>(() -> d().andesiteBlast);
    public static final ConfigValue<Double> ANDESITE_PROJ = new ConfigValue<>(() -> d().andesiteProj);
    public static final ConfigValue<Double> ANDESITE_SPEED = new ConfigValue<>(() -> d().andesiteSpeed);
    public static final ConfigValue<Double> ANDESITE_DMG_RED = new ConfigValue<>(() -> d().andesiteDmgRed);
    public static final ConfigValue<Double> ANDESITE_CRIT = new ConfigValue<>(() -> d().andesiteCrit);
    public static final ConfigValue<Integer> ANDESITE_MOB_RANGE = new ConfigValue<>(() -> d().andesiteMobRange);
    public static final ConfigValue<Double> ANDESITE_REPAIR_COST = new ConfigValue<>(() -> d().andesiteRepairCost);
    public static final ConfigValue<Double> ANDESITE_SPEED_PENALTY_RED = new ConfigValue<>(() -> d().andesiteSpeedPenaltyRed);
    public static final ConfigValue<Boolean> ANDESITE_FIRE_RESIST = new ConfigValue<>(() -> d().andesiteFireResist);
    public static final ConfigValue<Integer> ANDESITE_MOD_SLOTS = new ConfigValue<>(() -> d().andesiteModSlots);
    public static final ConfigValue<String> ANDESITE_REPAIR_MAT = new ConfigValue<>(() -> d().andesiteRepairMat);

    public static final ConfigValue<Integer> COPPER_HP = new ConfigValue<>(() -> d().copperHp);
    public static final ConfigValue<Double> COPPER_BLAST = new ConfigValue<>(() -> d().copperBlast);
    public static final ConfigValue<Double> COPPER_PROJ = new ConfigValue<>(() -> d().copperProj);
    public static final ConfigValue<Double> COPPER_SPEED = new ConfigValue<>(() -> d().copperSpeed);
    public static final ConfigValue<Double> COPPER_DMG_RED = new ConfigValue<>(() -> d().copperDmgRed);
    public static final ConfigValue<Double> COPPER_CRIT = new ConfigValue<>(() -> d().copperCrit);
    public static final ConfigValue<Integer> COPPER_MOB_RANGE = new ConfigValue<>(() -> d().copperMobRange);
    public static final ConfigValue<Double> COPPER_REPAIR_COST = new ConfigValue<>(() -> d().copperRepairCost);
    public static final ConfigValue<Double> COPPER_SPEED_PENALTY_RED = new ConfigValue<>(() -> d().copperSpeedPenaltyRed);
    public static final ConfigValue<Boolean> COPPER_FIRE_RESIST = new ConfigValue<>(() -> d().copperFireResist);
    public static final ConfigValue<Integer> COPPER_MOD_SLOTS = new ConfigValue<>(() -> d().copperModSlots);
    public static final ConfigValue<String> COPPER_REPAIR_MAT = new ConfigValue<>(() -> d().copperRepairMat);

    public static final ConfigValue<Integer> BRASS_HP = new ConfigValue<>(() -> d().brassHp);
    public static final ConfigValue<Double> BRASS_BLAST = new ConfigValue<>(() -> d().brassBlast);
    public static final ConfigValue<Double> BRASS_PROJ = new ConfigValue<>(() -> d().brassProj);
    public static final ConfigValue<Double> BRASS_SPEED = new ConfigValue<>(() -> d().brassSpeed);
    public static final ConfigValue<Double> BRASS_DMG_RED = new ConfigValue<>(() -> d().brassDmgRed);
    public static final ConfigValue<Double> BRASS_CRIT = new ConfigValue<>(() -> d().brassCrit);
    public static final ConfigValue<Integer> BRASS_MOB_RANGE = new ConfigValue<>(() -> d().brassMobRange);
    public static final ConfigValue<Double> BRASS_REPAIR_COST = new ConfigValue<>(() -> d().brassRepairCost);
    public static final ConfigValue<Double> BRASS_SPEED_PENALTY_RED = new ConfigValue<>(() -> d().brassSpeedPenaltyRed);
    public static final ConfigValue<Boolean> BRASS_FIRE_RESIST = new ConfigValue<>(() -> d().brassFireResist);
    public static final ConfigValue<Integer> BRASS_MOD_SLOTS = new ConfigValue<>(() -> d().brassModSlots);
    public static final ConfigValue<String> BRASS_REPAIR_MAT = new ConfigValue<>(() -> d().brassRepairMat);

    public static final ConfigValue<Integer> ALUMINIUM_HP = new ConfigValue<>(() -> d().aluminiumHp);
    public static final ConfigValue<Double> ALUMINIUM_BLAST = new ConfigValue<>(() -> d().aluminiumBlast);
    public static final ConfigValue<Double> ALUMINIUM_PROJ = new ConfigValue<>(() -> d().aluminiumProj);
    public static final ConfigValue<Double> ALUMINIUM_SPEED = new ConfigValue<>(() -> d().aluminiumSpeed);
    public static final ConfigValue<Double> ALUMINIUM_DMG_RED = new ConfigValue<>(() -> d().aluminiumDmgRed);
    public static final ConfigValue<Double> ALUMINIUM_CRIT = new ConfigValue<>(() -> d().aluminiumCrit);
    public static final ConfigValue<Integer> ALUMINIUM_MOB_RANGE = new ConfigValue<>(() -> d().aluminiumMobRange);
    public static final ConfigValue<Double> ALUMINIUM_REPAIR_COST = new ConfigValue<>(() -> d().aluminiumRepairCost);
    public static final ConfigValue<Double> ALUMINIUM_SPEED_PENALTY_RED = new ConfigValue<>(() -> d().aluminiumSpeedPenaltyRed);
    public static final ConfigValue<Boolean> ALUMINIUM_FIRE_RESIST = new ConfigValue<>(() -> d().aluminiumFireResist);
    public static final ConfigValue<Integer> ALUMINIUM_MOD_SLOTS = new ConfigValue<>(() -> d().aluminiumModSlots);
    public static final ConfigValue<String> ALUMINIUM_REPAIR_MAT = new ConfigValue<>(() -> d().aluminiumRepairMat);

    public static final ConfigValue<Integer> IRON_HP = new ConfigValue<>(() -> d().ironHp);
    public static final ConfigValue<Double> IRON_BLAST = new ConfigValue<>(() -> d().ironBlast);
    public static final ConfigValue<Double> IRON_PROJ = new ConfigValue<>(() -> d().ironProj);
    public static final ConfigValue<Double> IRON_SPEED = new ConfigValue<>(() -> d().ironSpeed);
    public static final ConfigValue<Double> IRON_DMG_RED = new ConfigValue<>(() -> d().ironDmgRed);
    public static final ConfigValue<Double> IRON_CRIT = new ConfigValue<>(() -> d().ironCrit);
    public static final ConfigValue<Integer> IRON_MOB_RANGE = new ConfigValue<>(() -> d().ironMobRange);
    public static final ConfigValue<Double> IRON_REPAIR_COST = new ConfigValue<>(() -> d().ironRepairCost);
    public static final ConfigValue<Double> IRON_SPEED_PENALTY_RED = new ConfigValue<>(() -> d().ironSpeedPenaltyRed);
    public static final ConfigValue<Boolean> IRON_FIRE_RESIST = new ConfigValue<>(() -> d().ironFireResist);
    public static final ConfigValue<Integer> IRON_MOD_SLOTS = new ConfigValue<>(() -> d().ironModSlots);
    public static final ConfigValue<String> IRON_REPAIR_MAT = new ConfigValue<>(() -> d().ironRepairMat);

    public static final ConfigValue<Integer> STEEL_HP = new ConfigValue<>(() -> d().steelHp);
    public static final ConfigValue<Double> STEEL_BLAST = new ConfigValue<>(() -> d().steelBlast);
    public static final ConfigValue<Double> STEEL_PROJ = new ConfigValue<>(() -> d().steelProj);
    public static final ConfigValue<Double> STEEL_SPEED = new ConfigValue<>(() -> d().steelSpeed);
    public static final ConfigValue<Double> STEEL_DMG_RED = new ConfigValue<>(() -> d().steelDmgRed);
    public static final ConfigValue<Double> STEEL_CRIT = new ConfigValue<>(() -> d().steelCrit);
    public static final ConfigValue<Integer> STEEL_MOB_RANGE = new ConfigValue<>(() -> d().steelMobRange);
    public static final ConfigValue<Double> STEEL_REPAIR_COST = new ConfigValue<>(() -> d().steelRepairCost);
    public static final ConfigValue<Double> STEEL_SPEED_PENALTY_RED = new ConfigValue<>(() -> d().steelSpeedPenaltyRed);
    public static final ConfigValue<Boolean> STEEL_FIRE_RESIST = new ConfigValue<>(() -> d().steelFireResist);
    public static final ConfigValue<Integer> STEEL_MOD_SLOTS = new ConfigValue<>(() -> d().steelModSlots);
    public static final ConfigValue<String> STEEL_REPAIR_MAT = new ConfigValue<>(() -> d().steelRepairMat);

    public static final ConfigValue<Integer> TUNGSTEN_HP = new ConfigValue<>(() -> d().tungstenHp);
    public static final ConfigValue<Double> TUNGSTEN_BLAST = new ConfigValue<>(() -> d().tungstenBlast);
    public static final ConfigValue<Double> TUNGSTEN_PROJ = new ConfigValue<>(() -> d().tungstenProj);
    public static final ConfigValue<Double> TUNGSTEN_SPEED = new ConfigValue<>(() -> d().tungstenSpeed);
    public static final ConfigValue<Double> TUNGSTEN_DMG_RED = new ConfigValue<>(() -> d().tungstenDmgRed);
    public static final ConfigValue<Double> TUNGSTEN_CRIT = new ConfigValue<>(() -> d().tungstenCrit);
    public static final ConfigValue<Integer> TUNGSTEN_MOB_RANGE = new ConfigValue<>(() -> d().tungstenMobRange);
    public static final ConfigValue<Double> TUNGSTEN_REPAIR_COST = new ConfigValue<>(() -> d().tungstenRepairCost);
    public static final ConfigValue<Double> TUNGSTEN_SPEED_PENALTY_RED = new ConfigValue<>(() -> d().tungstenSpeedPenaltyRed);
    public static final ConfigValue<Boolean> TUNGSTEN_FIRE_RESIST = new ConfigValue<>(() -> d().tungstenFireResist);
    public static final ConfigValue<Integer> TUNGSTEN_MOD_SLOTS = new ConfigValue<>(() -> d().tungstenModSlots);
    public static final ConfigValue<String> TUNGSTEN_REPAIR_MAT = new ConfigValue<>(() -> d().tungstenRepairMat);

    public static final ConfigValue<Integer> NETHERITE_HP = new ConfigValue<>(() -> d().netheriteHp);
    public static final ConfigValue<Double> NETHERITE_BLAST = new ConfigValue<>(() -> d().netheriteBlast);
    public static final ConfigValue<Double> NETHERITE_PROJ = new ConfigValue<>(() -> d().netheriteProj);
    public static final ConfigValue<Double> NETHERITE_SPEED = new ConfigValue<>(() -> d().netheriteSpeed);
    public static final ConfigValue<Double> NETHERITE_DMG_RED = new ConfigValue<>(() -> d().netheriteDmgRed);
    public static final ConfigValue<Double> NETHERITE_CRIT = new ConfigValue<>(() -> d().netheriteCrit);
    public static final ConfigValue<Integer> NETHERITE_MOB_RANGE = new ConfigValue<>(() -> d().netheriteMobRange);
    public static final ConfigValue<Double> NETHERITE_REPAIR_COST = new ConfigValue<>(() -> d().netheriteRepairCost);
    public static final ConfigValue<Double> NETHERITE_SPEED_PENALTY_RED = new ConfigValue<>(() -> d().netheriteSpeedPenaltyRed);
    public static final ConfigValue<Boolean> NETHERITE_FIRE_RESIST = new ConfigValue<>(() -> d().netheriteFireResist);
    public static final ConfigValue<Integer> NETHERITE_MOD_SLOTS = new ConfigValue<>(() -> d().netheriteModSlots);
    public static final ConfigValue<String> NETHERITE_REPAIR_MAT = new ConfigValue<>(() -> d().netheriteRepairMat);

    public static final ConfigValue<Integer> TUNGSTEN_ALLOY_HP = new ConfigValue<>(() -> d().tungstenAlloyHp);
    public static final ConfigValue<Double> TUNGSTEN_ALLOY_BLAST = new ConfigValue<>(() -> d().tungstenAlloyBlast);
    public static final ConfigValue<Double> TUNGSTEN_ALLOY_PROJ = new ConfigValue<>(() -> d().tungstenAlloyProj);
    public static final ConfigValue<Double> TUNGSTEN_ALLOY_SPEED = new ConfigValue<>(() -> d().tungstenAlloySpeed);
    public static final ConfigValue<Double> TUNGSTEN_ALLOY_DMG_RED = new ConfigValue<>(() -> d().tungstenAlloyDmgRed);
    public static final ConfigValue<Double> TUNGSTEN_ALLOY_CRIT = new ConfigValue<>(() -> d().tungstenAlloyCrit);
    public static final ConfigValue<Integer> TUNGSTEN_ALLOY_MOB_RANGE = new ConfigValue<>(() -> d().tungstenAlloyMobRange);
    public static final ConfigValue<Double> TUNGSTEN_ALLOY_REPAIR_COST = new ConfigValue<>(() -> d().tungstenAlloyRepairCost);
    public static final ConfigValue<Double> TUNGSTEN_ALLOY_SPEED_PENALTY_RED = new ConfigValue<>(() -> d().tungstenAlloySpeedPenaltyRed);
    public static final ConfigValue<Boolean> TUNGSTEN_ALLOY_FIRE_RESIST = new ConfigValue<>(() -> d().tungstenAlloyFireResist);
    public static final ConfigValue<Integer> TUNGSTEN_ALLOY_MOD_SLOTS = new ConfigValue<>(() -> d().tungstenAlloyModSlots);
    public static final ConfigValue<String> TUNGSTEN_ALLOY_REPAIR_MAT = new ConfigValue<>(() -> d().tungstenAlloyRepairMat);

    public static final ConfigValue<Double> OVERDRIVE_SPEED_BOOST = new ConfigValue<>(() -> d().overdriveSpeedBoost);
    public static final ConfigValue<Integer> OVERDRIVE_COOLDOWN_TICKS = new ConfigValue<>(() -> d().overdriveCooldownTicks);
    public static final ConfigValue<Double> EMERGENCY_OVERRIDE_CRIT_REDUCTION = new ConfigValue<>(() -> d().emergencyOverrideCritReduction);
    public static final ConfigValue<Integer> OPTICAL_CAMOUFLAGE_RANGE_REDUCTION = new ConfigValue<>(() -> d().opticalCamouflageRangeReduction);
    public static final ConfigValue<Double> PRISTINE_SPEED_BOOST = new ConfigValue<>(() -> d().pristineSpeedBoost);
    public static final ConfigValue<Double> SUPERHEATED_SPEED_BOOST = new ConfigValue<>(() -> d().superheatedSpeedBoost);
    public static final ConfigValue<Double> REINFORCED_BULKHEADS_DMG_REDUCTION = new ConfigValue<>(() -> d().reinforcedBulkheadsDmgReduction);
    public static final ConfigValue<Double> SHOCK_ABSORBER_BLAST_PROTECTION = new ConfigValue<>(() -> d().shockAbsorberBlastProtection);
    public static final ConfigValue<Double> REACTIVE_ARMOR_THORNS_DAMAGE = new ConfigValue<>(() -> d().reactiveArmorThornsDamage);
    public static final ConfigValue<Double> ENGINEERS_MANUALS_REPAIR_REDUCTION = new ConfigValue<>(() -> d().engineersManualsRepairReduction);

    public static final ConfigValue<Integer> REPAIR_AMOUNT_PER_HIT = new ConfigValue<>(() -> d().repairAmountPerHit);
    public static final ConfigValue<String> BASE_REPAIR_MATERIAL = new ConfigValue<>(() -> d().baseRepairMaterial);

    public static final ConfigValue<Boolean> LINEAR_SPEED_MALUS = new ConfigValue<>(() -> d().linearSpeedMalus);
    public static final ConfigValue<Boolean> EXPLODE_ON_DEATH = new ConfigValue<>(() -> d().explodeOnDeath);
    public static final ConfigValue<Double> EXPLOSION_POWER_PER_BLOCK = new ConfigValue<>(() -> d().explosionPowerPerBlock);
    public static final ConfigValue<Boolean> EXPLOSION_NON_DESTRUCTIVE = new ConfigValue<>(() -> d().explosionNonDestructive);
    public static final ConfigValue<Boolean> CRITICAL_ALARM_ENABLED = new ConfigValue<>(() -> d().criticalAlarmEnabled);
    public static final ConfigValue<Integer> CRITICAL_ALARM_INTERVAL = new ConfigValue<>(() -> d().criticalAlarmInterval);
    public static final ConfigValue<Boolean> MOB_TARGETING_ENABLED = new ConfigValue<>(() -> d().mobTargetingEnabled);
    public static final ConfigValue<Double> MOB_PROXIMITY_RANGE = new ConfigValue<>(() -> d().mobProximityRange);
    public static final ConfigValue<Boolean> HULL_ASSEMBLY_MANDATORY = new ConfigValue<>(() -> d().hullAssemblyMandatory);
    public static final ConfigValue<Boolean> COLLISION_DAMAGE_ENABLED = new ConfigValue<>(() -> d().collisionDamageEnabled);
    public static final ConfigValue<Double> COLLISION_DAMAGE_MULTIPLIER = new ConfigValue<>(() -> d().collisionDamageMultiplier);

    public static final ConfigValue<Boolean> TEMPERATURE_DAMAGE_ENABLED = new ConfigValue<>(() -> d().temperatureDamageEnabled);
    public static final ConfigValue<Integer> TEMPERATURE_DAMAGE_INTERVAL = new ConfigValue<>(() -> d().temperatureDamageInterval);
    public static final ConfigValue<Double> TEMPERATURE_DAMAGE_AMOUNT = new ConfigValue<>(() -> d().temperatureDamageAmount);
    public static final ConfigValue<Double> TEMPERATURE_COLD_THRESHOLD = new ConfigValue<>(() -> d().temperatureColdThreshold);
    public static final ConfigValue<Double> TEMPERATURE_HOT_THRESHOLD = new ConfigValue<>(() -> d().temperatureHotThreshold);
    public static final ConfigValue<Double> TEMPERATURE_STORM_MULTIPLIER = new ConfigValue<>(() -> d().temperatureStormMultiplier);

    @SuppressWarnings("unchecked")
    public static final ConfigValue<List<? extends String>> CUSTOM_UPGRADES = new ConfigValue<>(() -> d().customUpgrades);
    @SuppressWarnings("unchecked")
    public static final ConfigValue<List<? extends String>> CUSTOM_MODIFIERS = new ConfigValue<>(() -> d().customModifiers);
    @SuppressWarnings("unchecked")
    public static final ConfigValue<List<? extends String>> INTERACTIVE_BLOCKS = new ConfigValue<>(() -> d().interactiveBlocks);
}
