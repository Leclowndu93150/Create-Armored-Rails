package com.leclowndu93150.create_armored_rails.behaviour;

import com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class VanillaBlockMovingInteraction extends MovingInteractionBehaviour {
    @Override
    public boolean handlePlayerInteraction(Player player, InteractionHand activeHand, BlockPos localPos,
                                           AbstractContraptionEntity contraptionEntity) {
        if (player.level().isClientSide()) return true;
        if (!(player instanceof ServerPlayer sp)) return false;

        StructureBlockInfo info = contraptionEntity.getContraption().getBlocks().get(localPos);
        if (info == null) return false;

        BlockState state = info.state();
        BlockPos fakePos = sp.blockPosition();

        if (state.getBlock() instanceof EntityBlock entityBlock) {
            BlockEntity be = entityBlock.newBlockEntity(fakePos, state);
            if (be == null) return false;
            be.setLevel(sp.serverLevel());
            if (info.nbt() != null) {
                be.load(info.nbt());
            }
            if (be instanceof MenuProvider menuProvider) {
                ContraptionMenuTracker.markWithContext(sp, contraptionEntity.getId(), localPos, be);
                sp.openMenu(menuProvider);
                return true;
            }
            return false;
        }

        ContraptionMenuTracker.mark(sp);

        Vec3 hitVec = Vec3.atCenterOf(fakePos);
        BlockHitResult hit = new BlockHitResult(hitVec, Direction.UP, fakePos, false);
        var result = state.getBlock().use(state, sp.serverLevel(), fakePos, sp, activeHand, hit);

        if (!result.consumesAction()) {
            ContraptionMenuTracker.clear(sp);
            return false;
        }

        return true;
    }
}
