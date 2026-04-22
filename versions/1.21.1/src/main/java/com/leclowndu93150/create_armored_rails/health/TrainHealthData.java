package com.leclowndu93150.create_armored_rails.health;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public class TrainHealthData {
    private float maxHP;
    private float currentHP;
    private float damageReduction;
    private float criticalThresholdReduction;
    private float blastProtection;
    private float projectileProtection;
    private float speedModifier;
    private int mobRangeModifier;
    private float repairCostReduction;
    private boolean fireResistant;
    private float reducedSpeedPenalty;
    private float thornsDamage;
    private float conditionalSpeedBoostFullHP;
    private float conditionalSpeedBoostDamaged;
    private int damageCooldownTicks;
    private float flatSpeedBoost;
    private long lastDamagedTick;

    public TrainHealthData(float maxHP, float damageReduction, float criticalThresholdReduction) {
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.damageReduction = damageReduction;
        this.criticalThresholdReduction = criticalThresholdReduction;
    }

    public TrainHealthData() {
        this(0, 0, 0);
    }

    public float getMaxHP() { return maxHP; }
    public float getCurrentHP() { return currentHP; }
    public float getDamageReduction() { return damageReduction; }
    public float getCriticalThresholdReduction() { return criticalThresholdReduction; }
    public float getBlastProtection() { return blastProtection; }
    public float getProjectileProtection() { return projectileProtection; }
    public float getSpeedModifier() { return speedModifier; }
    public int getMobRangeModifier() { return mobRangeModifier; }
    public float getRepairCostReduction() { return repairCostReduction; }
    public boolean isFireResistant() { return fireResistant; }
    public float getReducedSpeedPenalty() { return reducedSpeedPenalty; }
    public float getThornsDamage() { return thornsDamage; }
    public float getConditionalSpeedBoostFullHP() { return conditionalSpeedBoostFullHP; }
    public float getConditionalSpeedBoostDamaged() { return conditionalSpeedBoostDamaged; }
    public int getDamageCooldownTicks() { return damageCooldownTicks; }
    public float getFlatSpeedBoost() { return flatSpeedBoost; }
    public long getLastDamagedTick() { return lastDamagedTick; }

    public void setMaxHP(float maxHP) { this.maxHP = maxHP; }
    public void setCurrentHP(float currentHP) { this.currentHP = Mth.clamp(currentHP, 0, maxHP); }
    public void setDamageReduction(float v) { this.damageReduction = v; }
    public void setCriticalThresholdReduction(float v) { this.criticalThresholdReduction = v; }
    public void setBlastProtection(float v) { this.blastProtection = v; }
    public void setProjectileProtection(float v) { this.projectileProtection = v; }
    public void setSpeedModifier(float v) { this.speedModifier = v; }
    public void setMobRangeModifier(int v) { this.mobRangeModifier = v; }
    public void setRepairCostReduction(float v) { this.repairCostReduction = v; }
    public void setFireResistant(boolean v) { this.fireResistant = v; }
    public void setReducedSpeedPenalty(float v) { this.reducedSpeedPenalty = v; }
    public void setThornsDamage(float v) { this.thornsDamage = v; }
    public void setConditionalSpeedBoostFullHP(float v) { this.conditionalSpeedBoostFullHP = v; }
    public void setConditionalSpeedBoostDamaged(float v) { this.conditionalSpeedBoostDamaged = v; }
    public void setDamageCooldownTicks(int v) { this.damageCooldownTicks = v; }
    public void setFlatSpeedBoost(float v) { this.flatSpeedBoost = v; }
    public void setLastDamagedTick(long v) { this.lastDamagedTick = v; }

    public float getHealthPercentage() {
        return maxHP > 0 ? currentHP / maxHP : 0f;
    }

    public boolean isCriticalFailure(double configuredThreshold) {
        float effectiveThreshold = (float)(configuredThreshold - criticalThresholdReduction);
        return maxHP > 0 && getHealthPercentage() < effectiveThreshold;
    }

    public float getSpeedMultiplier() {
        if (maxHP <= 0) return 1f;
        float base = 1.0f + speedModifier;
        float healthPct = Mth.clamp(getHealthPercentage(), 0f, 1f);
        float healthPenalty = (1f - healthPct) * (1f - reducedSpeedPenalty);
        float result = base * (1f - healthPenalty);
        result += flatSpeedBoost;
        if (conditionalSpeedBoostFullHP > 0 && currentHP >= maxHP) {
            result += conditionalSpeedBoostFullHP;
        }
        return Mth.clamp(result, 0f, 2f);
    }

    public float getSpeedMultiplierWithDamageBoost(long currentTick) {
        float base = getSpeedMultiplier();
        if (conditionalSpeedBoostDamaged > 0 && damageCooldownTicks > 0
                && lastDamagedTick > 0 && (currentTick - lastDamagedTick) <= damageCooldownTicks) {
            base += conditionalSpeedBoostDamaged;
        }
        return Mth.clamp(base, 0f, 2f);
    }

    public void takeDamage(float amount) {
        float effective = amount * (1f - Mth.clamp(damageReduction, 0f, 0.95f));
        currentHP = Math.max(0, currentHP - effective);
    }

    public void takeDamageWithType(float amount, boolean isExplosion, boolean isProjectile) {
        float protection = damageReduction;
        if (isExplosion) protection += blastProtection;
        if (isProjectile) protection += projectileProtection;
        float effective = amount * (1f - Mth.clamp(protection, 0f, 0.95f));
        currentHP = Math.max(0, currentHP - effective);
    }

    public void heal(float amount) {
        currentHP = Math.min(maxHP, currentHP + amount);
    }

    public CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("MaxHP", maxHP);
        tag.putFloat("CurrentHP", currentHP);
        tag.putFloat("DamageReduction", damageReduction);
        tag.putFloat("CriticalThresholdReduction", criticalThresholdReduction);
        tag.putFloat("BlastProtection", blastProtection);
        tag.putFloat("ProjectileProtection", projectileProtection);
        tag.putFloat("SpeedModifier", speedModifier);
        tag.putInt("MobRangeModifier", mobRangeModifier);
        tag.putFloat("RepairCostReduction", repairCostReduction);
        tag.putBoolean("FireResistant", fireResistant);
        tag.putFloat("ReducedSpeedPenalty", reducedSpeedPenalty);
        tag.putFloat("ThornsDamage", thornsDamage);
        tag.putFloat("ConditionalSpeedBoostFullHP", conditionalSpeedBoostFullHP);
        tag.putFloat("ConditionalSpeedBoostDamaged", conditionalSpeedBoostDamaged);
        tag.putInt("DamageCooldownTicks", damageCooldownTicks);
        tag.putFloat("FlatSpeedBoost", flatSpeedBoost);
        tag.putLong("LastDamagedTick", lastDamagedTick);
        return tag;
    }

    public static TrainHealthData read(CompoundTag tag) {
        TrainHealthData data = new TrainHealthData();
        data.maxHP = tag.getFloat("MaxHP");
        data.currentHP = tag.getFloat("CurrentHP");
        data.damageReduction = tag.getFloat("DamageReduction");
        data.criticalThresholdReduction = tag.getFloat("CriticalThresholdReduction");
        data.blastProtection = tag.getFloat("BlastProtection");
        data.projectileProtection = tag.getFloat("ProjectileProtection");
        data.speedModifier = tag.getFloat("SpeedModifier");
        data.mobRangeModifier = tag.getInt("MobRangeModifier");
        data.repairCostReduction = tag.getFloat("RepairCostReduction");
        data.fireResistant = tag.getBoolean("FireResistant");
        data.reducedSpeedPenalty = tag.getFloat("ReducedSpeedPenalty");
        data.thornsDamage = tag.getFloat("ThornsDamage");
        data.conditionalSpeedBoostFullHP = tag.getFloat("ConditionalSpeedBoostFullHP");
        data.conditionalSpeedBoostDamaged = tag.getFloat("ConditionalSpeedBoostDamaged");
        data.damageCooldownTicks = tag.getInt("DamageCooldownTicks");
        data.flatSpeedBoost = tag.getFloat("FlatSpeedBoost");
        data.lastDamagedTick = tag.getLong("LastDamagedTick");
        if (data.flatSpeedBoost == 0 && tag.contains("SpeedBoost")) {
            data.flatSpeedBoost = tag.getFloat("SpeedBoost");
        }
        return data;
    }
}
