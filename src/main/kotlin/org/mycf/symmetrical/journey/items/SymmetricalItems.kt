@file:Suppress("SameParameterValue")

package org.mycf.symmetrical.journey.items

import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.mycf.symmetrical.journey.SymmetricalJourney

object SymmetricalItems {
    private var ItemRegistry = linkedMapOf<String, Item>()

    init {
    }

    private fun addItem(name: String, item: Item): Item {
        val correctedName = name.lowercase().trim()
        ItemRegistry[correctedName] = item
        return item
    }

    fun registerItems() {
        ItemRegistry.forEach {
            Registry.register(Registry.ITEM, Identifier(SymmetricalJourney.MOD_ID, it.key), it.value)
        }
    }
}