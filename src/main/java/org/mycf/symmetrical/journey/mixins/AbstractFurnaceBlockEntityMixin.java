package org.mycf.symmetrical.journey.mixins;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.mycf.symmetrical.journey.static_collections.ModifiedLootKt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin {

    @Inject(method = "Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;setLastRecipe(Lnet/minecraft/recipe/Recipe;)V",
                    shift = At.Shift.AFTER))
    private static void mightBurnYourHouse(World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        final var input = (ItemStack) ((AbstractFurnaceBlockEntityAccessor) blockEntity).getInventory().get(0);
        if ((ModifiedLootKt.isItemIncluded(input) || input.isIn(ItemTags.FISHES)) && world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            if (world.random.nextFloat() > 0.8) {
                for (var blockPos : BlockPos.iterateOutwards(pos, 3, 2, 3)) {
                    for (var direction : Direction.values()) {
                        var offset = blockPos.offset(direction);
                        if (world.getBlockState(blockPos).getMaterial().isBurnable()
                                && world.canSetBlock(offset)
                                && world.isAir(offset)
                                && world.random.nextFloat() > 0.3) {
                            world.setBlockState(offset, AbstractFireBlock.getState(world, offset));
                        }
                    }
                }
            }
        }
    }
}
