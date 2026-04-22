package com.leclowndu93150.create_armored_rails.client;

import com.leclowndu93150.create_armored_rails.registry.ModBlocks;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.decoration.encasing.EncasedCTBehaviour;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModCasingCTSetup {

    public static void register() {
        CreateClient.CASING_CONNECTIVITY.makeCasing(
                ModBlocks.STEEL_CASING_CONNECTED.get(), ModSpriteShifts.STEEL_CASING);
        CreateClient.CASING_CONNECTIVITY.makeCasing(
                ModBlocks.TUNGSTEN_CASING_CONNECTED.get(), ModSpriteShifts.TUNGSTEN_CASING);
        CreateClient.CASING_CONNECTIVITY.makeCasing(
                ModBlocks.TUNGSTEN_ALLOY_CASING_CONNECTED.get(), ModSpriteShifts.TUNGSTEN_ALLOY_CASING);

        registerCTModel(ModBlocks.STEEL_CASING_CONNECTED.get(), ModSpriteShifts.STEEL_CASING);
        registerCTModel(ModBlocks.TUNGSTEN_CASING_CONNECTED.get(), ModSpriteShifts.TUNGSTEN_CASING);
        registerCTModel(ModBlocks.TUNGSTEN_ALLOY_CASING_CONNECTED.get(), ModSpriteShifts.TUNGSTEN_ALLOY_CASING);
    }

    private static void registerCTModel(Block block, CTSpriteShiftEntry shift) {
        EncasedCTBehaviour behaviour = new EncasedCTBehaviour(shift);
        CreateClient.MODEL_SWAPPER.getCustomBlockModels()
                .register(RegisteredObjectsHelper.getKeyOrThrow(block), model -> new CTModel(model, behaviour));
    }
}
