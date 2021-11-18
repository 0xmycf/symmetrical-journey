package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.passive.RabbitEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RabbitEntity.class)
public class RabbitEntityMixin {

    @ModifyConstant(method = "Lnet/minecraft/entity/passive/RabbitEntity;initGoals()V",
                    constant = @Constant(doubleValue = 2.2D))
    private double modifyFleeSpeed(double oldValue) {
        return oldValue * 4;
    }
}
