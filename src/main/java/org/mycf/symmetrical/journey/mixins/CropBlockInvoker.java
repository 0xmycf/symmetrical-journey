package org.mycf.symmetrical.journey.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CropBlock.class)
public interface CropBlockInvoker {

    @Invoker
    static float callGetAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        throw new AssertionError("Called Mixin not true method! - " +
                "org.mycf.symmetrical.journey.mixins.CropBlockInvoker.callGetAvailableMoisture");
    }
}
