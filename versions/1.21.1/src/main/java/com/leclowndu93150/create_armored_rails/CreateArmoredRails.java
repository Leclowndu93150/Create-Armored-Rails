package com.leclowndu93150.create_armored_rails;

import com.leclowndu93150.create_armored_rails.behaviour.HullMovingInteraction;
import com.leclowndu93150.create_armored_rails.behaviour.VanillaBlockMovingInteraction;
import com.leclowndu93150.create_armored_rails.client.HullScreen;
import com.leclowndu93150.create_armored_rails.client.ModCasingCTSetup;
import com.leclowndu93150.create_armored_rails.health.UpgradeHelper;
import com.leclowndu93150.create_armored_rails.registry.*;
import com.mojang.logging.LogUtils;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(CreateArmoredRails.MODID)
public class CreateArmoredRails {
    public static final String MODID = "create_armored_rails";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreateArmoredRails(IEventBus modEventBus, ModContainer container) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        ModVillagerProfessions.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        Config.load();
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (mc, parent) -> Config.HANDLER.generateGui().generateScreen(parent));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            UpgradeHelper.init();
            MovingInteractionBehaviour.REGISTRY.register(
                    ModBlocks.HULL_BLOCK.get(), new HullMovingInteraction());
            registerInteractiveBlocks();
        });
    }

    private void registerInteractiveBlocks() {
        VanillaBlockMovingInteraction behaviour = new VanillaBlockMovingInteraction();
        for (String blockId : Config.INTERACTIVE_BLOCKS.get()) {
            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(blockId));
            if (block == null) {
                LOGGER.warn("Interactive block not found: {}", blockId);
                continue;
            }
            if (MovingInteractionBehaviour.REGISTRY.get(block) != null) {
                LOGGER.debug("Interactive block {} already registered by another mod, skipping", blockId);
                continue;
            }
            MovingInteractionBehaviour.REGISTRY.register(block, behaviour);
        }
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.HULL_MENU.get(), HullScreen::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(ModCasingCTSetup::register);
        }
    }
}
