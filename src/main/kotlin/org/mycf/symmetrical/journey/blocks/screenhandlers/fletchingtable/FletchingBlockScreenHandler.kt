// constructor(screenHandlerType: ScreenHandlerType<*>?, i:Int) : this(screenHandlerType, i, ScreenHandlerContext.EMPTY)
package org.mycf.symmetrical.journey.blocks.screenhandlers.fletchingtable

import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.slot.Slot
import org.mycf.symmetrical.journey.SymmetricalJourney

class FletchingBlockScreenHandler(
    playerInventory: PlayerInventory,
    i: Int,
    screenHandlerContext: ScreenHandlerContext?,
) : ScreenHandler(SymmetricalJourney.FletchingBlockScreenHandlerType, i) {

    constructor(playerInventory: PlayerInventory, i: Int) : this(playerInventory, i, ScreenHandlerContext.EMPTY)


    private var input: Inventory = object : SimpleInventory(2) {
        override fun markDirty() {
            super.markDirty()
            println("Do something fun later 1")
        }
    }
    private var output: Inventory = object : SimpleInventory(2) {
        override fun markDirty() {
            super.markDirty()
            println("Do something fun later 2")
        }
    }
    private var outputSlot: Slot = object : Slot(this.output, 0, 143, 58) {
        override fun canInsert(stack: ItemStack?): Boolean {
            return false
        }
    }
    private var rodSlot: Slot = object : Slot(this.output, 0, 153, 68) {
        override fun canInsert(stack: ItemStack?): Boolean {
            return stack?.isOf(Items.FISHING_ROD) ?: false
        }
    }
    private var stringSlot: Slot = object : Slot(this.output, 0, 133, 48) {
        override fun canInsert(stack: ItemStack?): Boolean {
            return stack?.isOf(Items.STRING) ?: false
        }
    }

    var context: ScreenHandlerContext?


    // Kotlin
    init {

        this.context = screenHandlerContext

        /**
         * The player inventory
         */
        for (j in 0..2) {
            for (k in 0..8) {
                this.addSlot(Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        for (j in 0..8) {
            this.addSlot(Slot(playerInventory, j, 8 + j * 18, 142));
        }

        this.addSlot(this.outputSlot)
        this.addSlot(this.rodSlot)
        this.addSlot(this.stringSlot)
    }

    override fun canUse(player: PlayerEntity?): Boolean = canUse(this.context, player, Blocks.FLETCHING_TABLE)


}


//class FletchingBlockScreenHandler: AnvilScreenHandler {
//
//    constructor(i: Int,playerInventory: PlayerInventory , screenHandlerContext: ScreenHandlerContext ) : super(i,playerInventory , screenHandlerContext)
//
//
//
//}