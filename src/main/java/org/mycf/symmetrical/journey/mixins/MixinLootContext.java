package org.mycf.symmetrical.journey.mixins;

import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(LootContext.class)
public interface MixinLootContext {

    @Accessor("parameters")
    Map<LootContextParameter<?>, Object> getParams();
}
