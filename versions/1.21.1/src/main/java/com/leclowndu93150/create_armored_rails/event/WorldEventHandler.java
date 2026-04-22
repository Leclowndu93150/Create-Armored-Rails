package com.leclowndu93150.create_armored_rails.event;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.leclowndu93150.create_armored_rails.health.TrainHealthSavedData;
import com.leclowndu93150.create_armored_rails.behaviour.ContraptionMenuTracker;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

@EventBusSubscriber(modid = CreateArmoredRails.MODID)
public class WorldEventHandler {
    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel level) {
            if (level.dimension() == Level.OVERWORLD) {
                TrainHealthSavedData.getOrCreate(level.getServer());
            }
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel level) {
            if (level.dimension() == Level.OVERWORLD) {
                TrainHealthManager.clear();
            }
        }
    }

    @SubscribeEvent
    public static void onContainerClose(PlayerContainerEvent.Close event) {
        ContraptionMenuTracker.clear(event.getEntity());
    }
}
