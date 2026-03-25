package com.leclowndu93150.create_armored_rails.event;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.Set;

@Mod.EventBusSubscriber(modid = CreateArmoredRails.MODID)
public class LootTableHandler {

    private static final Set<String> BASIC_CHESTS = Set.of(
            "minecraft:chests/simple_dungeon",
            "minecraft:chests/abandoned_mineshaft"
    );

    private static final Set<String> INTERMEDIATE_CHESTS = Set.of(
            "minecraft:chests/stronghold_corridor",
            "minecraft:chests/stronghold_crossing",
            "minecraft:chests/desert_pyramid",
            "minecraft:chests/jungle_temple"
    );

    private static final Set<String> ADVANCED_CHESTS = Set.of(
            "minecraft:chests/end_city_treasure",
            "minecraft:chests/bastion_treasure",
            "minecraft:chests/ancient_city"
    );

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        String name = event.getName().toString();

        if (BASIC_CHESTS.contains(name)) {
            event.getTable().addPool(LootPool.lootPool()
                    .when(LootItemRandomChanceCondition.randomChance(0.15f))
                    .add(LootItem.lootTableItem(ModItems.SHOCK_ABSORBER.get()).setWeight(3))
                    .add(LootItem.lootTableItem(ModItems.REINFORCED_BULKHEADS.get()).setWeight(3))
                    .add(LootItem.lootTableItem(ModItems.PRISTINE_CONDITION.get()).setWeight(2))
                    .build());
        }

        if (INTERMEDIATE_CHESTS.contains(name)) {
            event.getTable().addPool(LootPool.lootPool()
                    .when(LootItemRandomChanceCondition.randomChance(0.12f))
                    .add(LootItem.lootTableItem(ModItems.SUPERHEATED_ENGINES.get()).setWeight(3))
                    .add(LootItem.lootTableItem(ModItems.OPTICAL_CAMOUFLAGE.get()).setWeight(3))
                    .add(LootItem.lootTableItem(ModItems.EMERGENCY_OVERRIDE_SYSTEM.get()).setWeight(2))
                    .build());
        }

        if (ADVANCED_CHESTS.contains(name)) {
            event.getTable().addPool(LootPool.lootPool()
                    .when(LootItemRandomChanceCondition.randomChance(0.10f))
                    .add(LootItem.lootTableItem(ModItems.OVERDRIVE_REGULATOR.get()).setWeight(3))
                    .add(LootItem.lootTableItem(ModItems.REACTIVE_ARMOR.get()).setWeight(3))
                    .add(LootItem.lootTableItem(ModItems.ENGINEERS_MANUALS.get()).setWeight(2))
                    .build());
        }
    }
}
