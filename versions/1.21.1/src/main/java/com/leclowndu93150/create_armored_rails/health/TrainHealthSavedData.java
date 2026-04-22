package com.leclowndu93150.create_armored_rails.health;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;

public class TrainHealthSavedData extends SavedData {
    private static final String DATA_NAME = "create_armored_rails_train_health";

    public TrainHealthSavedData() {
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        CompoundTag data = TrainHealthManager.saveToNBT();
        tag.put("Data", data);
        return tag;
    }

    public static TrainHealthSavedData load(CompoundTag tag, HolderLookup.Provider registries) {
        TrainHealthSavedData savedData = new TrainHealthSavedData();
        if (tag.contains("Data")) {
            TrainHealthManager.loadFromNBT(tag.getCompound("Data"));
        }
        return savedData;
    }

    public static TrainHealthSavedData getOrCreate(MinecraftServer server) {
        return server.overworld().getDataStorage()
                .computeIfAbsent(new SavedData.Factory<>(
                        TrainHealthSavedData::new,
                        TrainHealthSavedData::load,
                        DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES
                ), DATA_NAME);
    }

    public static void markDirty(MinecraftServer server) {
        getOrCreate(server).setDirty();
    }
}
