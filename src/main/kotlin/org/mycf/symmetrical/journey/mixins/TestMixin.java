package org.mycf.symmetrical.journey.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.CocoaBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CocoaBlock.class)
public abstract class TestMixin {
    @ModifyArg(method = "Lnet/minecraft/block/CocoaBlock;canPlaceAt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/tag/Tag;)Z"), index = 0)
    private Tag<Block> mixin(Tag<Block> tag) {
        return BlockTags.LEAVES;
    }
}