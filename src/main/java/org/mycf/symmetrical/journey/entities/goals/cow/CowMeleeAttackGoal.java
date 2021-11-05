package org.mycf.symmetrical.journey.entities.goals.cow;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.passive.CowEntity;

public class CowMeleeAttackGoal extends MeleeAttackGoal {
    public CowMeleeAttackGoal(CowEntity cow) {
        super(cow, 2, true);
    }

}
