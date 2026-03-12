package com.leclowndu93150.create_armored_rails.compat.jade;

import com.leclowndu93150.create_armored_rails.CreateArmoredRails;
import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.health.TrainHealthData;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum TrainEntityComponentProvider implements IEntityComponentProvider, IServerDataProvider<EntityAccessor> {
    INSTANCE;

    private static final ResourceLocation UID = new ResourceLocation(CreateArmoredRails.MODID, "train_health");
    private static final ResourceLocation OBJECT_NAME = new ResourceLocation("jade", "object_name");

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();

        if (data.contains("TrainName")) {
            String trainName = data.getString("TrainName");
            if (!trainName.isEmpty()) {
                tooltip.remove(OBJECT_NAME);
                tooltip.add(0, Component.literal(trainName).withStyle(ChatFormatting.WHITE), OBJECT_NAME);
            }
        }

        if (!data.contains("TrainMaxHP")) {
            tooltip.add(Component.translatable("jade.create_armored_rails.initializing")
                    .withStyle(ChatFormatting.GRAY));
            return;
        }

        float maxHP = data.getFloat("TrainMaxHP");
        float currentHP = data.getFloat("TrainCurrentHP");
        boolean critical = data.getBoolean("TrainCritical");

        if (maxHP <= 0) return;

        int hpPercent = Math.round(currentHP / maxHP * 100f);
        ChatFormatting hpColor;
        if (critical) {
            hpColor = ChatFormatting.DARK_RED;
        } else if (hpPercent <= 50) {
            hpColor = ChatFormatting.YELLOW;
        } else {
            hpColor = ChatFormatting.GREEN;
        }

        tooltip.add(Component.translatable("jade.create_armored_rails.train_health")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(String.format("%.0f / %.0f", currentHP, maxHP)).withStyle(hpColor))
                .append(Component.literal(" (" + hpPercent + "%)").withStyle(ChatFormatting.DARK_GRAY)));

        if (critical) {
            tooltip.add(Component.translatable("jade.create_armored_rails.critical")
                    .withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));
        }

        float speedMultiplier = data.getFloat("TrainSpeedMultiplier");
        int speedPercent = Math.round(speedMultiplier * 100f);
        int speedReduction = 100 - speedPercent;

        MutableComponent speedLine = Component.translatable("jade.create_armored_rails.max_speed")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal(speedPercent + "%").withStyle(speedPercent >= 75 ? ChatFormatting.GREEN : speedPercent >= 50 ? ChatFormatting.YELLOW : ChatFormatting.RED));

        if (speedReduction > 0) {
            speedLine.append(Component.literal(" (-" + speedReduction + "%)").withStyle(ChatFormatting.RED));
        }

        tooltip.add(speedLine);

        float damageReduction = data.getFloat("TrainDamageReduction");
        if (damageReduction > 0) {
            tooltip.add(Component.translatable("jade.create_armored_rails.damage_reduction")
                    .withStyle(ChatFormatting.GRAY)
                    .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal(Math.round(damageReduction * 100) + "%").withStyle(ChatFormatting.BLUE)));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, EntityAccessor accessor) {
        if (!(accessor.getEntity() instanceof CarriageContraptionEntity cce)) return;
        var carriage = cce.getCarriage();
        if (carriage == null) return;

        String trainName = carriage.train.name.getString();
        data.putString("TrainName", trainName);

        TrainHealthData health = TrainHealthManager.get(carriage.train.id);
        if (health == null) {
            if (TrainHealthManager.hasHullBlocks(carriage.train)) {
                TrainHealthManager.recalculateFromContraption(carriage.train);
            } else {
                TrainHealthManager.ensureBaseHealth(carriage.train);
            }
            health = TrainHealthManager.get(carriage.train.id);
        }

        if (health == null || health.getMaxHP() <= 0) return;

        data.putFloat("TrainMaxHP", health.getMaxHP());
        data.putFloat("TrainCurrentHP", health.getCurrentHP());
        data.putBoolean("TrainCritical", health.isCriticalFailure(Config.CRITICAL_FAILURE_THRESHOLD.get()));
        data.putFloat("TrainDamageReduction", health.getDamageReduction());
        data.putFloat("TrainSpeedMultiplier", health.getSpeedMultiplier());
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}
