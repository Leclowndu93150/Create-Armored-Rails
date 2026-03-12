package com.leclowndu93150.create_armored_rails.network;

import com.leclowndu93150.create_armored_rails.health.TrainHealthData;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class TrainHealthSyncPacket {
    private final UUID trainId;
    private final float currentHP;
    private final float maxHP;
    private final boolean criticalFailure;

    public TrainHealthSyncPacket(UUID trainId, float currentHP, float maxHP, boolean criticalFailure) {
        this.trainId = trainId;
        this.currentHP = currentHP;
        this.maxHP = maxHP;
        this.criticalFailure = criticalFailure;
    }

    public static void encode(TrainHealthSyncPacket pkt, FriendlyByteBuf buf) {
        buf.writeUUID(pkt.trainId);
        buf.writeFloat(pkt.currentHP);
        buf.writeFloat(pkt.maxHP);
        buf.writeBoolean(pkt.criticalFailure);
    }

    public static TrainHealthSyncPacket decode(FriendlyByteBuf buf) {
        return new TrainHealthSyncPacket(buf.readUUID(), buf.readFloat(), buf.readFloat(), buf.readBoolean());
    }

    public static void handle(TrainHealthSyncPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TrainHealthData data = TrainHealthManager.getOrCreate(pkt.trainId);
            data.setMaxHP(pkt.maxHP);
            data.setCurrentHP(pkt.currentHP);
        });
        ctx.get().setPacketHandled(true);
    }
}
