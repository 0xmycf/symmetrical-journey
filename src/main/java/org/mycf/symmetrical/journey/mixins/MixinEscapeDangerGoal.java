package org.mycf.symmetrical.journey.mixins;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EscapeDangerGoal.class)
public abstract class MixinEscapeDangerGoal {

    @Unique
    private static final TargetPredicate CLOSE_GOLEM_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(6.0D);


    @Shadow
    @Final
    protected PathAwareEntity mob;

    @Shadow
    protected abstract boolean findTarget();

    @Inject(method = "Lnet/minecraft/entity/ai/goal/EscapeDangerGoal;canStart()Z",
            at = @At("HEAD"),
            cancellable = true)
    private void canStart(CallbackInfoReturnable<Boolean> cir) {
        var blockPos = this.mob.getBlockPos();

        if (this.mob instanceof ParrotEntity && !((ParrotEntity) this.mob).isTamed()) {
            for (var pos : BlockPos.iterate(blockPos.add(-10, -6, -10), blockPos.add(10, 6, 10))) {
                if (this.mob.getWorld().getBlockState(pos).isOf(Blocks.CARVED_PUMPKIN)) {
                    cir.setReturnValue(this.findTarget());
                }
            }
            if (!noGolemNearby()) {
                cir.setReturnValue(this.findTarget());
            }
        }
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