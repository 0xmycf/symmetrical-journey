package org.mycf.symmetrical.journey.entities.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldView;
import org.mycf.symmetrical.journey.static_collections.CropBlocks;

public class EatSeedsGoal extends MoveToTargetPosGoal {
    protected int timer;
    private static final TargetPredicate CLOSE_GOLEM_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(10.0D);

    public EatSeedsGoal(PathAwareEntity entity, double d, int i, int j) {
        super(entity, d, i, j);
    }

    public double getDesiredSquaredDistanceToTarget() {
        return 2.0D;
    }

    public boolean shouldResetPath() {
        return this.tryingTime % 100 == 0;
    }

    public boolean canStart() {
        return !this.mob.isSleeping() && super.canStart() && noPumpkinNearby() && noGolemNearby() && !((ParrotEntity) this.mob).isTamed();
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
//            state.with(CropBlock.AGE, 1);

            this.mob.playSound(SoundEvents.ENTITY_PARROT_EAT, 1.0F, 1.0F);
            this.mob.world.breakBlock(this.targetPos, false);
        }
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

        if (this.mob instanceof ParrotEntity) {
            for (var blockPos : BlockPos.iterate(pos.add(-10, -6, -10), pos.add(10, 6, 10))) {
                if (this.mob.getWorld().getBlockState(blockPos).isOf(Blocks.CARVED_PUMPKIN)) {
                    return false;
                }
            }
        }
        return true;
    }

    @SuppressWarnings("DuplicatedCode")
    public boolean noGolemNearby() {
        LivingEntity golemEntity = this.mob.world.getClosestEntity(GolemEntity.class, CLOSE_GOLEM_PREDICATE, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(), this.mob.getBoundingBox().expand(10.0D, 5.0D, 10.0D));
        LivingEntity snowGolemEntity = this.mob.world.getClosestEntity(SnowGolemEntity.class, CLOSE_GOLEM_PREDICATE, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(), this.mob.getBoundingBox().expand(10.0D, 5.0D, 10.0D));
        PlayerEntity player = this.mob.world.getClosestPlayer(CLOSE_GOLEM_PREDICATE, this.mob);
        if (player != null) {
            boolean playerHasPumpkin = player.getInventory().armor.get(3).isOf(Blocks.CARVED_PUMPKIN.asItem());
            return golemEntity == null && snowGolemEntity == null && !playerHasPumpkin;
        }
        return golemEntity == null && snowGolemEntity == null;
    }
}