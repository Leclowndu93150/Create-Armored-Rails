package com.leclowndu93150.create_armored_rails.registry;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.item.RepairHammerItem;
import com.leclowndu93150.create_armored_rails.item.StatTooltipItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateArmoredRails.MODID);

    public static final DeferredHolder<Item, Item> HULL_BLOCK_ITEM = ITEMS.register("hull",
            () -> new BlockItem(ModBlocks.HULL_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_ORE_ITEM = ITEMS.register("tungsten_ore",
            () -> new BlockItem(ModBlocks.TUNGSTEN_ORE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> DEEPSLATE_TUNGSTEN_ORE_ITEM = ITEMS.register("deepslate_tungsten_ore",
            () -> new BlockItem(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RAW_TUNGSTEN_BLOCK_ITEM = ITEMS.register("raw_tungsten_block",
            () -> new BlockItem(ModBlocks.RAW_TUNGSTEN_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_BLOCK_ITEM = ITEMS.register("tungsten_block",
            () -> new BlockItem(ModBlocks.TUNGSTEN_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_ALLOY_BLOCK_ITEM = ITEMS.register("tungsten_alloy_block",
            () -> new BlockItem(ModBlocks.TUNGSTEN_ALLOY_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_PLATING_BLOCK_ITEM = ITEMS.register("tungsten_plating_block",
            () -> new BlockItem(ModBlocks.TUNGSTEN_PLATING_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_ALLOY_PLATING_BLOCK_ITEM = ITEMS.register("tungsten_alloy_plating_block",
            () -> new BlockItem(ModBlocks.TUNGSTEN_ALLOY_PLATING_BLOCK.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_CASING_CONNECTED_ITEM = ITEMS.register("steel_casing_connected",
            () -> new BlockItem(ModBlocks.STEEL_CASING_CONNECTED.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_CASING_CONNECTED_ITEM = ITEMS.register("tungsten_casing_connected",
            () -> new BlockItem(ModBlocks.TUNGSTEN_CASING_CONNECTED.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_ALLOY_CASING_CONNECTED_ITEM = ITEMS.register("tungsten_alloy_casing_connected",
            () -> new BlockItem(ModBlocks.TUNGSTEN_ALLOY_CASING_CONNECTED.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> REPAIR_HAMMER = ITEMS.register("repair_hammer",
            () -> new RepairHammerItem(new Item.Properties().stacksTo(1)));

    public static final DeferredHolder<Item, Item> ANDESITE_HULL_FRAME = ITEMS.register("andesite_hull_frame",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_HULL_FRAME = ITEMS.register("copper_hull_frame",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRASS_HULL_FRAME = ITEMS.register("brass_hull_frame",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ALUMINIUM_HULL_FRAME = ITEMS.register("aluminium_hull_frame",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> IRON_HULL_FRAME = ITEMS.register("iron_hull_frame",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_HULL_FRAME = ITEMS.register("steel_hull_frame",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_HULL_FRAME = ITEMS.register("tungsten_hull_frame",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHERITE_HULL_FRAME = ITEMS.register("netherite_hull_frame",
            () -> new StatTooltipItem(new Item.Properties().fireResistant()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_ALLOY_HULL_FRAME = ITEMS.register("tungsten_alloy_hull_frame",
            () -> new StatTooltipItem(new Item.Properties().fireResistant()));

    public static final DeferredHolder<Item, Item> OVERDRIVE_REGULATOR = ITEMS.register("overdrive_regulator",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> EMERGENCY_OVERRIDE_SYSTEM = ITEMS.register("emergency_override_system",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> OPTICAL_CAMOUFLAGE = ITEMS.register("optical_camouflage",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISTINE_CONDITION = ITEMS.register("pristine_condition",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SUPERHEATED_ENGINES = ITEMS.register("superheated_engines",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> REINFORCED_BULKHEADS = ITEMS.register("reinforced_bulkheads",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHOCK_ABSORBER = ITEMS.register("shock_absorber",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> REACTIVE_ARMOR = ITEMS.register("reactive_armor",
            () -> new StatTooltipItem(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ENGINEERS_MANUALS = ITEMS.register("engineers_manuals",
            () -> new StatTooltipItem(new Item.Properties()));

    public static final DeferredHolder<Item, Item> RAW_TUNGSTEN = ITEMS.register("raw_tungsten",
            () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> CRUSHED_RAW_TUNGSTEN = ITEMS.register("crushed_raw_tungsten",
            () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_NUGGET = ITEMS.register("tungsten_nugget",
            () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_INGOT = ITEMS.register("tungsten_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_SHEET = ITEMS.register("tungsten_sheet",
            () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_ALLOY_INGOT = ITEMS.register("tungsten_alloy_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUNGSTEN_ALLOY_SHEET = ITEMS.register("tungsten_alloy_sheet",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
