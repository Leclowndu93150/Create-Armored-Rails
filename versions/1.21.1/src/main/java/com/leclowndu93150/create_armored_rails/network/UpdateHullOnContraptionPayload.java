package com.leclowndu93150.create_armored_rails.network;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.leclowndu93150.create_armored_rails.health.TrainHealthSavedData;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record UpdateHullOnContraptionPayload(int entityId, BlockPos localPos, CompoundTag inventoryNBT) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<UpdateHullOnContraptionPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CreateArmoredRails.MODID, "update_hull_on_contraption"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateHullOnContraptionPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, UpdateHullOnContraptionPayload::entityId,
            BlockPos.STREAM_CODEC, UpdateHullOnContraptionPayload::localPos,
            ByteBufCodecs.COMPOUND_TAG, UpdateHullOnContraptionPayload::inventoryNBT,
            UpdateHullOnContraptionPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateHullOnContraptionPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) return;
            Entity entity = player.level().getEntity(payload.entityId);
            if (!(entity instanceof AbstractContraptionEntity ace)) return;

            StructureBlockInfo oldInfo = ace.getContraption().getBlocks().get(payload.localPos);
            if (oldInfo == null) return;

            CompoundTag newNbt = oldInfo.nbt() != null ? oldInfo.nbt().copy() : new CompoundTag();
            newNbt.put("Inventory", payload.inventoryNBT);

            StructureBlockInfo newInfo = new StructureBlockInfo(oldInfo.pos(), oldInfo.state(), newNbt);
            ace.getContraption().getBlocks().put(payload.localPos, newInfo);

            if (entity instanceof CarriageContraptionEntity cce) {
                var carriage = cce.getCarriage();
                if (carriage != null) {
                    TrainHealthManager.recalculateFromContraption(carriage.train);
                    TrainHealthSavedData.markDirty(player.server);
                }
            }
        });
    }
}
