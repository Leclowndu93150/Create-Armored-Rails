package com.leclowndu93150.create_armored_rails.registry;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.block.HullBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CreateArmoredRails.MODID);

    public static final RegistryObject<BlockEntityType<HullBlockEntity>> HULL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("hull",
                    () -> BlockEntityType.Builder.of(HullBlockEntity::new, ModBlocks.HULL_BLOCK.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
