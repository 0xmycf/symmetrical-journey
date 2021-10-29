package org.mycf.symmetrical.journey

import net.fabricmc.api.ModInitializer
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.CropBlock
import org.mycf.symmetrical.journey.blocks.SymmetricalBlocks
import org.mycf.symmetrical.journey.events.EventRegistry
import org.mycf.symmetrical.journey.items.SymmetricalItems


@Suppress("UNUSED")
object SymmetricalJourney : ModInitializer {
    const val MOD_ID = "symmetrical-journey"

    override fun onInitialize() {
        EventRegistry
    }
}
