package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.mycf.symmetrical.journey.entities.goals.pig.PigAttackGoal;
import org.mycf.symmetrical.journey.entities.goals.pig.PigMeleeAttackGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PigEntity.class)
public abstract class MixinPigEntity extends AnimalEntity implements Angerable {
    protected MixinPigEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private int symmjour$angerTime;
    @Unique
    private UUID symmjour$targetUuid;
    @Unique
    private static final UniformIntProvider symmjour$ANGER_TIME_RANGE;

    @Inject(method = "Lnet/minecraft/entity/passive/PigEntity;initGoals()V",
            at = @At("TAIL"))
    private void initCustomGoals(CallbackInfo ci) {
        this.goalSelector.add(3, new RevengeGoal(this)); // pigs should not attack in a group
        this.goalSelector.add(0, new PigAttackGoal((PigEntity) (Object) this, this::shouldAngerAt));
        this.goalSelector.add(0, new UniversalAngerGoal<>(this, false)); // idk if I need this, but I'll leave it for now
        this.goalSelector.add(0, new PigMeleeAttackGoal((PigEntity) (Object) this));

    }

    @Inject(method = "Lnet/minecraft/entity/passive/PigEntity;createPigAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;",
            at  = @At("TAIL"))
    private static void addGenericDamage(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir){
        cir.getReturnValue().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4);
    }

    @Override
    public int getAngerTime() {
        return this.symmjour$angerTime;
    }

    @Override
    public void setAngerTime(int ticks) {
        this.symmjour$angerTime = ticks;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.symmjour$targetUuid;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        this.symmjour$targetUuid = uuid;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(symmjour$ANGER_TIME_RANGE.get(this.random));
    }

    static {
        symmjour$ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    }
}
