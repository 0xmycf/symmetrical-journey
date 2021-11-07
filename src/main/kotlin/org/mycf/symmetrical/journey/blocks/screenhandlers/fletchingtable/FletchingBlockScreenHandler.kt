// constructor(screenHandlerType: ScreenHandlerType<*>?, i:Int) : this(screenHandlerType, i, ScreenHandlerContext.EMPTY)
package org.mycf.symmetrical.journey.blocks.screenhandlers.fletchingtable

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.screen.ForgingScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import org.mycf.symmetrical.journey.SymmetricalJourney

class FletchingBlockScreenHandler(
    playerInventory: PlayerInventory,
    i: Int,
    screenHandlerContext: ScreenHandlerContext?,
) : ForgingScreenHandler(SymmetricalJourney.FletchingBlockScreenHandlerType, i, playerInventory, screenHandlerContext) {

    constructor(playerInventory: PlayerInventory, i: Int) : this(playerInventory, i, ScreenHandlerContext.EMPTY)

    override fun canUse(player: PlayerEntity?): Boolean = canUse(this.context, player, Blocks.FLETCHING_TABLE)

    override fun canUse(state: BlockState?): Boolean {
        return state?.isOf(Blocks.FLETCHING_TABLE) ?: false
    }

    override fun canTakeOutput(player: PlayerEntity?, present: Boolean): Boolean {
        return present && this.input.getStack(0).getOrCreateNbt().getBoolean(SymmetricalJourney.symmjour_brokenLine) && !this.input.getStack(1).isEmpty
    }

    override fun onTakeOutput(player: PlayerEntity?, stack: ItemStack?) {
        this.input.setStack(0, ItemStack.EMPTY)
        if (!this.input.getStack(1).isEmpty){
            this.input.getStack(1).decrement(1)
        }
        player?.world?.playSound(player, player.x, player.y, player.z, SoundEvents.ENTITY_VILLAGER_WORK_FLETCHER, SoundCategory.NEUTRAL, 0.5F, 0.4F / (player.world.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    override fun updateResult() {
        val rod = this.input.getStack(0)
        if (!rod.getOrCreateNbt().contains(SymmetricalJourney.symmjour_brokenLine)) {
            rod.getOrCreateNbt().putBoolean(SymmetricalJourney.symmjour_brokenLine, false)
        }
        val bl = rod.getOrCreateNbt().getBoolean(SymmetricalJourney.symmjour_brokenLine)

        if (!rod.isEmpty && rod.isOf(Items.FISHING_ROD)) {
            if (!this.input.getStack(1).isEmpty && this.input.getStack(1).isOf(Items.STRING) && bl) {
                val newRod = this.input.getStack(0).copy().apply {
                    this.getOrCreateNbt().putBoolean(SymmetricalJourney.symmjour_brokenLine, false)
                }
                this.output.setStack(0, newRod)

            } else {
                this.output.setStack(0, this.input.getStack(0).copy())
            }
        } else {
            this.output.setStack(0, ItemStack.EMPTY)
        }
    }
}