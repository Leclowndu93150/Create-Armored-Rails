package com.leclowndu93150.create_armored_rails.event;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.ai.CreeperSwellTrainGoal;
import com.leclowndu93150.create_armored_rails.ai.TargetTrainGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = CreateArmoredRails.MODID)
public class MobTargetingHandler {
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!Config.MOB_TARGETING_ENABLED.get()) return;
        if (event.getEntity() instanceof Creeper creeper) {
            creeper.goalSelector.addGoal(2, new CreeperSwellTrainGoal(creeper));
            creeper.goalSelector.addGoal(3, new TargetTrainGoal(creeper));
        } else if (event.getEntity() instanceof Monster mob) {
            mob.goalSelector.addGoal(3, new TargetTrainGoal(mob));
        }
    }
}
