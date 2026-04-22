package com.leclowndu93150.create_armored_rails.registry;

import com.google.common.collect.ImmutableSet;
import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagerProfessions {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, CreateArmoredRails.MODID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, CreateArmoredRails.MODID);

    public static final RegistryObject<PoiType> MECHANIC_POI = POI_TYPES.register("mechanic",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.HULL_BLOCK.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> MECHANIC = PROFESSIONS.register("mechanic",
            () -> new VillagerProfession("mechanic",
                    holder -> holder.is(MECHANIC_POI.getKey()),
                    holder -> holder.is(MECHANIC_POI.getKey()),
                    ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_TOOLSMITH));

    public static void register(IEventBus bus) {
        POI_TYPES.register(bus);
        PROFESSIONS.register(bus);
    }
}
