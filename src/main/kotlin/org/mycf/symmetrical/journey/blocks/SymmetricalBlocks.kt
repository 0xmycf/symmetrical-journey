@file:Suppress("SameParameterValue")

package org.mycf.symmetrical.journey.blocks

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.PointedDripstoneBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.mycf.symmetrical.journey.SymmetricalJourney

object SymmetricalBlocks {
    private var BlockItemsRegistry = linkedMapOf<String, Item>()
    private var BlockRegistry = linkedMapOf<String, Block>()

//    val COOL_BLOCK: Block

    init {
//        COOL_BLOCK = addBlock("coolblock", PointedDripstoneBlock(FabricBlockSettings.of(Material.STONE).ticksRandomly().dynamicBounds()))
    }

    private fun addBlock(name: String, block: Block): Block {
        val correctedName = name.lowercase().trim()
        BlockRegistry[correctedName] = block
        BlockItemsRegistry[correctedName + "_item"] = (BlockItem(block, Item.Settings().maxCount(64).group(ItemGroup.MISC)))
        return block
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