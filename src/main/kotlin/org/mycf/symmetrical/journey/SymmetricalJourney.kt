package org.mycf.symmetrical.journey

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier
import org.mycf.symmetrical.journey.blocks.screenhandlers.fletchingtable.FletchingBlockScreenHandler
import org.mycf.symmetrical.journey.blocks.screenhandlers.fletchingtable.FletchingScreen
import org.mycf.symmetrical.journey.events.EventRegistry


@Suppress("UNUSED")
object SymmetricalJourney : ModInitializer {
    const val MOD_ID = "symmetrical-journey"
    const val symmjour_brokenLine = "symmjour_brokenLine"


    lateinit var FletchingBlockScreenHandlerType: ScreenHandlerType<FletchingBlockScreenHandler>

    override fun onInitialize() {
        EventRegistry
        FletchingBlockScreenHandlerType =
            ScreenHandlerRegistry.registerSimple(Identifier(MOD_ID, "screenhandler_fletchingtable")) { i, pi ->
                FletchingBlockScreenHandler(pi, i)
            }
        ScreenRegistry.register(FletchingBlockScreenHandlerType) { sh, pi, title -> FletchingScreen(sh, pi, title) }
    }
}
