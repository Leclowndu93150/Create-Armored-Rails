package com.leclowndu93150.create_armored_rails.registry;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.item.RepairHammerItem;
import com.leclowndu93150.create_armored_rails.item.TooltipItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CreateArmoredRails.MODID);

    public static final RegistryObject<Item> HULL_BLOCK_ITEM = ITEMS.register("hull",
            () -> new BlockItem(ModBlocks.HULL_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> REPAIR_HAMMER = ITEMS.register("repair_hammer",
            () -> new RepairHammerItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> IRON_PLATING = ITEMS.register("iron_plating",
            () -> new TooltipItem(new Item.Properties(),
                    "item.create_armored_rails.iron_plating.desc1",
                    "item.create_armored_rails.iron_plating.desc2"));

    public static final RegistryObject<Item> STEEL_PLATING = ITEMS.register("steel_plating",
            () -> new TooltipItem(new Item.Properties(),
                    "item.create_armored_rails.steel_plating.desc1",
                    "item.create_armored_rails.steel_plating.desc2"));

    public static final RegistryObject<Item> TUNGSTEN_PLATING = ITEMS.register("tungsten_plating",
            () -> new TooltipItem(new Item.Properties(),
                    "item.create_armored_rails.tungsten_plating.desc1",
                    "item.create_armored_rails.tungsten_plating.desc2"));

    public static final RegistryObject<Item> SHOCK_ABSORBER = ITEMS.register("shock_absorber",
            () -> new TooltipItem(new Item.Properties(),
                    "item.create_armored_rails.shock_absorber.desc"));

    public static final RegistryObject<Item> REINFORCED_FRAME = ITEMS.register("reinforced_frame",
            () -> new TooltipItem(new Item.Properties(),
                    "item.create_armored_rails.reinforced_frame.desc"));

    public static final RegistryObject<Item> HARDENED_PLATING = ITEMS.register("hardened_plating",
            () -> new TooltipItem(new Item.Properties(),
                    "item.create_armored_rails.hardened_plating.desc"));

    public static final RegistryObject<Item> AERODYNAMIC_CASING = ITEMS.register("aerodynamic_casing",
            () -> new TooltipItem(new Item.Properties(),
                    "item.create_armored_rails.aerodynamic_casing.desc"));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}
