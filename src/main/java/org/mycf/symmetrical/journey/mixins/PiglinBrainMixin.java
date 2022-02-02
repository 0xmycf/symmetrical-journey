package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {

    @Inject(at = @At("HEAD"),
            method = "acceptsForBarter(Lnet/minecraft/item/ItemStack;)Z",
            cancellable = true)
    private static void acceptsForBarter(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(Items.PORKCHOP)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"),
            method = "getBarteredItem(Lnet/minecraft/entity/mob/PiglinEntity;)Ljava/util/List;",
            cancellable = true)
    private static void getBarteredItem(PiglinEntity piglin, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (piglin.getActiveItem().isOf(Items.PORKCHOP)) {
            final var amnt = piglin.getWorld().getRandom().nextInt(1,4);
            final var list = List.of(new ItemStack(Items.GOLD_NUGGET, amnt));
            cir.setReturnValue(list);
        }
    }

}
