package org.mycf.symmetrical.journey.mixins;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.LootPoolEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPool.class)
public interface MixinLootPool {

    @Accessor("entries")
    LootPoolEntry[] getEntries();

    @Mutable
    @Accessor("entries")
    void setEntries(LootPoolEntry[] entries);

}
