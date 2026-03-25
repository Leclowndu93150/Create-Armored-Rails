package com.leclowndu93150.create_armored_rails.event;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = CreateArmoredRails.MODID)
public class MissingMappingsHandler {

    private static final Map<String, String> ITEM_REMAPS = Map.of(
            "create_armored_rails:iron_plating", "create_armored_rails:iron_hull_frame",
            "create_armored_rails:steel_plating", "create_armored_rails:steel_hull_frame",
            "create_armored_rails:tungsten_plating", "create_armored_rails:tungsten_hull_frame",
            "create_armored_rails:reinforced_frame", "create_armored_rails:reinforced_bulkheads",
            "create_armored_rails:hardened_plating", "create_armored_rails:pristine_condition",
            "create_armored_rails:aerodynamic_casing", "create_armored_rails:superheated_engines"
    );

    @SubscribeEvent
    public static void onMissingItems(MissingMappingsEvent event) {
        for (MissingMappingsEvent.Mapping<Item> mapping : event.getMappings(ForgeRegistries.Keys.ITEMS, CreateArmoredRails.MODID)) {
            String oldKey = mapping.getKey().toString();
            String newKey = ITEM_REMAPS.get(oldKey);
            if (newKey != null) {
                Item newItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(newKey));
                if (newItem != null) {
                    mapping.remap(newItem);
                }
            }
        }
    }
}
