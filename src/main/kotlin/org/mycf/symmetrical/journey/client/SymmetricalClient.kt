package org.mycf.symmetrical.journey.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
public class SymmetricalClient : ClientModInitializer {

    public override fun onInitializeClient() {
//        BlockRenderLayerMap.INSTANCE.putBlock(SymmetricalJourneyBlocks.COOL_BLOCK, RenderLayer.getCutout())
    }

}