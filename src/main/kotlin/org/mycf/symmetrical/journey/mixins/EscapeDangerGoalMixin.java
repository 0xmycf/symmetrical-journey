package org.mycf.symmetrical.journey.mixins;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EscapeDangerGoal.class)
public abstract class EscapeDangerGoalMixin {

    @Shadow @Final protected PathAwareEntity mob;

    @Shadow protected abstract boolean findTarget();

    @Inject(method = "Lnet/minecraft/entity/ai/goal/EscapeDangerGoal;canStart()Z",
            at = @At("HEAD"),
            cancellable = true)
    private void canStart(CallbackInfoReturnable<Boolean> cir){
        var blockPos = this.mob.getBlockPos();

        if (this.mob instanceof ParrotEntity) {
            for (var pos : BlockPos.iterate(blockPos.add(-10 , -6 , -10), blockPos.add(10, 6, 10))){
                if (this.mob.getWorld().getBlockState(pos).isOf(Blocks.CARVED_PUMPKIN)) {
                    cir.setReturnValue(this.findTarget());
                }
            }
        }
    }
}
