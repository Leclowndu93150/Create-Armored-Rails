package com.leclowndu93150.create_armored_rails.health;

public record ModifierStats(
        float damageReduction,
        float blastProtection,
        float criticalThresholdReduction,
        float speedBoost,
        float speedBoostAtFullHP,
        float speedBoostWhenDamaged,
        int damageCooldownTicks,
        int mobRangeReduction,
        float repairCostReduction,
        float thornsDamage
) {}
