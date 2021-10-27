package org.mycf.symmetrical.journey

import net.fabricmc.api.ModInitializer
import org.mycf.symmetrical.journey.blocks.SymmetricalJourneyBlocks
import org.mycf.symmetrical.journey.items.SymmetricalJourneyItems


@Suppress("UNUSED")
object SymmetricalJourney: ModInitializer {
    const val MOD_ID = "symmetrical-journey"



    override fun onInitialize() {
        SymmetricalJourneyBlocks.registerBlocks()
        SymmetricalJourneyItems.registerItems()
    }
}
