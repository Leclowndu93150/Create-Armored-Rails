package com.leclowndu93150.create_armored_rails.health;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.registry.ModBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.AllPackets;
import com.simibubi.create.content.trains.entity.Carriage;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.entity.TrainPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.PacketDistributor;

import com.leclowndu93150.create_armored_rails.block.HullMenu;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.*;

public class TrainHealthManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<UUID, TrainHealthData> healthMap = new HashMap<>();
    private static final Set<UUID> checkedTrains = new HashSet<>();

    public static TrainHealthData get(UUID trainId) {
        return healthMap.get(trainId);
    }

    public static TrainHealthData getOrCreate(UUID trainId) {
        return healthMap.computeIfAbsent(trainId, id -> new TrainHealthData());
    }

    public static void remove(UUID trainId) {
        healthMap.remove(trainId);
        checkedTrains.remove(trainId);
    }

    public static boolean isTracked(UUID trainId) {
        return healthMap.containsKey(trainId);
    }

    public static boolean isChecked(UUID trainId) {
        return checkedTrains.contains(trainId);
    }

    public static void markChecked(UUID trainId) {
        checkedTrains.add(trainId);
    }

    public static void clearChecked(UUID trainId) {
        checkedTrains.remove(trainId);
    }

    public static void ensureBaseHealth(Train train) {
        if (healthMap.containsKey(train.id)) {
            checkedTrains.add(train.id);
            return;
        }
        float baseHP = Config.BASE_TRAIN_HP.get();
        TrainHealthData data = new TrainHealthData(baseHP, 0, 0);
        healthMap.put(train.id, data);
        checkedTrains.add(train.id);
    }

    public static boolean hasHullBlocks(Train train) {
        for (Carriage carriage : train.carriages) {
            if (carriage.anyAvailableEntity() == null) continue;
            var entity = carriage.anyAvailableEntity();
            if (entity.getContraption() instanceof CarriageContraption cc) {
                for (StructureBlockInfo info : cc.getBlocks().values()) {
                    if (info.state().getBlock() == ModBlocks.HULL_BLOCK.get()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void recalculateFromContraption(Train train) {
        float storedHealthPercent = -1;
        boolean foundHull = false;
        HullFrameStats frameStats = null;
        float totalDamageReduction = 0;
        float totalBlastProtection = 0;
        float totalProjectileProtection = 0;
        float totalCritReduction = 0;
        float totalFlatSpeedBoost = 0;
        float totalSpeedBoostFullHP = 0;
        float totalSpeedBoostDamaged = 0;
        int totalMobRangeReduction = 0;
        float totalRepairCostReduction = 0;
        float totalThornsDamage = 0;
        int damageCooldownTicks = 0;

        for (Carriage carriage : train.carriages) {
            if (foundHull) break;
            var entity = carriage.anyAvailableEntity();
            if (entity == null) continue;
            if (!(entity.getContraption() instanceof CarriageContraption cc)) continue;

            for (StructureBlockInfo info : cc.getBlocks().values()) {
                if (info.state().getBlock() != ModBlocks.HULL_BLOCK.get()) continue;
                foundHull = true;

                if (info.nbt() != null) {
                    if (info.nbt().contains("HealthPercent")) {
                        storedHealthPercent = info.nbt().getFloat("HealthPercent");
                    }

                    if (info.nbt().contains("Inventory")) {
                        ItemStackHandler handler = HullMenu.loadInventory(info.nbt().getCompound("Inventory"));

                        ItemStack upgradeStack = handler.getStackInSlot(0);
                        frameStats = UpgradeHelper.getHullFrameStats(upgradeStack);
                        int unlockedSlots = frameStats != null ? frameStats.modifierSlots() : 0;

                        for (int i = 1; i <= Math.min(unlockedSlots, 4); i++) {
                            ItemStack modStack = handler.getStackInSlot(i);
                            ModifierStats ms = UpgradeHelper.getModifierStats(modStack);
                            if (ms == null) continue;
                            totalDamageReduction += ms.damageReduction();
                            totalBlastProtection += ms.blastProtection();
                            totalCritReduction += ms.criticalThresholdReduction();
                            totalFlatSpeedBoost += ms.speedBoost();
                            totalSpeedBoostFullHP += ms.speedBoostAtFullHP();
                            totalSpeedBoostDamaged += ms.speedBoostWhenDamaged();
                            totalMobRangeReduction += ms.mobRangeReduction();
                            totalRepairCostReduction += ms.repairCostReduction();
                            totalThornsDamage += ms.thornsDamage();
                            if (ms.damageCooldownTicks() > damageCooldownTicks) {
                                damageCooldownTicks = ms.damageCooldownTicks();
                            }
                        }
                    }
                }
                break;
            }
        }

        if (!foundHull) return;

        float frameHP = frameStats != null ? frameStats.hpBonus() : 0;
        float totalMaxHP = Config.BASE_TRAIN_HP.get() + frameHP;

        float frameDmgRed = frameStats != null ? frameStats.damageReduction() : 0;
        float frameBlast = frameStats != null ? frameStats.blastProtection() : 0;
        float frameProj = frameStats != null ? frameStats.projectileProtection() : 0;
        float frameCrit = frameStats != null ? frameStats.criticalThresholdModifier() : 0;
        float frameSpeed = frameStats != null ? frameStats.speedModifier() : 0;
        int frameMobRange = frameStats != null ? frameStats.mobRangeModifier() : 0;
        float frameRepairCost = frameStats != null ? frameStats.repairCostReduction() : 0;
        float frameSpeedPenaltyRed = frameStats != null ? frameStats.reducedSpeedPenalty() : 0;
        boolean fireResistant = frameStats != null && frameStats.fireResistant();

        TrainHealthData data = healthMap.get(train.id);
        float oldPercent = data != null ? data.getHealthPercentage() : -1;
        long oldLastDamaged = data != null ? data.getLastDamagedTick() : 0;

        if (data == null) {
            data = new TrainHealthData(totalMaxHP, frameDmgRed + totalDamageReduction,
                    Math.abs(frameCrit) + totalCritReduction);
            if (storedHealthPercent >= 0) {
                data.setCurrentHP(totalMaxHP * storedHealthPercent);
            }
            healthMap.put(train.id, data);
        } else {
            data.setMaxHP(totalMaxHP);
            if (storedHealthPercent >= 0) {
                data.setCurrentHP(totalMaxHP * storedHealthPercent);
            } else if (oldPercent >= 0) {
                data.setCurrentHP(totalMaxHP * oldPercent);
            }
            data.setDamageReduction(frameDmgRed + totalDamageReduction);
            data.setCriticalThresholdReduction(Math.abs(frameCrit) + totalCritReduction);
        }

        data.setBlastProtection(frameBlast + totalBlastProtection);
        data.setProjectileProtection(frameProj);
        data.setSpeedModifier(frameSpeed);
        data.setMobRangeModifier(frameMobRange - totalMobRangeReduction);
        data.setRepairCostReduction(frameRepairCost + totalRepairCostReduction);
        data.setFireResistant(fireResistant);
        data.setReducedSpeedPenalty(frameSpeedPenaltyRed);
        data.setThornsDamage(totalThornsDamage);
        data.setConditionalSpeedBoostFullHP(totalSpeedBoostFullHP);
        data.setConditionalSpeedBoostDamaged(totalSpeedBoostDamaged);
        data.setDamageCooldownTicks(damageCooldownTicks);
        data.setFlatSpeedBoost(totalFlatSpeedBoost);
        data.setLastDamagedTick(oldLastDamaged);

        checkedTrains.remove(train.id);
    }

    public static void destroyTrain(Train train, ServerLevel serverLevel) {
        remove(train.id);
        for (Carriage carriage : train.carriages) {
            CarriageContraptionEntity entity = carriage.anyAvailableEntity();
            if (entity == null) continue;

            for (Entity passenger : new ArrayList<>(entity.getPassengers())) {
                passenger.stopRiding();
            }

            if (entity.getContraption() instanceof CarriageContraption cc) {
                int blockCount = cc.getBlocks().size();
                double powerPerBlock = Config.EXPLOSION_POWER_PER_BLOCK.get();
                float explosionPower = (float)(blockCount * powerPerBlock);
                explosionPower = Math.max(1.0f, Math.min(explosionPower, 10.0f));

                Vec3 center = entity.position().add(0, 1, 0);
                serverLevel.explode(null, center.x, center.y, center.z,
                        explosionPower, Level.ExplosionInteraction.NONE);

                double radius = explosionPower * 2;
                for (Entity nearby : serverLevel.getEntities(entity,
                        entity.getBoundingBox().inflate(radius))) {
                    if (nearby instanceof net.minecraft.world.entity.LivingEntity living) {
                        double dist = living.distanceTo(entity);
                        if (dist < radius) {
                            float dmg = (float)((1.0 - dist / radius) * explosionPower * 4);
                            living.hurt(serverLevel.damageSources().explosion(null, null), dmg);
                        }
                    }
                }
            }
        }
    }

    public static void syncHealthToContraption(Train train) {
        TrainHealthData data = healthMap.get(train.id);
        if (data == null) return;

        for (Carriage carriage : train.carriages) {
            var entity = carriage.anyAvailableEntity();
            if (entity == null) continue;
            if (!(entity.getContraption() instanceof CarriageContraption cc)) continue;

            for (var entry : cc.getBlocks().entrySet()) {
                StructureBlockInfo info = entry.getValue();
                if (info.state().getBlock() != ModBlocks.HULL_BLOCK.get()) continue;

                CompoundTag nbt = info.nbt() != null ? info.nbt().copy() : new CompoundTag();
                nbt.putFloat("HealthPercent", data.getHealthPercentage());
                cc.getBlocks().put(entry.getKey(),
                        new StructureBlockInfo(info.pos(), info.state(), nbt));
                return;
            }
        }
    }

    public static CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag list = new ListTag();
        for (Map.Entry<UUID, TrainHealthData> entry : healthMap.entrySet()) {
            CompoundTag entryTag = entry.getValue().write();
            entryTag.putUUID("TrainId", entry.getKey());
            list.add(entryTag);
        }
        tag.put("TrainHealthEntries", list);
        return tag;
    }

    public static void loadFromNBT(CompoundTag tag) {
        healthMap.clear();
        checkedTrains.clear();
        if (tag.contains("TrainHealthEntries")) {
            ListTag list = tag.getList("TrainHealthEntries", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag entryTag = list.getCompound(i);
                UUID trainId = entryTag.getUUID("TrainId");
                TrainHealthData data = TrainHealthData.read(entryTag);
                healthMap.put(trainId, data);
            }
        }
    }

    public static void clear() {
        healthMap.clear();
        checkedTrains.clear();
    }
}
