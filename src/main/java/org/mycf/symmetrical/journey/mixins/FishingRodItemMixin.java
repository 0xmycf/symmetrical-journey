package org.mycf.symmetrical.journey.mixins;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.mycf.symmetrical.journey.SymmetricalJourney;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(FishingRodItem.class)
public class FishingRodItemMixin extends Item {
    public FishingRodItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "Lnet/minecraft/item/FishingRodItem;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;use(Lnet/minecraft/item/ItemStack;)I", shift = At.Shift.AFTER))
    private void mightBreakLine(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (world.getRandom().nextFloat() > 0.97) {
            world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            user.getStackInHand(hand).getOrCreateNbt().putBoolean(SymmetricalJourney.symmjour_brokenLine, true);
        }
    }

    @Inject(method = "Lnet/minecraft/item/FishingRodItem;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;",
            at = @At("HEAD"),
            cancellable = true)
    private void hasBrokenLine(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (!user.getStackInHand(hand).getOrCreateNbt().contains(SymmetricalJourney.symmjour_brokenLine)) {
            user.getStackInHand(hand).getOrCreateNbt().putBoolean(SymmetricalJourney.symmjour_brokenLine, false);
        }
        if (user.getStackInHand(hand).getOrCreateNbt().getBoolean(SymmetricalJourney.symmjour_brokenLine)) {
            cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!stack.getOrCreateNbt().contains(SymmetricalJourney.symmjour_brokenLine)) {
            stack.getOrCreateNbt().putBoolean(SymmetricalJourney.symmjour_brokenLine, false);
        }

        if (stack.getOrCreateNbt().getBoolean(SymmetricalJourney.symmjour_brokenLine)) {
            tooltip.add(new TranslatableText("symmetricaljourney.rod.broken"));
        } else {
            tooltip.add(new TranslatableText("symmetricaljourney.rod.fine"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
