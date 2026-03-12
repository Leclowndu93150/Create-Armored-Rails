package com.leclowndu93150.create_armored_rails.health;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public class TrainHealthData {
    private float maxHP;
    private float currentHP;
    private float damageReduction;
    private float criticalThresholdReduction;
    private float speedBoost;

    public TrainHealthData(float maxHP, float damageReduction, float criticalThresholdReduction, float speedBoost) {
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.damageReduction = damageReduction;
        this.criticalThresholdReduction = criticalThresholdReduction;
        this.speedBoost = speedBoost;
    }

    public TrainHealthData(float maxHP, float damageReduction, float criticalThresholdReduction) {
        this(maxHP, damageReduction, criticalThresholdReduction, 0);
    }

    public TrainHealthData() {
        this(0, 0, 0, 0);
    }

    public float getMaxHP() { return maxHP; }
    public float getCurrentHP() { return currentHP; }
    public float getDamageReduction() { return damageReduction; }
    public float getCriticalThresholdReduction() { return criticalThresholdReduction; }
    public float getSpeedBoost() { return speedBoost; }

    public void setMaxHP(float maxHP) { this.maxHP = maxHP; }
    public void setCurrentHP(float currentHP) { this.currentHP = Mth.clamp(currentHP, 0, maxHP); }
    public void setDamageReduction(float damageReduction) { this.damageReduction = damageReduction; }
    public void setCriticalThresholdReduction(float reduction) { this.criticalThresholdReduction = reduction; }
    public void setSpeedBoost(float speedBoost) { this.speedBoost = speedBoost; }

    public float getHealthPercentage() {
        return maxHP > 0 ? currentHP / maxHP : 0f;
    }

    public boolean isCriticalFailure(double configuredThreshold) {
        float effectiveThreshold = (float)(configuredThreshold - criticalThresholdReduction);
        return maxHP > 0 && getHealthPercentage() < effectiveThreshold;
    }

    public float getSpeedMultiplier() {
        if (maxHP <= 0) return 1f;
        float base = Mth.clamp(getHealthPercentage(), 0f, 1f);
        if (speedBoost > 0 && currentHP >= maxHP) {
            base += speedBoost;
        }
        return base;
    }

    public void takeDamage(float amount) {
        float effective = amount * (1f - Mth.clamp(damageReduction, 0f, 0.95f));
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
        tag.putFloat("SpeedBoost", speedBoost);
        return tag;
    }

    public static TrainHealthData read(CompoundTag tag) {
        TrainHealthData data = new TrainHealthData();
        data.maxHP = tag.getFloat("MaxHP");
        data.currentHP = tag.getFloat("CurrentHP");
        data.damageReduction = tag.getFloat("DamageReduction");
        data.criticalThresholdReduction = tag.getFloat("CriticalThresholdReduction");
        data.speedBoost = tag.getFloat("SpeedBoost");
        return data;
    }
}
