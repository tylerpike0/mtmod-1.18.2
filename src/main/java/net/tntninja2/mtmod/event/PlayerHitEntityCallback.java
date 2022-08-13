package net.tntninja2.mtmod.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

/*
*Callback for when an entity kills another
* Called at HEAD
* */

public interface PlayerHitEntityCallback {
    Event<PlayerHitEntityCallback> EVENT = EventFactory.createArrayBacked(PlayerHitEntityCallback.class,
            (listeners) -> (playerEntity, entity) -> {
                for (PlayerHitEntityCallback listener : listeners) {
                    ActionResult result = listener.hurtEntity(playerEntity, entity);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult hurtEntity(PlayerEntity playerEntity, Entity entity);
}
