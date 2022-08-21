package net.tntninja2.mtmod.item.custom;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.profiling.jfr.event.ServerTickTimeEvent;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.item.custom.petTotems.PetTestTotemItem;

public class RegisterPetTotemTick {
    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {


            PlayerEntity[] playerEntities = world.getPlayers().toArray(new ServerPlayerEntity[0]);
            for (PlayerEntity playerEntity: playerEntities) {

                ItemStack itemStack = playerEntity.getOffHandStack();
                Item item = itemStack.getItem();

                if (item instanceof PetTotemItem) {
                    PetTotemItem petTotemItem = ((PetTotemItem) item);
                    petTotemItem.offhandTick(world, playerEntity, itemStack);
                }
            }
        });
    }
}
