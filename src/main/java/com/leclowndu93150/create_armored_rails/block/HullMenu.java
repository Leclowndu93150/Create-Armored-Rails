package com.leclowndu93150.create_armored_rails.block;

import com.leclowndu93150.create_armored_rails.health.TrainHealthManager;
import com.leclowndu93150.create_armored_rails.health.TrainHealthSavedData;
import com.leclowndu93150.create_armored_rails.health.UpgradeHelper;
import com.leclowndu93150.create_armored_rails.registry.ModMenuTypes;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class HullMenu extends AbstractContainerMenu {
    public static final int HULL_SLOT_COUNT = 4;
    private static final int[] MODIFIER_X = {55, 77, 99};

    private final ItemStackHandler hullInventory;
    private BlockPos blockPos;
    private int entityId = -1;
    private BlockPos localPos;
    private final Player owner;

    public HullMenu(int containerId, Inventory playerInv, ItemStackHandler hullInventory, BlockPos blockPos) {
        super(ModMenuTypes.HULL_MENU.get(), containerId);
        this.hullInventory = hullInventory;
        this.blockPos = blockPos;
        this.owner = playerInv.player;
        addHullSlots();
        addPlayerInventory(playerInv);
    }

    public HullMenu(int containerId, Inventory playerInv, ItemStackHandler hullInventory, int entityId, BlockPos localPos) {
        super(ModMenuTypes.HULL_MENU.get(), containerId);
        this.hullInventory = hullInventory;
        this.entityId = entityId;
        this.localPos = localPos;
        this.owner = playerInv.player;
        addHullSlots();
        addPlayerInventory(playerInv);
    }

    private void addHullSlots() {
        addSlot(new SlotItemHandler(hullInventory, 0, 18, 41) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return UpgradeHelper.isUpgradeItem(stack);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        for (int i = 0; i < 3; i++) {
            final int slotIndex = i + 1;
            addSlot(new SlotItemHandler(hullInventory, slotIndex, MODIFIER_X[i], 41) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    if (!isModifierSlotUnlocked(slotIndex)) return false;
                    return UpgradeHelper.isModifierItem(stack);
                }

                @Override
                public boolean mayPickup(Player player) {
                    return true;
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean isActive() {
                    return isModifierSlotUnlocked(slotIndex);
                }
            });
        }
    }

    public boolean isModifierSlotUnlocked(int slotIndex) {
        ItemStack upgrade = hullInventory.getStackInSlot(0);
        int unlocked = UpgradeHelper.getModifierSlotCount(upgrade);
        return slotIndex <= unlocked;
    }

    public static HullMenu fromNetwork(int containerId, Inventory playerInv, FriendlyByteBuf buf) {
        boolean isContraption = buf.readBoolean();
        if (isContraption) {
            int entityId = buf.readInt();
            BlockPos localPos = buf.readBlockPos();
            ItemStackHandler handler = new ItemStackHandler(HULL_SLOT_COUNT);
            handler.deserializeNBT(buf.readNbt());
            return new HullMenu(containerId, playerInv, handler, entityId, localPos);
        } else {
            BlockPos pos = buf.readBlockPos();
            ItemStackHandler handler = new ItemStackHandler(HULL_SLOT_COUNT);
            handler.deserializeNBT(buf.readNbt());
            return new HullMenu(containerId, playerInv, handler, pos);
        }
    }

    private void addPlayerInventory(Inventory playerInv) {
        int startX = 20;
        int startY = 96;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInv, col + row * 9 + 9, startX + col * 18, startY + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInv, col, startX + col * 18, startY + 58));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (index < HULL_SLOT_COUNT) {
                if (!moveItemStackTo(stack, HULL_SLOT_COUNT, HULL_SLOT_COUNT + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (UpgradeHelper.isUpgradeItem(stack)) {
                    if (!moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (UpgradeHelper.isModifierItem(stack)) {
                    if (!moveItemStackTo(stack, 1, HULL_SLOT_COUNT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }
            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return result;
    }

    @Override
    public void clicked(int slotId, int button, net.minecraft.world.inventory.ClickType type, Player player) {
        super.clicked(slotId, button, type, player);
        syncToContraption();
    }

    private void syncToContraption() {
        if (!isContraptionMode()) return;
        if (!(owner instanceof ServerPlayer sp)) return;

        Entity entity = sp.level().getEntity(entityId);
        if (!(entity instanceof AbstractContraptionEntity ace)) return;

        StructureBlockInfo oldInfo = ace.getContraption().getBlocks().get(localPos);
        if (oldInfo == null) return;

        CompoundTag newNbt = oldInfo.nbt() != null ? oldInfo.nbt().copy() : new CompoundTag();
        newNbt.put("Inventory", hullInventory.serializeNBT());
        ace.getContraption().getBlocks().put(localPos,
                new StructureBlockInfo(oldInfo.pos(), oldInfo.state(), newNbt));

        if (entity instanceof CarriageContraptionEntity cce) {
            var carriage = cce.getCarriage();
            if (carriage != null) {
                TrainHealthManager.recalculateFromContraption(carriage.train);
                TrainHealthSavedData.markDirty(sp.server);
            }
        }
    }

    @Override
    public boolean stillValid(Player player) {
        if (entityId >= 0) {
            Entity entity = player.level().getEntity(entityId);
            return entity != null && entity.distanceTo(player) < 8.0;
        }
        if (blockPos != null) {
            return player.distanceToSqr(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5) <= 64.0;
        }
        return false;
    }

    public ItemStackHandler getHullInventory() {
        return hullInventory;
    }

    public int getEntityId() {
        return entityId;
    }

    public BlockPos getLocalPos() {
        return localPos;
    }

    public boolean isContraptionMode() {
        return entityId >= 0;
    }
}
