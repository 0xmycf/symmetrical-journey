package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.mycf.symmetrical.journey.ducks.CowDuck;
import org.mycf.symmetrical.journey.entities.goals.CowAttackGoal;
import org.mycf.symmetrical.journey.entities.goals.CowMeleeAttackGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(CowEntity.class)
public abstract class MixinCowEntity extends AnimalEntity implements CowDuck, Angerable {

    @Unique
    private int angerTime;
    @Unique
    private UUID targetUuid;
    @Unique
    private static final UniformIntProvider ANGER_TIME_RANGE;


    protected MixinCowEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "Lnet/minecraft/entity/passive/CowEntity;initGoals()V",
            at = @At(value = "TAIL"))
    private void addNewRevengeGoal(CallbackInfo ci) {
        this.goalSelector.add(3, new RevengeGoal(this).setGroupRevenge());
        this.goalSelector.add(0, new CowAttackGoal((CowEntity) (Object) this, this::shouldAngerAt));
        this.goalSelector.add(0, new UniversalAngerGoal<>(this, false)); // idk if I need this, but I'll leave it for now
        this.goalSelector.add(0, new CowMeleeAttackGoal((CowEntity) (Object) this));
    }


    @Override
    protected void mobTick() {
        if (!this.world.isClient()) {
            this.tickAngerLogic((ServerWorld) this.world, false);
        }
        super.mobTick();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity) {
            this.setTarget((LivingEntity) source.getAttacker());
        }
        return super.damage(source, amount);
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngerTime(int ticks) {
        this.angerTime = ticks;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.targetUuid;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        this.targetUuid = uuid;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }

    @Override
    public boolean shouldAnger(LivingEntity entity) {
        return this.shouldAngerAt(entity);
    }


    @Inject(method = "createCowAttributes", at = @At(value = "TAIL"))
    private static void addAttackDamage(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }

    static {
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    }
}