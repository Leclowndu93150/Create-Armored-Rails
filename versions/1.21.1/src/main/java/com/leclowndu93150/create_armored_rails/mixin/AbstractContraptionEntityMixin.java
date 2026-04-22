package com.leclowndu93150.create_armored_rails.mixin;

import com.leclowndu93150.create_armored_rails.Config;
import com.leclowndu93150.create_armored_rails.health.TrainHealthData;
import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.leclowndu93150.create_armored_rails.health.TrainHealthSavedData;
import com.leclowndu93150.create_armored_rails.health.UpgradeHelper;
import com.leclowndu93150.create_armored_rails.block.HullMenu;
import com.leclowndu93150.create_armored_rails.network.ModPackets;
import com.leclowndu93150.create_armored_rails.network.TrainHealthSyncPayload;
import com.leclowndu93150.create_armored_rails.registry.ModBlocks;
import com.leclowndu93150.create_armored_rails.registry.ModItems;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageContraption;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.neoforged.neoforge.items.ItemStackHandler;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContraptionEntity.class)
public abstract class AbstractContraptionEntityMixin {

    private static final Logger LOGGER = LogUtils.getLogger();

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void armoredRails$handleTrainDamage(DamageSource source, float amount,
                                                 CallbackInfoReturnable<Boolean> cir) {
        AbstractContraptionEntity self = (AbstractContraptionEntity)(Object)this;
        if (!(self instanceof CarriageContraptionEntity cce)) return;

        var carriage = cce.getCarriage();
        if (carriage == null) return;

        TrainHealthData data = TrainHealthManager.get(carriage.train.id);
        if (data == null || data.getMaxHP() <= 0) return;

        boolean isFire = source.is(DamageTypeTags.IS_FIRE);
        if (isFire && data.isFireResistant()) {
            cir.setReturnValue(false);
            return;
        }

        boolean isExplosion = source.is(DamageTypeTags.IS_EXPLOSION);
        boolean isProjectile = source.is(DamageTypeTags.IS_PROJECTILE);
        data.takeDamageWithType(amount, isExplosion, isProjectile);

        if (self.level() instanceof ServerLevel serverLevel) {
            data.setLastDamagedTick(serverLevel.getGameTime());

            if (data.getThornsDamage() > 0 && source.getEntity() instanceof LivingEntity attacker) {
                attacker.hurt(self.damageSources().thorns(self), data.getThornsDamage());
            }

            if (data.getCurrentHP() <= 0 && Config.EXPLODE_ON_DEATH.get()) {
                TrainHealthManager.destroyTrain(carriage.train, serverLevel);
                TrainHealthSavedData.markDirty(serverLevel.getServer());
                cir.setReturnValue(true);
                return;
            }

            serverLevel.playSound(null, self.getX(), self.getY(), self.getZ(),
                    SoundEvents.IRON_GOLEM_DAMAGE, SoundSource.NEUTRAL, 1.0f, 0.8f);

            serverLevel.sendParticles(ParticleTypes.SMOKE,
                    self.getX(), self.getY() + 1.0, self.getZ(),
                    5, 0.5, 0.3, 0.5, 0.02);

            TrainHealthSavedData.markDirty(serverLevel.getServer());
            TrainHealthManager.syncHealthToContraption(carriage.train);
            ModPackets.sendToTrackingPlayers(self, new TrainHealthSyncPayload(
                    carriage.train.id, data.getCurrentHP(), data.getMaxHP(),
                    data.isCriticalFailure(Config.CRITICAL_FAILURE_THRESHOLD.get())));
        }

        cir.setReturnValue(true);
    }

    @Inject(method = "handlePlayerInteraction", at = @At("HEAD"), cancellable = true, remap = false)
    private void armoredRails$handleRepairInteraction(Player player, BlockPos localPos, Direction side,
                                                       InteractionHand interactionHand,
                                                       CallbackInfoReturnable<Boolean> cir) {
        AbstractContraptionEntity self = (AbstractContraptionEntity)(Object)this;
        if (!(self instanceof CarriageContraptionEntity cce)) return;
        if (interactionHand != InteractionHand.MAIN_HAND) return;
        if (!player.getMainHandItem().is(ModItems.REPAIR_HAMMER.get())) return;

        if (player.level().isClientSide()) {
            cir.setReturnValue(true);
            return;
        }

        if (!(player instanceof ServerPlayer sp)) return;

        var carriage = cce.getCarriage();
        if (carriage == null) return;

        TrainHealthData data = TrainHealthManager.get(carriage.train.id);
        if (data == null) return;

        if (data.getCurrentHP() >= data.getMaxHP()) {
            sp.displayClientMessage(
                    Component.translatable("message.create_armored_rails.full_health")
                            .withStyle(ChatFormatting.GREEN), true);
            cir.setReturnValue(true);
            return;
        }

        String requiredMaterial = findRepairMaterial(cce);
        if (requiredMaterial.isEmpty()) {
            requiredMaterial = Config.BASE_REPAIR_MATERIAL.get();
        }

        boolean skipConsume = data.getRepairCostReduction() > 0
                && sp.level().getRandom().nextFloat() < data.getRepairCostReduction();

        if (!skipConsume) {
            ItemStack offHand = player.getOffhandItem();
            var requiredItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse(requiredMaterial));
            if (requiredItem == null || !offHand.is(requiredItem)) {
                sp.displayClientMessage(
                        Component.translatable("message.create_armored_rails.wrong_material")
                                .withStyle(ChatFormatting.RED), true);
                cir.setReturnValue(true);
                return;
            }
            offHand.shrink(1);
        }

        data.heal(Config.REPAIR_AMOUNT_PER_HIT.get());

        self.level().playSound(null, self.getX(), self.getY(), self.getZ(),
                SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.5f, 1.2f);

        sp.displayClientMessage(
                Component.translatable("message.create_armored_rails.repaired")
                        .withStyle(ChatFormatting.GREEN), true);

        TrainHealthSavedData.markDirty(sp.server);
        TrainHealthManager.syncHealthToContraption(carriage.train);
        ModPackets.sendToTrackingPlayers(self, new TrainHealthSyncPayload(
                carriage.train.id, data.getCurrentHP(), data.getMaxHP(),
                data.isCriticalFailure(Config.CRITICAL_FAILURE_THRESHOLD.get())));

        cir.setReturnValue(true);
    }

    private static String findRepairMaterial(CarriageContraptionEntity cce) {
        if (!(cce.getContraption() instanceof CarriageContraption cc)) return "";
        for (StructureBlockInfo info : cc.getBlocks().values()) {
            if (info.state().getBlock() != ModBlocks.HULL_BLOCK.get()) continue;
            if (info.nbt() != null && info.nbt().contains("Inventory")) {
                ItemStackHandler handler = HullMenu.loadInventory(cce.registryAccess(), info.nbt().getCompound("Inventory"));
                return UpgradeHelper.getRepairMaterial(handler.getStackInSlot(0));
            }
            return "";
        }
        return "";
    }
}
