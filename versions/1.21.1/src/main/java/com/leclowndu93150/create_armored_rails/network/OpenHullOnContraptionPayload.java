package com.leclowndu93150.create_armored_rails.network;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.client.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenHullOnContraptionPayload(int entityId, BlockPos localPos, CompoundTag inventoryNBT, int containerId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenHullOnContraptionPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CreateArmoredRails.MODID, "open_hull_on_contraption"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenHullOnContraptionPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, OpenHullOnContraptionPayload::entityId,
            BlockPos.STREAM_CODEC, OpenHullOnContraptionPayload::localPos,
            ByteBufCodecs.COMPOUND_TAG, OpenHullOnContraptionPayload::inventoryNBT,
            ByteBufCodecs.INT, OpenHullOnContraptionPayload::containerId,
            OpenHullOnContraptionPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenHullOnContraptionPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (FMLEnvironment.dist.isClient()) {
                ClientPacketHandler.handleOpenHull(payload.entityId, payload.localPos, payload.inventoryNBT, payload.containerId);
            }
        });
    }
}
