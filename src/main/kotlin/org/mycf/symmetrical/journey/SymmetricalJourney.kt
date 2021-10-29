package org.mycf.symmetrical.journey

import net.fabricmc.api.ModInitializer
import org.mycf.symmetrical.journey.blocks.SymmetricalBlocks
import org.mycf.symmetrical.journey.events.EventRegistry
import org.mycf.symmetrical.journey.items.SymmetricalItems


@Suppress("UNUSED")
object SymmetricalJourney : ModInitializer {
    const val MOD_ID = "symmetrical-journey"


    override fun onInitialize() {
        SymmetricalBlocks.registerBlocks()
        SymmetricalItems.registerItems()
        EventRegistry
    }
}
