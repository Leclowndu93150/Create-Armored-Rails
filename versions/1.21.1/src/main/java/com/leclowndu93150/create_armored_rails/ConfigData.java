package com.leclowndu93150.create_armored_rails;

import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;

import java.util.ArrayList;
import java.util.List;

public class ConfigData {

    @SerialEntry
    @AutoGen(category = "health")
    @IntSlider(min = 1, max = 10000, step = 1)
    public int baseTrainHP = 50;

    @SerialEntry
    @AutoGen(category = "health")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double criticalFailureThreshold = 0.30;

    @SerialEntry
    @AutoGen(category = "health")
    @DoubleSlider(min = 0, max = 128, step = 1)
    public double baseMobRange = 16.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @IntSlider(min = 0, max = 10000, step = 1)
    public int andesiteHp = 15;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double andesiteBlast = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double andesiteProj = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double andesiteSpeed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double andesiteDmgRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double andesiteCrit = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @IntSlider(min = -10, max = 10, step = 1)
    public int andesiteMobRange = 0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double andesiteRepairCost = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double andesiteSpeedPenaltyRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @TickBox
    public boolean andesiteFireResist = false;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @IntSlider(min = 0, max = 4, step = 1)
    public int andesiteModSlots = 0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "andesite")
    @StringField
    public String andesiteRepairMat = "create:andesite_alloy";

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @IntSlider(min = 0, max = 10000, step = 1)
    public int copperHp = 10;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double copperBlast = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double copperProj = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double copperSpeed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double copperDmgRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double copperCrit = -0.05;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @IntSlider(min = -10, max = 10, step = 1)
    public int copperMobRange = 0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double copperRepairCost = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double copperSpeedPenaltyRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @TickBox
    public boolean copperFireResist = false;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @IntSlider(min = 0, max = 4, step = 1)
    public int copperModSlots = 0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "copper")
    @StringField
    public String copperRepairMat = "minecraft:copper_ingot";

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @IntSlider(min = 0, max = 10000, step = 1)
    public int brassHp = 35;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double brassBlast = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double brassProj = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double brassSpeed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double brassDmgRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double brassCrit = -0.10;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @IntSlider(min = -10, max = 10, step = 1)
    public int brassMobRange = 0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double brassRepairCost = 0.05;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double brassSpeedPenaltyRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @TickBox
    public boolean brassFireResist = false;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @IntSlider(min = 0, max = 4, step = 1)
    public int brassModSlots = 1;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "brass")
    @StringField
    public String brassRepairMat = "create:brass_ingot";

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @IntSlider(min = 0, max = 10000, step = 1)
    public int aluminiumHp = 25;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double aluminiumBlast = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double aluminiumProj = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double aluminiumSpeed = 0.10;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double aluminiumDmgRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double aluminiumCrit = 0.05;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @IntSlider(min = -10, max = 10, step = 1)
    public int aluminiumMobRange = 0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double aluminiumRepairCost = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double aluminiumSpeedPenaltyRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @TickBox
    public boolean aluminiumFireResist = false;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @IntSlider(min = 0, max = 4, step = 1)
    public int aluminiumModSlots = 0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "aluminium")
    @StringField
    public String aluminiumRepairMat = "forge:ingots/aluminium";

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @IntSlider(min = 0, max = 10000, step = 1)
    public int ironHp = 50;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double ironBlast = 0.05;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double ironProj = 0.15;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double ironSpeed = -0.05;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double ironDmgRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double ironCrit = -0.05;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @IntSlider(min = -10, max = 10, step = 1)
    public int ironMobRange = 0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double ironRepairCost = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double ironSpeedPenaltyRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @TickBox
    public boolean ironFireResist = false;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @IntSlider(min = 0, max = 4, step = 1)
    public int ironModSlots = 1;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "iron")
    @StringField
    public String ironRepairMat = "minecraft:iron_ingot";

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @IntSlider(min = 0, max = 10000, step = 1)
    public int steelHp = 60;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double steelBlast = 0.15;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double steelProj = 0.30;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double steelSpeed = -0.10;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double steelDmgRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double steelCrit = -0.07;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @IntSlider(min = -10, max = 10, step = 1)
    public int steelMobRange = 1;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double steelRepairCost = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double steelSpeedPenaltyRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @TickBox
    public boolean steelFireResist = false;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @IntSlider(min = 0, max = 4, step = 1)
    public int steelModSlots = 2;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "steel")
    @StringField
    public String steelRepairMat = "create:steel_ingot";

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @IntSlider(min = 0, max = 10000, step = 1)
    public int tungstenHp = 75;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double tungstenBlast = 0.25;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double tungstenProj = 0.45;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double tungstenSpeed = -0.15;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double tungstenDmgRed = -0.05;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double tungstenCrit = -0.10;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @IntSlider(min = -10, max = 10, step = 1)
    public int tungstenMobRange = 1;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double tungstenRepairCost = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double tungstenSpeedPenaltyRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @TickBox
    public boolean tungstenFireResist = false;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @IntSlider(min = 0, max = 4, step = 1)
    public int tungstenModSlots = 2;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten")
    @StringField
    public String tungstenRepairMat = "create_armored_rails:tungsten_ingot";

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @IntSlider(min = 0, max = 10000, step = 1)
    public int netheriteHp = 100;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double netheriteBlast = 0.35;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double netheriteProj = 0.60;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double netheriteSpeed = -0.15;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double netheriteDmgRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double netheriteCrit = -0.12;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @IntSlider(min = -10, max = 10, step = 1)
    public int netheriteMobRange = 2;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double netheriteRepairCost = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double netheriteSpeedPenaltyRed = 0.05;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @TickBox
    public boolean netheriteFireResist = true;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @IntSlider(min = 0, max = 4, step = 1)
    public int netheriteModSlots = 3;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "netherite")
    @StringField
    public String netheriteRepairMat = "minecraft:netherite_scrap";

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @IntSlider(min = 0, max = 10000, step = 1)
    public int tungstenAlloyHp = 120;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double tungstenAlloyBlast = 0.50;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double tungstenAlloyProj = 0.75;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double tungstenAlloySpeed = -0.05;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double tungstenAlloyDmgRed = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @DoubleSlider(min = -1, max = 1, step = 0.01)
    public double tungstenAlloyCrit = -0.15;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @IntSlider(min = -10, max = 10, step = 1)
    public int tungstenAlloyMobRange = 2;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double tungstenAlloyRepairCost = 0.0;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double tungstenAlloySpeedPenaltyRed = 0.05;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @TickBox
    public boolean tungstenAlloyFireResist = true;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @IntSlider(min = 0, max = 4, step = 1)
    public int tungstenAlloyModSlots = 4;

    @SerialEntry
    @AutoGen(category = "hull_frames", group = "tungsten_alloy")
    @StringField
    public String tungstenAlloyRepairMat = "create_armored_rails:tungsten_alloy_ingot";

    @SerialEntry
    @AutoGen(category = "modifiers")
    @DoubleField(min = 0, max = 1)
    public double overdriveSpeedBoost = 0.05;

    @SerialEntry
    @AutoGen(category = "modifiers")
    @IntField(min = 1, max = 600)
    public int overdriveCooldownTicks = 60;

    @SerialEntry
    @AutoGen(category = "modifiers")
    @DoubleField(min = 0, max = 1)
    public double emergencyOverrideCritReduction = 0.02;

    @SerialEntry
    @AutoGen(category = "modifiers")
    @IntField(min = 0, max = 10)
    public int opticalCamouflageRangeReduction = 1;

    @SerialEntry
    @AutoGen(category = "modifiers")
    @DoubleField(min = 0, max = 1)
    public double pristineSpeedBoost = 0.05;

    @SerialEntry
    @AutoGen(category = "modifiers")
    @DoubleField(min = 0, max = 1)
    public double superheatedSpeedBoost = 0.05;

    @SerialEntry
    @AutoGen(category = "modifiers")
    @DoubleField(min = 0, max = 1)
    public double reinforcedBulkheadsDmgReduction = 0.10;

    @SerialEntry
    @AutoGen(category = "modifiers")
    @DoubleField(min = 0, max = 1)
    public double shockAbsorberBlastProtection = 0.05;

    @SerialEntry
    @AutoGen(category = "modifiers")
    @DoubleField(min = 0, max = 100)
    public double reactiveArmorThornsDamage = 3.0;

    @SerialEntry
    @AutoGen(category = "modifiers")
    @DoubleField(min = 0, max = 1)
    public double engineersManualsRepairReduction = 0.05;

    @SerialEntry
    @AutoGen(category = "repair")
    @IntSlider(min = 1, max = 10000, step = 1)
    public int repairAmountPerHit = 10;

    @SerialEntry
    @AutoGen(category = "repair")
    @StringField
    public String baseRepairMaterial = "minecraft:iron_nugget";

    @SerialEntry
    @AutoGen(category = "mechanics")
    @TickBox
    public boolean linearSpeedMalus = true;

    @SerialEntry
    @AutoGen(category = "mechanics")
    @TickBox
    public boolean explodeOnDeath = true;

    @SerialEntry
    @AutoGen(category = "mechanics")
    @DoubleSlider(min = 0, max = 1, step = 0.01)
    public double explosionPowerPerBlock = 0.02;

    @SerialEntry
    @AutoGen(category = "mechanics")
    @TickBox
    public boolean explosionNonDestructive = true;

    @SerialEntry
    @AutoGen(category = "mechanics")
    @TickBox
    public boolean criticalAlarmEnabled = true;

    @SerialEntry
    @AutoGen(category = "mechanics")
    @IntSlider(min = 10, max = 200, step = 1)
    public int criticalAlarmInterval = 40;

    @SerialEntry
    @AutoGen(category = "mechanics")
    @TickBox
    public boolean mobTargetingEnabled = true;

    @SerialEntry
    @AutoGen(category = "mechanics")
    @DoubleSlider(min = 0, max = 256, step = 1)
    public double mobProximityRange = 0.0;

    @SerialEntry
    @AutoGen(category = "mechanics")
    @TickBox
    public boolean hullAssemblyMandatory = true;

    @SerialEntry
    @AutoGen(category = "mechanics")
    @TickBox
    public boolean collisionDamageEnabled = true;

    @SerialEntry
    @AutoGen(category = "mechanics")
    @DoubleSlider(min = 0, max = 10, step = 0.1)
    public double collisionDamageMultiplier = 0.5;

    @SerialEntry
    @AutoGen(category = "temperature")
    @TickBox
    public boolean temperatureDamageEnabled = false;

    @SerialEntry
    @AutoGen(category = "temperature")
    @IntSlider(min = 20, max = 1200, step = 1)
    public int temperatureDamageInterval = 100;

    @SerialEntry
    @AutoGen(category = "temperature")
    @DoubleSlider(min = 0.1, max = 100, step = 0.1)
    public double temperatureDamageAmount = 1.0;

    @SerialEntry
    @AutoGen(category = "temperature")
    @DoubleSlider(min = -2, max = 2, step = 0.01)
    public double temperatureColdThreshold = 0.05;

    @SerialEntry
    @AutoGen(category = "temperature")
    @DoubleSlider(min = -2, max = 3, step = 0.01)
    public double temperatureHotThreshold = 1.5;

    @SerialEntry
    @AutoGen(category = "temperature")
    @DoubleSlider(min = 1, max = 10, step = 0.1)
    public double temperatureStormMultiplier = 2.0;

    @SerialEntry
    public List<String> customUpgrades = new ArrayList<>();

    @SerialEntry
    public List<String> customModifiers = new ArrayList<>();

    @SerialEntry
    public List<String> interactiveBlocks = new ArrayList<>(List.of(
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
    ));
}
