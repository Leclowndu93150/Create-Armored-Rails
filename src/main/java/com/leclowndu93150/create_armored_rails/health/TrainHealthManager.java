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
        float upgradeHP = 0;
        float hpBonusPercent = 0;
        float damageReduction = 0;
        float thresholdReduction = 0;
        float speedBoost = 0;
        float storedHealthPercent = -1;
        boolean foundHull = false;

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
                        ItemStackHandler handler = new ItemStackHandler(HullMenu.HULL_SLOT_COUNT);
                        handler.deserializeNBT(info.nbt().getCompound("Inventory"));

                        ItemStack upgradeStack = handler.getStackInSlot(0);
                        upgradeHP = UpgradeHelper.getUpgradeHP(upgradeStack);
                        int unlockedSlots = UpgradeHelper.getModifierSlotCount(upgradeStack);

                        for (int i = 1; i <= Math.min(unlockedSlots, 3); i++) {
                            ItemStack modStack = handler.getStackInSlot(i);
                            damageReduction += UpgradeHelper.getDamageReduction(modStack);
                            thresholdReduction += UpgradeHelper.getThresholdReduction(modStack);
                            hpBonusPercent += UpgradeHelper.getHPBonusPercent(modStack);
                            speedBoost += UpgradeHelper.getSpeedBoost(modStack);
                        }
                    }
                }
                break;
            }
        }

        if (!foundHull) return;

        float baseHP = Config.BASE_TRAIN_HP.get() + upgradeHP;
        float totalMaxHP = baseHP * (1f + hpBonusPercent);
        TrainHealthData data = healthMap.get(train.id);
        if (data == null) {
            data = new TrainHealthData(totalMaxHP, damageReduction, thresholdReduction, speedBoost);
            if (storedHealthPercent >= 0) {
                data.setCurrentHP(totalMaxHP * storedHealthPercent);
            }
            healthMap.put(train.id, data);
        } else {
            float oldPercentage = data.getHealthPercentage();
            data.setMaxHP(totalMaxHP);
            data.setCurrentHP(totalMaxHP * oldPercentage);
            data.setDamageReduction(damageReduction);
            data.setCriticalThresholdReduction(thresholdReduction);
            data.setSpeedBoost(speedBoost);
        }
        checkedTrains.remove(train.id);
    }

    public static void destroyTrain(Train train, ServerLevel serverLevel) {
        boolean explode = Config.EXPLODE_ON_DEATH.get();
        double powerPerBlock = Config.EXPLOSION_POWER_PER_BLOCK.get();

        List<Vec3> explosionPositions = new ArrayList<>();
        List<Float> explosionPowers = new ArrayList<>();

        for (Carriage carriage : train.carriages) {
            CarriageContraptionEntity entity = carriage.anyAvailableEntity();
            if (entity == null) continue;

            for (Entity passenger : new ArrayList<>(entity.getPassengers())) {
                passenger.stopRiding();
            }

            if (explode && entity.getContraption() instanceof CarriageContraption cc) {
                int blockCount = cc.getBlocks().size();
                float explosionPower = (float)(blockCount * powerPerBlock);
                explosionPower = Math.max(2.0f, Math.min(explosionPower, 20.0f));

                List<BlockPos> blockPositions = new ArrayList<>(cc.getBlocks().keySet());
                int explosionCount = Math.max(1, blockCount / 10);
                explosionCount = Math.min(explosionCount, 8);

                float perExplosionPower = explosionPower / explosionCount;

                for (int i = 0; i < explosionCount; i++) {
                    int index = (int)((long)i * blockPositions.size() / explosionCount);
                    BlockPos localPos = blockPositions.get(index);
                    Vec3 worldPos = entity.toGlobalVector(Vec3.atCenterOf(localPos), 1.0f);
                    explosionPositions.add(worldPos);
                    explosionPowers.add(perExplosionPower);
                }
            }

            entity.discard();
        }

        Create.RAILWAYS.removeTrain(train.id);
        AllPackets.getChannel().send(PacketDistributor.ALL.noArg(), new TrainPacket(train, false));
        remove(train.id);

        Level.ExplosionInteraction interaction = Config.EXPLOSION_NON_DESTRUCTIVE.get()
                ? Level.ExplosionInteraction.NONE
                : Level.ExplosionInteraction.TNT;

        for (int i = 0; i < explosionPositions.size(); i++) {
            Vec3 pos = explosionPositions.get(i);
            serverLevel.explode(null, pos.x, pos.y, pos.z,
                    explosionPowers.get(i), interaction);
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
