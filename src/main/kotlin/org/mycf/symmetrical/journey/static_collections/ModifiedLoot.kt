package org.mycf.symmetrical.journey.static_collections

import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

class ModifiedLoot {
    companion object {
        val CONFIGURED_MOB_LOOT: HashMap<EntityType<*>, Set<Item>> = hashMapOf(
            EntityType.COW to setOf(Items.BEEF, Items.COOKED_BEEF),
            EntityType.SHEEP to setOf(Items.MUTTON, Items.COOKED_MUTTON),
            EntityType.PIG to setOf(Items.PORKCHOP, Items.COOKED_PORKCHOP),
            EntityType.CHICKEN to setOf(Items.CHICKEN, Items.COOKED_CHICKEN),
            EntityType.RABBIT to setOf(Items.RABBIT, Items.COOKED_RABBIT),
        )

        fun isItemIncluded(stack: ItemStack): Boolean = CONFIGURED_MOB_LOOT.values.flatten().contains(stack.item)
        // wtf java
        // ModifiedLoot.Companion.getCONFIGURED_MOB_LOOT().values().stream().flatMap(Set::stream).collect(Collectors.toList()).contains(stack.getItem())
    }
}