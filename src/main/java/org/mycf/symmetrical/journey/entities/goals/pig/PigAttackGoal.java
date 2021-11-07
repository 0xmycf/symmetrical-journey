package org.mycf.symmetrical.journey.entities.goals.pig;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class PigAttackGoal extends ActiveTargetGoal<PlayerEntity> {
    public PigAttackGoal(PigEntity pig, @Nullable Predicate<LivingEntity> predicate) {
        super(pig, PlayerEntity.class, 10, true, true, predicate);
    }

    // idk anything about this
    @Deprecated
    @Override
    public boolean shouldContinue() {
        return this.targetEntity != null && this.mob.getTarget() != null;
    }

    @Override
    protected void findClosestTarget() {
        super.findClosestTarget();
    }
}