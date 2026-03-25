package com.leclowndu93150.create_armored_rails.registry;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.block.HullBlock;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
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

    public static final RegistryObject<Block> TUNGSTEN_ORE = BLOCKS.register("tungsten_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4.5f, 3.0f)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops(), UniformInt.of(2, 5)));

    public static final RegistryObject<Block> DEEPSLATE_TUNGSTEN_ORE = BLOCKS.register("deepslate_tungsten_ore",
            () -> new DropExperienceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(5.5f, 3.0f)
                    .sound(SoundType.DEEPSLATE)
                    .requiresCorrectToolForDrops(), UniformInt.of(2, 5)));

    public static final RegistryObject<Block> RAW_TUNGSTEN_BLOCK = BLOCKS.register("raw_tungsten_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(5.0f, 6.0f)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TUNGSTEN_BLOCK = BLOCKS.register("tungsten_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(6.0f, 8.0f)
                    .sound(SoundType.NETHERITE_BLOCK)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TUNGSTEN_ALLOY_BLOCK = BLOCKS.register("tungsten_alloy_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(7.0f, 10.0f)
                    .sound(SoundType.NETHERITE_BLOCK)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> TUNGSTEN_PLATING_BLOCK = BLOCKS.register("tungsten_plating_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(4.0f, 6.0f)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistryObject<Block> TUNGSTEN_ALLOY_PLATING_BLOCK = BLOCKS.register("tungsten_alloy_plating_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(5.0f, 8.0f)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistryObject<Block> ENCASED_STEEL_PLATING = BLOCKS.register("encased_steel_plating",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(4.0f, 6.0f)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistryObject<Block> ENCASED_TUNGSTEN_PLATING = BLOCKS.register("encased_tungsten_plating",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(5.0f, 8.0f)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistryObject<Block> ENCASED_TUNGSTEN_ALLOY_PLATING = BLOCKS.register("encased_tungsten_alloy_plating",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(6.0f, 10.0f)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistryObject<Block> STEEL_CASING_CONNECTED = BLOCKS.register("steel_casing_connected",
            () -> new CasingBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5f, 6.0f)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistryObject<Block> TUNGSTEN_CASING_CONNECTED = BLOCKS.register("tungsten_casing_connected",
            () -> new CasingBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(4.5f, 8.0f)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static final RegistryObject<Block> TUNGSTEN_ALLOY_CASING_CONNECTED = BLOCKS.register("tungsten_alloy_casing_connected",
            () -> new CasingBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(5.5f, 10.0f)
                    .sound(SoundType.NETHERITE_BLOCK)));

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
