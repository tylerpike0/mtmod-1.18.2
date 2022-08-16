package net.tntninja2.mtmod.event;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface LivingEntityDamageCancelCallback {
    Event<LivingEntityDamageCancelCallback> EVENT = EventFactory.createArrayBacked(LivingEntityDamageCancelCallback.class,
            (listeners) -> (livingEntity, damageSource, amount) -> {
                for (LivingEntityDamageCancelCallback listener : listeners) {
                    ActionResult result = listener.damageCancel(livingEntity, damageSource, amount);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult damageCancel(LivingEntity livingEntity, DamageSource damageSource, float amount) throws CommandSyntaxException;
}
