package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CowEntity.class)
public abstract class MixinCowEntity extends AnimalEntity {
    protected MixinCowEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

//    @Redirect(method = "Lnet/minecraft/entity/passive/CowEntity;initGoals()V",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V"))
//    private void disableRunningAway(GoalSelector instance, int priority, Goal goal) {
//        if (goal instanceof EscapeDangerGoal) {
//            this.goalSelector.add(priority, new RevengeGoal(this).setGroupRevenge());
//        }
//    }

    @Inject(method = "Lnet/minecraft/entity/passive/CowEntity;initGoals()V",
            at = @At(value = "TAIL"))
    private void addNewRevengeGoal(CallbackInfo ci) {
        this.goalSelector.add(0, new RevengeGoal(this).setGroupRevenge());
    }
}
