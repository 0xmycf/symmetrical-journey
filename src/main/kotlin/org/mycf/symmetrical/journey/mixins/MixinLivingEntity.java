package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Deprecated
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    public MixinLivingEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

//    @Inject(method = "Lnet/minecraft/entity/LivingEntity;dropLoot(Lnet/minecraft/entity/damage/DamageSource;Z)V", at = @At("HEAD"), cancellable = true)
//    protected void dropLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
//        ActionResult result = DropLootCallback.EVENT.invoker().onDrop(source, causedByPlayer, (LivingEntity) (Object) this);
//
//        if (result == ActionResult.FAIL) {
//            ci.cancel();
//        }
//    }

}
