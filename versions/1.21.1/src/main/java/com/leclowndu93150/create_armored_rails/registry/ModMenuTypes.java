package com.leclowndu93150.create_armored_rails.registry;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.block.HullMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, CreateArmoredRails.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<HullMenu>> HULL_MENU =
            MENUS.register("hull", () -> IMenuTypeExtension.create(HullMenu::fromNetwork));

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
