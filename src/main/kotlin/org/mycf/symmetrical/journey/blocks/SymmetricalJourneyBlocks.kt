@file:Suppress("SameParameterValue")

package org.mycf.symmetrical.journey.blocks

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.mycf.symmetrical.journey.SymmetricalJourney

object SymmetricalJourneyBlocks {
    private var BlockItemsRegistry = linkedMapOf<String, Item>()
    private var BlockRegistry = linkedMapOf<String, Block>()

    init {
        addBlock("coolblock", Block(FabricBlockSettings.copy(Blocks.STONE)))
    }

    private fun addBlock(name: String, block: Block) {
        val correctedName = name.lowercase().trim()
        BlockRegistry[correctedName] = block
        BlockItemsRegistry[correctedName + "_item"] = (Item(Item.Settings().maxCount(64).group(ItemGroup.MISC)))
    }

    fun registerBlocks() {
        BlockRegistry.forEach {
            Registry.register(Registry.BLOCK, Identifier(SymmetricalJourney.MOD_ID, it.key), it.value)
        }
        registerBlockItems()
    }

    private fun registerBlockItems() {
        BlockItemsRegistry.forEach {
            Registry.register(Registry.ITEM, Identifier(SymmetricalJourney.MOD_ID, it.key), it.value)
        }
    }
}