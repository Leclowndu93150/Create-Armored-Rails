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
                        output.accept(ModItems.IRON_PLATING.get());
                        output.accept(ModItems.STEEL_PLATING.get());
                        output.accept(ModItems.TUNGSTEN_PLATING.get());
                        output.accept(ModItems.SHOCK_ABSORBER.get());
                        output.accept(ModItems.REINFORCED_FRAME.get());
                        output.accept(ModItems.HARDENED_PLATING.get());
                        output.accept(ModItems.AERODYNAMIC_CASING.get());
                    })
                    .build());

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
