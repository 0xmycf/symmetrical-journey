package org.mycf.symmetrical.journey.static_collections

import net.minecraft.block.BeetrootsBlock
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.CropBlock

/**
 * Blocks that are specified in here won't drop any items
 */
val CONFIGURED_BLOCKSTATES = setOf<BlockState>(
    Blocks.WHEAT.defaultState,
    Blocks.WHEAT.defaultState.with(CropBlock.AGE, 1),
    Blocks.WHEAT.defaultState.with(CropBlock.AGE, 2),
    Blocks.WHEAT.defaultState.with(CropBlock.AGE, 3),
    Blocks.WHEAT.defaultState.with(CropBlock.AGE, 4),
    Blocks.WHEAT.defaultState.with(CropBlock.AGE, 5),
    Blocks.WHEAT.defaultState.with(CropBlock.AGE, 6),

    Blocks.POTATOES.defaultState,
    Blocks.POTATOES.defaultState.with(CropBlock.AGE, 1),
    Blocks.POTATOES.defaultState.with(CropBlock.AGE, 2),
    Blocks.POTATOES.defaultState.with(CropBlock.AGE, 3),
    Blocks.POTATOES.defaultState.with(CropBlock.AGE, 4),
    Blocks.POTATOES.defaultState.with(CropBlock.AGE, 5),
    Blocks.POTATOES.defaultState.with(CropBlock.AGE, 6),

    Blocks.CARROTS.defaultState,
    Blocks.CARROTS.defaultState.with(CropBlock.AGE, 1),
    Blocks.CARROTS.defaultState.with(CropBlock.AGE, 2),
    Blocks.CARROTS.defaultState.with(CropBlock.AGE, 3),
    Blocks.CARROTS.defaultState.with(CropBlock.AGE, 4),
    Blocks.CARROTS.defaultState.with(CropBlock.AGE, 5),
    Blocks.CARROTS.defaultState.with(CropBlock.AGE, 6),

    Blocks.BEETROOTS.defaultState,
    Blocks.BEETROOTS.defaultState.with(BeetrootsBlock.AGE, 1),
    Blocks.BEETROOTS.defaultState.with(BeetrootsBlock.AGE, 2),
)
