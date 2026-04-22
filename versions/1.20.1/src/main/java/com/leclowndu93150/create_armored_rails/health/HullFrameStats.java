package com.leclowndu93150.create_armored_rails.health;

public record HullFrameStats(
        float hpBonus,
        float blastProtection,
        float projectileProtection,
        float speedModifier,
        float damageReduction,
        float criticalThresholdModifier,
        int mobRangeModifier,
        float repairCostReduction,
        float reducedSpeedPenalty,
        boolean fireResistant,
        int modifierSlots,
        String repairMaterial
) {}
