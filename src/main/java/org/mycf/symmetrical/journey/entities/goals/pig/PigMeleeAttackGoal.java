package org.mycf.symmetrical.journey.entities.goals.pig;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.passive.PigEntity;

public class PigMeleeAttackGoal extends MeleeAttackGoal {
    public PigMeleeAttackGoal(PigEntity pig) {
        super(pig, 2, true);
    }

}
