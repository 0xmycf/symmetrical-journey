package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.PiglinSpecificSensor;
import net.minecraft.entity.mob.PiglinBrain;
import org.mycf.symmetrical.journey.ducks.PlayerEntityDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PiglinSpecificSensor.class)
public class MixinPiglinSpecificSensor {

    // This might lead to compatability issues, I could just inject into the net.minecraft.entity.mob.PiglinBrain.wearsGoldArmor method...
    @Deprecated
    @Redirect(method = "sense",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PiglinBrain;wearsGoldArmor(Lnet/minecraft/entity/LivingEntity;)Z"))
    private boolean didPlayerKillHoglin(LivingEntity entity) {
        if (((PlayerEntityDuck) entity).getHasKilledHolgin())
            return false;
        else
            return PiglinBrain.wearsGoldArmor(entity);
    }
}
