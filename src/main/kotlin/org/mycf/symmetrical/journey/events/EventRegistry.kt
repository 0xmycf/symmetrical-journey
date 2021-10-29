package org.mycf.symmetrical.journey.events

import net.minecraft.entity.passive.CowEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.ActionResult
import org.mycf.symmetrical.journey.events.impl.DropLootCallback

object EventRegistry {

    init {
        DropLootCallback.EVENT.register { source, byPlayer, entity ->
            if (!entity.world.isClient) {
                if (entity is CowEntity) {
                    entity.dropStack(ItemStack(Items.BEEF, 1))
                    return@register ActionResult.FAIL
                }
                return@register ActionResult.PASS
            }
            return@register ActionResult.FAIL
        }
    }

}