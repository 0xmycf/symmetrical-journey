package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FishEntity.class)
public class MixinFishEntity extends WaterCreatureEntity {
    protected MixinFishEntity(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canSpawn(WorldView world) {
        if (!this.world.isRaining()){
            return false;
        }
        return super.canSpawn(world);
    }
}
