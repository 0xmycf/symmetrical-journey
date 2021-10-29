package org.mycf.symmetrical.journey.events.impl;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

public interface DropLootCallback {

    Event<DropLootCallback> EVENT = EventFactory.createArrayBacked(DropLootCallback.class,
            (listeners) -> (source, causedByPlayer, entity) -> {
                for (DropLootCallback listener : listeners) {
                    ActionResult result = listener.onDrop(source, causedByPlayer, entity);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult onDrop(DamageSource source, boolean causedByPlayer, LivingEntity entity);
}
