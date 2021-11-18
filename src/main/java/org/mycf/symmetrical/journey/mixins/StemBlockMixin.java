package org.mycf.symmetrical.journey.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Random;

@Mixin(StemBlock.class)
public class StemBlockMixin extends PlantBlock implements CropBlockInvoker {
    @Shadow @Final public static IntProperty AGE;

    @Shadow @Final public static int MAX_AGE;

    protected StemBlockMixin(Settings settings) {
        super(settings);
    }

    @ModifyArgs(method = "Lnet/minecraft/block/StemBlock;randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V",
            at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    private void newNumberStemBlock(Args args, BlockState state, ServerWorld world, BlockPos pos, Random random) {
        float f = CropBlockInvoker.callGetAvailableMoisture(this, world, pos);
        if (world.isRaining()) {
            f += 5; // Gives raining a nice little boost
        }
        if (state.get(AGE) < MAX_AGE - 3) {
            f += 2; // Gives early stages of these crops a little boost
        }
        if (random.nextInt((int) (100f / f) + 1) == 0) {
            args.set(0, 1);
        }
    }
}