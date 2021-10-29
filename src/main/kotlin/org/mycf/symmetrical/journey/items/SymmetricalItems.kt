@file:Suppress("SameParameterValue")

package org.mycf.symmetrical.journey.items

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.mycf.symmetrical.journey.SymmetricalJourney

object SymmetricalItems {
    private var ItemRegistry = linkedMapOf<String, Item>()

    init {
//        addItem("coolitem", Item(FabricItemSettings().maxCount(64).group(ItemGroup.MISC)))
    }

    private fun addItem(name: String, item: Item): Item {
        val correctedName = name.lowercase().trim()
        ItemRegistry[correctedName] = item
        return item
    }

    public fun registerItems() {
        ItemRegistry.forEach {
            Registry.register(Registry.ITEM, Identifier(SymmetricalJourney.MOD_ID, it.key), it.value)
        }
    }
}