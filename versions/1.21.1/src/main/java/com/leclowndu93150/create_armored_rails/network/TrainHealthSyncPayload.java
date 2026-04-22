package com.leclowndu93150.create_armored_rails.network;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.health.TrainHealthData;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public record TrainHealthSyncPayload(UUID trainId, float currentHP, float maxHP, boolean criticalFailure) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TrainHealthSyncPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CreateArmoredRails.MODID, "train_health_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TrainHealthSyncPayload> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, TrainHealthSyncPayload::trainId,
            ByteBufCodecs.FLOAT, TrainHealthSyncPayload::currentHP,
            ByteBufCodecs.FLOAT, TrainHealthSyncPayload::maxHP,
            ByteBufCodecs.BOOL, TrainHealthSyncPayload::criticalFailure,
            TrainHealthSyncPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(TrainHealthSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            TrainHealthData data = TrainHealthManager.getOrCreate(payload.trainId);
            data.setMaxHP(payload.maxHP);
            data.setCurrentHP(payload.currentHP);
        });
    }
}
