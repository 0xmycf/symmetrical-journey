package org.mycf.symmetrical.journey.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldView;
import org.mycf.symmetrical.journey.static_maps.CropBlocks;

public class EatSeedsGoal extends MoveToTargetPosGoal {
    protected int timer;
    private final TargetPredicate CLOSE_GOLEM_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(6.0D);

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
//        if (isBlockOrGolemNearby()) { // always (0,0,0)
//            System.out.println("true");
//            System.out.println(this.getTargetPos());
//        }
        return !this.mob.isSleeping() && super.canStart();
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

//    public boolean isBlockOrGolemNearby() {
//        for (var i = -4; i <= 4; i++) {
//            for (var j = -4; j <= 4; j++) {
//                for (var k = -20; k <= 20; k++) {
//                    var toCheck = this.getTargetPos();
//                    toCheck.add(i, k, j);
//                    if (this.mob.world.getBlockState(toCheck).getBlock() == Blocks.CARVED_PUMPKIN
//                            || this.mob.world.getClosestEntity(IronGolemEntity.class, CLOSE_GOLEM_PREDICATE,
//                            this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(),
//                            this.mob.getBoundingBox().expand(6.0D, 2.0D, 6.0D)) != null
//                            || this.mob.world.getClosestEntity(SnowGolemEntity.class, CLOSE_GOLEM_PREDICATE,
//                            this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(),
//                            this.mob.getBoundingBox().expand(6.0D, 2.0D, 6.0D)) != null) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    This was proof of concept like... but it didnt work
//    problem was with the method above that tragetPos always returns (000)
//
//    @Override
//    protected boolean findTargetPos() {
//        BlockPos blockPos = this.mob.getBlockPos();
//        BlockPos.Mutable mutable = new BlockPos.Mutable();
//
//        for (int k = this.lowestY; k <= 5; k = k > 0 ? -k : 1 - k) {
//            for (int l = 0; l < 12; ++l) {
//                for (int m = 0; m <= l; m = m > 0 ? -m : 1 - m) {
//                    for (int n = m < l && m > -l ? l : 0; n <= l; n = n > 0 ? -n : 1 - n) {
//                        mutable.set((Vec3i) blockPos, m, k - 1, n);
//                        if (this.mob.isInWalkTargetRange(mutable) && this.isTargetPos(this.mob.world, mutable)) {
//                            this.targetPos = mutable;
//                            for (int kk = this.lowestY; kk <= 5; kk = kk > 0 ? -kk : 1 - kk) {
//                                for (int ll = 0; ll < 12; ++ll) {
//                                    for (int mm = 0; mm <= l; mm = mm > 0 ? -mm : 1 - mm) {
//                                        for (int nn = mm < ll && mm > -ll ? ll : 0; nn <= ll; nn = nn > 0 ? -nn : 1 - nn) {
//                                            mutable.set(blockPos, mm, kk, nn);
//                                            if (!(this.mob.world.getBlockState(mutable).getBlock() == Blocks.CARVED_PUMPKIN
//                                                    || this.mob.world.getClosestEntity(IronGolemEntity.class, CLOSE_GOLEM_PREDICATE,
//                                                    this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(),
//                                                    this.mob.getBoundingBox().expand(6.0D, 2.0D, 6.0D)) != null
//                                                    || this.mob.world.getClosestEntity(SnowGolemEntity.class, CLOSE_GOLEM_PREDICATE,
//                                                    this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(),
//                                                    this.mob.getBoundingBox().expand(6.0D, 2.0D, 6.0D)) != null))
//
//                                                 return true;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//    }
}