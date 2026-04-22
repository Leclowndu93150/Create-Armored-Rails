package com.leclowndu93150.create_armored_rails.behaviour;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.block.HullMenu;
import com.leclowndu93150.create_armored_rails.health.TrainHealthData;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.leclowndu93150.create_armored_rails.health.TrainHealthSavedData;
import com.leclowndu93150.create_armored_rails.health.UpgradeHelper;
import com.leclowndu93150.create_armored_rails.mixin.ServerPlayerAccessor;
import com.leclowndu93150.create_armored_rails.network.ModPackets;
import com.leclowndu93150.create_armored_rails.network.OpenHullOnContraptionPacket;
import com.leclowndu93150.create_armored_rails.network.TrainHealthSyncPacket;
import com.leclowndu93150.create_armored_rails.registry.ModItems;
import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class HullMovingInteraction extends MovingInteractionBehaviour {
    @Override
    public boolean handlePlayerInteraction(Player player, InteractionHand activeHand, BlockPos localPos,
                                           AbstractContraptionEntity contraptionEntity) {
        if (player.level().isClientSide()) return true;
        if (!(player instanceof ServerPlayer sp)) return false;

        ItemStack mainHand = player.getMainHandItem();

        if (mainHand.is(ModItems.REPAIR_HAMMER.get())) {
            return handleRepair(sp, localPos, contraptionEntity);
        }

        return openGUI(sp, localPos, contraptionEntity);
    }

    private boolean openGUI(ServerPlayer player, BlockPos localPos, AbstractContraptionEntity entity) {
        StructureBlockInfo info = entity.getContraption().getBlocks().get(localPos);
        if (info == null) return false;

        ItemStackHandler handler = info.nbt() != null && info.nbt().contains("Inventory")
                ? HullMenu.loadInventory(info.nbt().getCompound("Inventory"))
                : new ItemStackHandler(HullMenu.HULL_SLOT_COUNT);

        ((ServerPlayerAccessor) player).invokeNextContainerCounter();
        int containerId = ((ServerPlayerAccessor) player).getContainerCounter();
        HullMenu serverMenu = new HullMenu(containerId, player.getInventory(), handler, entity.getId(), localPos);
        player.containerMenu = serverMenu;
        player.initMenu(serverMenu);
        ModPackets.sendToPlayer(player, new OpenHullOnContraptionPacket(
                entity.getId(), localPos, handler.serializeNBT(), containerId));
        return true;
    }

    private boolean handleRepair(ServerPlayer player, BlockPos localPos, AbstractContraptionEntity entity) {
        if (!(entity instanceof CarriageContraptionEntity cce)) return false;
        var carriage = cce.getCarriage();
        if (carriage == null) return false;

        TrainHealthData data = TrainHealthManager.get(carriage.train.id);
        if (data == null || data.getCurrentHP() >= data.getMaxHP()) return false;

        StructureBlockInfo info = entity.getContraption().getBlocks().get(localPos);
        if (info == null) return false;

        String requiredMaterial = "";
        if (info.nbt() != null && info.nbt().contains("Inventory")) {
            ItemStackHandler handler = HullMenu.loadInventory(info.nbt().getCompound("Inventory"));
            requiredMaterial = UpgradeHelper.getRepairMaterial(handler.getStackInSlot(0));
        }

        if (requiredMaterial.isEmpty()) {
            data.heal(Config.REPAIR_AMOUNT_PER_HIT.get());
        } else {
            ItemStack offHand = player.getOffhandItem();
            var requiredItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(requiredMaterial));
            if (requiredItem == null || !offHand.is(requiredItem)) {
                player.displayClientMessage(
                        Component.translatable("message.create_armored_rails.wrong_material")
                                .withStyle(ChatFormatting.RED), true);
                return false;
            }
            offHand.shrink(1);
            data.heal(Config.REPAIR_AMOUNT_PER_HIT.get());
        }

        player.level().playSound(null, player.blockPosition(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.5f, 1.2f);

        boolean wasCritical = data.isCriticalFailure(Config.CRITICAL_FAILURE_THRESHOLD.get());
        if (!wasCritical && data.getHealthPercentage() > 0) {
            player.displayClientMessage(
                    Component.translatable("message.create_armored_rails.repaired")
                            .withStyle(ChatFormatting.GREEN), true);
        }

        TrainHealthSavedData.markDirty(player.server);
        ModPackets.sendToTrackingPlayers(entity, new TrainHealthSyncPacket(
                carriage.train.id, data.getCurrentHP(), data.getMaxHP(),
                data.isCriticalFailure(Config.CRITICAL_FAILURE_THRESHOLD.get())));

        return true;
    }
}
