package net.tntninja2.mtmod;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.tntninja2.mtmod.damage.ModDamageSource;
import net.tntninja2.mtmod.mixinInterface.IMixinEntity;
import net.tntninja2.mtmod.util.nbtUtil.AttributeUtil;

import java.util.Iterator;
import java.util.UUID;


public class MTModTickEvents {

    
    public static void registerServerEndTickEvents() {

    }



    public static void registerClientEndTickEvent() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickDashRegen(client);
        });
    }

    private static void tickDashRegen(MinecraftClient client) {
        if (!(client.player == null)) {

            int dashRegenTimer = ((IMixinEntity) client.player).getMTModData().getInt("dash_regen_timer");
            int dashEnergy = ((IMixinEntity) client.player).getMTModData().getInt("dash_energy");

            if (dashEnergy <= 2) {
                if (dashRegenTimer >= 60) {

                    dashRegenTimer = 0;
                    dashEnergy = 3;

                } else {
                    dashRegenTimer++;
                }
            }

            ((IMixinEntity) client.player).getMTModData().putInt("dash_energy", dashEnergy);
            ((IMixinEntity) client.player).getMTModData().putInt("dash_regen_timer", dashRegenTimer);
        }

    }



}
