package com.leclowndu93150.create_armored_rails;

import com.leclowndu93150.create_armored_rails.behaviour.HullMovingInteraction;
import com.leclowndu93150.create_armored_rails.client.ClientSetup;
import com.leclowndu93150.create_armored_rails.health.UpgradeHelper;
import com.leclowndu93150.create_armored_rails.registry.*;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(CreateArmoredRails.MODID)
public class CreateArmoredRails {
    public static final String MODID = "create_armored_rails";

    public CreateArmoredRails(IEventBus modEventBus, ModContainer container) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        //ModVillagerProfessions.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        Config.load();
        if (FMLEnvironment.dist.isClient()) {
            ClientSetup.init(modEventBus, container);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            UpgradeHelper.init();
            MovingInteractionBehaviour.REGISTRY.register(
                    ModBlocks.HULL_BLOCK.get(), new HullMovingInteraction());
        });
    }
}
