package org.mycf.symmetrical.journey.entities.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldView;
import org.mycf.symmetrical.journey.static_maps.CropBlocks;

public class EatSeedsGoal extends MoveToTargetPosGoal {
    protected int timer;

    public EatSeedsGoal(PathAwareEntity entity, double d, int i, int j) {
        super(entity, d, i, j);
    }

    public double getDesiredSquaredDistanceToTarget() {
        return 2.0D;
    }

    public boolean shouldResetPath() {
        return this.tryingTime % 100 == 0;
    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return CropBlocks.Companion.getCONFIGURED_BLOCKSSTATES().contains(blockState) && !(blockState.getBlock() == Blocks.BEETROOTS);
    }

    public void tick() {
        if (this.hasReached()) {
            if (this.timer >= 40) {
                this.eatSeedBlock();
            } else {
                ++this.timer;
            }
        } else if (!this.hasReached() && this.mob.getRandom().nextFloat() < 0.05F) {

            this.mob.playSound(SoundEvents.ENTITY_PARROT_AMBIENT, 1.0F, 1.0F);
        }

        super.tick();
    }

    protected void eatSeedBlock() {
        if (this.mob.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            BlockState blockState = this.mob.world.getBlockState(this.targetPos);
            if (CropBlocks.Companion.getCONFIGURED_BLOCKSSTATES().contains(blockState) && !(blockState.getBlock() == Blocks.BEETROOTS)) {
                this.pickSeed(blockState);
            }
        }
    }

    private void pickSeed(BlockState state) {
        if (!(state.getBlock() == Blocks.BEETROOTS)) {
            int i = (Integer) state.get(CropBlock.AGE);
            state.with(CropBlock.AGE, 1);
            int j = 1 + this.mob.world.random.nextInt(2) + (i == 3 ? 1 : 0);

            this.mob.playSound(SoundEvents.ENTITY_PARROT_EAT, 1.0F, 1.0F);
            this.mob.world.breakBlock(this.targetPos, false);
        }
    }

    public boolean canStart() {
        return !this.mob.isSleeping() && super.canStart() && noPumpkinNearby();
    }

    public void start() {
        this.timer = 0;
        ((ParrotEntity) this.mob).setSitting(false);
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    private boolean noPumpkinNearby() {
        var pos = this.targetPos;
        System.out.println("targetPos = " + targetPos);

        if (this.mob instanceof ParrotEntity) {
            for (var blockPos : BlockPos.iterate(pos.add(-10, -6, -10), pos.add(10, 6, 10))) {
                System.out.println(this.mob.getWorld().getBlockState(blockPos).isOf(Blocks.CARVED_PUMPKIN));
                if (this.mob.getWorld().getBlockState(blockPos).isOf(Blocks.CARVED_PUMPKIN)) {
                    return false;
                }
            }
        }
        return true;
    }
}