package com.leclowndu93150.create_armored_rails.registry;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.block.HullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CreateArmoredRails.MODID);

    public static final RegistryObject<Block> HULL_BLOCK = BLOCKS.register("hull",
            () -> new HullBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5f)
                    .sound(SoundType.NETHERITE_BLOCK)
                    .requiresCorrectToolForDrops()));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
