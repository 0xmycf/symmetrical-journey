package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.mycf.symmetrical.journey.ducks.PlayerEntityDuck;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HoglinEntity.class)
public abstract class HoglinEntityMixin extends AnimalEntity {
    protected HoglinEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onDeath(DamageSource source) {
        if (source.getAttacker() instanceof PlayerEntity entity) {
            ((PlayerEntityDuck)entity).setHasKilledHolgin(true);
            ((PlayerEntityDuck)entity).setLastAttackedHoglinTickTime(0);
        }
        super.onDeath(source);
    }
}
