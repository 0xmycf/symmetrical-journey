package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.TimeHelper;
import net.minecraft.world.World;
import org.mycf.symmetrical.journey.ducks.PlayerEntityDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements PlayerEntityDuck {

    private boolean symmjour$hasKilledHoglin;
    private int symmjour$lastAttackedHoglinTickTime;

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean getHasKilledHolgin() {
        return this.symmjour$hasKilledHoglin;
    }

    @Override
    public void setHasKilledHolgin(boolean hasKilledHolgin) {
        this.symmjour$hasKilledHoglin = hasKilledHolgin;
    }

    @Override
    public int getHoglinTickTime() {
        return TimeHelper.betweenSeconds(60, 60 * 5).get(this.world.getRandom());
    }

    @Override
    public int getLastAttackedHoglinTickTime() {
        return this.symmjour$lastAttackedHoglinTickTime;
    }

    @Override
    public void setLastAttackedHoglinTickTime(int lastAttackedHoglinTickTime) {
        this.symmjour$lastAttackedHoglinTickTime = lastAttackedHoglinTickTime;
    }

    @Inject(method = "Lnet/minecraft/entity/LivingEntity;tick()V", at = @At("HEAD"))
    private void tickHoglinTime(CallbackInfo ci) {
        if (symmjour$hasKilledHoglin) {
            if (this.getLastAttackedHoglinTickTime() == this.getHoglinTickTime()) {
                this.setHasKilledHolgin(false);
            } else {
                this.setLastAttackedHoglinTickTime(this.getLastAttackedHoglinTickTime() + 1);
            }
        }
    }

    @Inject(method = "Lnet/minecraft/entity/LivingEntity;writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At("TAIL"))
    private void saveHoglinTimes(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("symmjour$hasKilledHoglin", this.getHasKilledHolgin());
        nbt.putInt("symmjour$lastAttackedHoglinTickTime", this.getLastAttackedHoglinTickTime());
    }

    @Inject(method = "Lnet/minecraft/entity/LivingEntity;readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V",
            at = @At("TAIL"))
    private void getHoglinTimes(NbtCompound nbt, CallbackInfo ci) {
        this.setHasKilledHolgin(nbt.getBoolean("symmjour$hasKilledHoglin"));
        this.setLastAttackedHoglinTickTime(nbt.getInt("symmjour$lastAttackedHoglinTickTime"));
    }

}
