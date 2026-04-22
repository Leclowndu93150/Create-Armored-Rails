package com.leclowndu93150.create_armored_rails;

import com.leclowndu93150.create_armored_rails.behaviour.HullMovingInteraction;
import com.leclowndu93150.create_armored_rails.behaviour.VanillaBlockMovingInteraction;
import com.leclowndu93150.create_armored_rails.client.HullScreen;
import com.leclowndu93150.create_armored_rails.client.ModCasingCTSetup;
import com.leclowndu93150.create_armored_rails.health.UpgradeHelper;
import com.leclowndu93150.create_armored_rails.network.ModPackets;
import com.leclowndu93150.create_armored_rails.registry.*;
import com.mojang.logging.LogUtils;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(CreateArmoredRails.MODID)
public class CreateArmoredRails {
    public static final String MODID = "create_armored_rails";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CreateArmoredRails() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        ModVillagerProfessions.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        Config.load();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (mc, parent) -> Config.HANDLER.generateGui().generateScreen(parent)));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            UpgradeHelper.init();
            MovingInteractionBehaviour.REGISTRY.register(
                    ModBlocks.HULL_BLOCK.get(), new HullMovingInteraction());
            registerInteractiveBlocks();
        });
        ModPackets.register();
    }

    private void registerInteractiveBlocks() {
        VanillaBlockMovingInteraction behaviour = new VanillaBlockMovingInteraction();
        for (String blockId : Config.INTERACTIVE_BLOCKS.get()) {
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockId));
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

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                MenuScreens.register(ModMenuTypes.HULL_MENU.get(), HullScreen::new);
                ModCasingCTSetup.register();
            });
        }
    }
}
