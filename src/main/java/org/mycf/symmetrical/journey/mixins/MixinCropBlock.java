package org.mycf.symmetrical.journey.mixins;

import net.minecraft.block.BeetrootsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.mycf.symmetrical.journey.static_maps.CropBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Random;

@Mixin(CropBlock.class)
public abstract class MixinCropBlock {

    @Shadow
    protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        return 1;
    }

    @Shadow protected abstract int getAge(BlockState state);

    @Shadow public abstract int getMaxAge();

    /**
     * This takes approx. one real life hour to grow.
     */
    @ModifyArgs(
            method = "Lnet/minecraft/block/CropBlock;randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I")
    )
    private void newNumber(Args args, BlockState state, ServerWorld world, BlockPos pos, Random random) {
        float f = getAvailableMoisture((Block) (Object) this, world, pos);
        if (world.isRaining()) {
            f += 5; // Gives raining a nice little boost
        }
        if (this.getAge(state) < this.getMaxAge() - 3) {
            f += 2; // Gives early stages of these crops a little boost
        }
        if (random.nextInt((int) (100f / f) + 1) == 0) {
            args.set(0, 1);
        }
    }
}
