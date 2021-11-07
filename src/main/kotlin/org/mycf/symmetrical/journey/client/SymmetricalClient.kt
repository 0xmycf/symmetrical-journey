package org.mycf.symmetrical.journey.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import org.mycf.symmetrical.journey.SymmetricalJourney
import org.mycf.symmetrical.journey.blocks.screenhandlers.fletchingtable.FletchingScreen

@Environment(EnvType.CLIENT)
object SymmetricalClient : ClientModInitializer {

    override fun onInitializeClient() {
    }

}