package org.mycf.symmetrical.journey

import net.fabricmc.api.ModInitializer
import org.mycf.symmetrical.journey.events.EventRegistry


@Suppress("UNUSED")
object SymmetricalJourney : ModInitializer {
    const val MOD_ID = "symmetrical-journey"

    override fun onInitialize() {
        EventRegistry
    }
}
