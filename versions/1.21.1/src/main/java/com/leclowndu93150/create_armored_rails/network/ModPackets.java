package com.leclowndu93150.create_armored_rails.network;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

@EventBusSubscriber(modid = CreateArmoredRails.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModPackets {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(CreateArmoredRails.MODID);
        registrar.playToClient(TrainHealthSyncPayload.TYPE, TrainHealthSyncPayload.STREAM_CODEC, TrainHealthSyncPayload::handle);
        registrar.playToClient(OpenHullOnContraptionPayload.TYPE, OpenHullOnContraptionPayload.STREAM_CODEC, OpenHullOnContraptionPayload::handle);
        registrar.playToServer(UpdateHullOnContraptionPayload.TYPE, UpdateHullOnContraptionPayload.STREAM_CODEC, UpdateHullOnContraptionPayload::handle);
    }

    public static void sendToTrackingPlayers(Entity entity, Object payload) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, (net.minecraft.network.protocol.common.custom.CustomPacketPayload) payload);
    }

    public static void sendToPlayer(ServerPlayer player, Object payload) {
        PacketDistributor.sendToPlayer(player, (net.minecraft.network.protocol.common.custom.CustomPacketPayload) payload);
    }

    public static void sendToServer(Object payload) {
        PacketDistributor.sendToServer((net.minecraft.network.protocol.common.custom.CustomPacketPayload) payload);
    }
}
