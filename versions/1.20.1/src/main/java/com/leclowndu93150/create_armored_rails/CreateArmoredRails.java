package com.leclowndu93150.create_armored_rails;

import com.leclowndu93150.create_armored_rails.behaviour.HullMovingInteraction;
import com.leclowndu93150.create_armored_rails.client.ClientSetup;
import com.leclowndu93150.create_armored_rails.health.UpgradeHelper;
import com.leclowndu93150.create_armored_rails.network.ModPackets;
import com.leclowndu93150.create_armored_rails.registry.*;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CreateArmoredRails.MODID)
public class CreateArmoredRails {
    public static final String MODID = "create_armored_rails";

    public CreateArmoredRails() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        //ModVillagerProfessions.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        Config.load();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientSetup.registerConfigScreen();
            modEventBus.addListener(ClientSetup::onClientSetup);
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            UpgradeHelper.init();
            MovingInteractionBehaviour.REGISTRY.register(
                    ModBlocks.HULL_BLOCK.get(), new HullMovingInteraction());
        });
        ModPackets.register();
    }
}
