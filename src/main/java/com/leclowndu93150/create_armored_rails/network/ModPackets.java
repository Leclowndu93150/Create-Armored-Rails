package com.leclowndu93150.create_armored_rails.network;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {
    private static final String PROTOCOL = "1";
    private static SimpleChannel CHANNEL;
    private static int id = 0;

    public static void register() {
        CHANNEL = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(CreateArmoredRails.MODID, "main"))
                .networkProtocolVersion(() -> PROTOCOL)
                .clientAcceptedVersions(PROTOCOL::equals)
                .serverAcceptedVersions(PROTOCOL::equals)
                .simpleChannel();

        CHANNEL.registerMessage(id++, TrainHealthSyncPacket.class,
                TrainHealthSyncPacket::encode, TrainHealthSyncPacket::decode, TrainHealthSyncPacket::handle);
        CHANNEL.registerMessage(id++, OpenHullOnContraptionPacket.class,
                OpenHullOnContraptionPacket::encode, OpenHullOnContraptionPacket::decode, OpenHullOnContraptionPacket::handle);
        CHANNEL.registerMessage(id++, UpdateHullOnContraptionPacket.class,
                UpdateHullOnContraptionPacket::encode, UpdateHullOnContraptionPacket::decode, UpdateHullOnContraptionPacket::handle);
    }

    public static void sendToTrackingPlayers(Entity entity, Object packet) {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), packet);
    }

    public static void sendToPlayer(ServerPlayer player, Object packet) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToServer(Object packet) {
        CHANNEL.sendToServer(packet);
    }

    public static SimpleChannel getChannel() {
        return CHANNEL;
    }
}
