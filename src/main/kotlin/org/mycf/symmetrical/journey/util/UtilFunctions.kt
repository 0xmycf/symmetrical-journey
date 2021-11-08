package org.mycf.symmetrical.journey.util

import net.minecraft.block.Blocks
import net.minecraft.entity.passive.IronGolemEntity
import net.minecraft.entity.passive.ParrotEntity
import net.minecraft.entity.passive.SnowGolemEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box

private val Int.d: Double
    get() {
        return this.toDouble()
    }

object UtilFunctions {
    fun noGolemNearby(parrot: ParrotEntity, blockPos: BlockPos): Boolean {
        val box = Box(blockPos).expand(10.d).stretch(0.0, parrot.world.height.d, 0.0)
        val set = parrot.world.getNonSpectatingEntities(PlayerEntity::class.java, box)
        val set1 = parrot.world.getNonSpectatingEntities(IronGolemEntity::class.java, box)
        val set2 = parrot.world.getNonSpectatingEntities(SnowGolemEntity::class.java, box)
        return set1.isEmpty() && set2.isEmpty() && set.none {
            it.inventory.armor[3].isOf(Blocks.CARVED_PUMPKIN.asItem())
        }
    }
}