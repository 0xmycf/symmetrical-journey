package org.mycf.symmetrical.journey.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Hand;
import org.mycf.symmetrical.journey.static_collections.ModifiedLoot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Consumer;

@Mixin(LootTable.class)
public class LootTableMixin {


    @ModifyArgs(
            method = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootContext;Ljava/util/function/Consumer;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;processStacks(Ljava/util/function/Consumer;)Ljava/util/function/Consumer;")
    )
    private void processStacksDifferently(Args args, LootContext context, Consumer<ItemStack> lootConsumer) {
        var ctx = ((LootContextMixin) (context)).getParams();
        var key = ((Entity) ctx.get(LootContextParameters.THIS_ENTITY));
        if (key != null && ModifiedLoot.Companion.getCONFIGURED_MOB_LOOT().containsKey(key.getType())) {
            args.set(0, processStacksCorrectly(lootConsumer, context));
        }
    }

    private static Consumer<ItemStack> processStacksCorrectly(Consumer<ItemStack> lootConsumer, LootContext context) {
        return (stack) -> {
            if (!ModifiedLoot.Companion.isItemIncluded(stack)
                    || ((LootContextMixin) (context)).getParams().get(LootContextParameters.DAMAGE_SOURCE) == DamageSource.ANVIL
                    || (((LootContextMixin) (context)).getParams().get(LootContextParameters.KILLER_ENTITY) instanceof PlayerEntity
                    && ((PlayerEntity) ((LootContextMixin) (context)).getParams().get(LootContextParameters.KILLER_ENTITY)).getStackInHand(Hand.MAIN_HAND).getItem() == Items.FISHING_ROD
                        && (((Entity) ((LootContextMixin) (context)).getParams().get(LootContextParameters.THIS_ENTITY)).getType() == EntityType.COD
                            || ((Entity) ((LootContextMixin) (context)).getParams().get(LootContextParameters.THIS_ENTITY)).getType() == EntityType.SALMON))) {
                if (stack.getCount() < stack.getMaxCount()) {
                    lootConsumer.accept(stack);
                } else {
                    int i = stack.getCount();

                    while (i > 0) {
                        ItemStack itemStack = stack.copy();
                        itemStack.setCount(Math.min(stack.getMaxCount(), i));
                        i -= itemStack.getCount();
                        lootConsumer.accept(itemStack);
                    }
                }
            }
        };
    }
}
