package com.leclowndu93150.create_armored_rails.client;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import net.minecraft.resources.ResourceLocation;

public class ModSpriteShifts {
    public static final CTSpriteShiftEntry STEEL_CASING = omni("steel_casing");
    public static final CTSpriteShiftEntry TUNGSTEN_CASING = omni("tungsten_casing");
    public static final CTSpriteShiftEntry TUNGSTEN_ALLOY_CASING = omni("tungsten_alloy_casing");

    private static CTSpriteShiftEntry omni(String name) {
        return CTSpriteShifter.getCT(AllCTTypes.OMNIDIRECTIONAL,
                new ResourceLocation(CreateArmoredRails.MODID, "block/" + name),
                new ResourceLocation(CreateArmoredRails.MODID, "block/" + name + "_connected"));
    }
}
