package com.leclowndu93150.create_armored_rails.registry;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateArmoredRails.MODID);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .title(Component.translatable("itemGroup." + CreateArmoredRails.MODID))
                    .icon(() -> ModItems.HULL_BLOCK_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.HULL_BLOCK_ITEM.get());
                        output.accept(ModItems.REPAIR_HAMMER.get());

                        output.accept(ModItems.ANDESITE_HULL_FRAME.get());
                        output.accept(ModItems.COPPER_HULL_FRAME.get());
                        output.accept(ModItems.BRASS_HULL_FRAME.get());
                        output.accept(ModItems.ALUMINIUM_HULL_FRAME.get());
                        output.accept(ModItems.IRON_HULL_FRAME.get());
                        output.accept(ModItems.STEEL_HULL_FRAME.get());
                        output.accept(ModItems.TUNGSTEN_HULL_FRAME.get());
                        output.accept(ModItems.NETHERITE_HULL_FRAME.get());
                        output.accept(ModItems.TUNGSTEN_ALLOY_HULL_FRAME.get());

                        output.accept(ModItems.OVERDRIVE_REGULATOR.get());
                        output.accept(ModItems.EMERGENCY_OVERRIDE_SYSTEM.get());
                        output.accept(ModItems.OPTICAL_CAMOUFLAGE.get());
                        output.accept(ModItems.PRISTINE_CONDITION.get());
                        output.accept(ModItems.SUPERHEATED_ENGINES.get());
                        output.accept(ModItems.REINFORCED_BULKHEADS.get());
                        output.accept(ModItems.SHOCK_ABSORBER.get());
                        output.accept(ModItems.REACTIVE_ARMOR.get());
                        output.accept(ModItems.ENGINEERS_MANUALS.get());

                        output.accept(ModItems.RAW_TUNGSTEN.get());
                        output.accept(ModItems.CRUSHED_RAW_TUNGSTEN.get());
                        output.accept(ModItems.TUNGSTEN_NUGGET.get());
                        output.accept(ModItems.TUNGSTEN_INGOT.get());
                        output.accept(ModItems.TUNGSTEN_SHEET.get());
                        output.accept(ModItems.TUNGSTEN_ALLOY_INGOT.get());
                        output.accept(ModItems.TUNGSTEN_ALLOY_SHEET.get());

                        output.accept(ModItems.TUNGSTEN_ORE_ITEM.get());
                        output.accept(ModItems.DEEPSLATE_TUNGSTEN_ORE_ITEM.get());
                        output.accept(ModItems.RAW_TUNGSTEN_BLOCK_ITEM.get());
                        output.accept(ModItems.TUNGSTEN_BLOCK_ITEM.get());
                        output.accept(ModItems.TUNGSTEN_ALLOY_BLOCK_ITEM.get());
                        output.accept(ModItems.TUNGSTEN_PLATING_BLOCK_ITEM.get());
                        output.accept(ModItems.TUNGSTEN_ALLOY_PLATING_BLOCK_ITEM.get());
                        output.accept(ModItems.ENCASED_STEEL_PLATING_ITEM.get());
                        output.accept(ModItems.ENCASED_TUNGSTEN_PLATING_ITEM.get());
                        output.accept(ModItems.ENCASED_TUNGSTEN_ALLOY_PLATING_ITEM.get());
                        output.accept(ModItems.STEEL_CASING_CONNECTED_ITEM.get());
                        output.accept(ModItems.TUNGSTEN_CASING_CONNECTED_ITEM.get());
                        output.accept(ModItems.TUNGSTEN_ALLOY_CASING_CONNECTED_ITEM.get());
                    })
                    .build());

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
