package com.leclowndu93150.create_armored_rails.health;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class TrainHealthSavedData extends SavedData {
    private static final String DATA_NAME = "create_armored_rails_train_health";

    public TrainHealthSavedData() {
    }

    public TrainHealthSavedData(CompoundTag tag) {
        TrainHealthManager.loadFromNBT(tag);
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        CompoundTag data = TrainHealthManager.saveToNBT();
        tag.put("Data", data);
        return tag;
    }

    public static TrainHealthSavedData load(CompoundTag tag) {
        TrainHealthSavedData savedData = new TrainHealthSavedData();
        if (tag.contains("Data")) {
            TrainHealthManager.loadFromNBT(tag.getCompound("Data"));
        }
        return savedData;
    }

    public static TrainHealthSavedData getOrCreate(MinecraftServer server) {
        return server.overworld().getDataStorage()
                .computeIfAbsent(TrainHealthSavedData::load, TrainHealthSavedData::new, DATA_NAME);
    }

    public static void markDirty(MinecraftServer server) {
        getOrCreate(server).setDirty();
    }
}
