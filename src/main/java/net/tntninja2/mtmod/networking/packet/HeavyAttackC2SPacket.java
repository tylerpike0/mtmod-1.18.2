package net.tntninja2.mtmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.tntninja2.mtmod.item.custom.weapons.ModWeapon;

public class HeavyAttackC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        //Everything here ONLY happens on the server
        ItemStack itemStack = player.getMainHandStack();
        ModWeapon weapon = itemStack.getItem() instanceof ModWeapon ? (ModWeapon) itemStack.getItem(): null;
        if (weapon != null) {
            weapon.heavyAttack(itemStack, player);
        }


    }


}
