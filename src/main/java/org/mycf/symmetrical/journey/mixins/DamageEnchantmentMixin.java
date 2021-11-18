package org.mycf.symmetrical.journey.mixins;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public class DamageEnchantmentMixin {

    @Shadow @Final public int typeIndex;

    @Inject(method = "Lnet/minecraft/enchantment/DamageEnchantment;isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z",
            at = @At("HEAD"),
            cancellable = true)
    private void EnchantFishingRod(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if (stack.isOf(Items.FISHING_ROD) && this.typeIndex == 0){
            cir.setReturnValue(true);
        }
    }
}
