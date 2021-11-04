package org.mycf.symmetrical.journey.mixins;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import org.mycf.symmetrical.journey.static_collections.CropBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(AbstractBlock.class)
public class MixinAbstractBlock {

    @Inject(method = "Lnet/minecraft/block/AbstractBlock;getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/loot/context/LootContext$Builder;)Ljava/util/List;",
            at = @At("HEAD"),
            cancellable = true)
    private void getDroppedStacks(BlockState state, LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (CropBlocks.Companion.getCONFIGURED_BLOCKSSTATES().contains(state)) {
            cir.setReturnValue(Collections.emptyList());
        }
    }
}