package com.leclowndu93150.create_armored_rails.event;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.ai.TargetTrainGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreateArmoredRails.MODID)
public class MobTargetingHandler {
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!Config.MOB_TARGETING_ENABLED.get()) return;
        if (event.getEntity() instanceof Monster mob) {
            mob.goalSelector.addGoal(3, new TargetTrainGoal(mob));
        }
    }
}
