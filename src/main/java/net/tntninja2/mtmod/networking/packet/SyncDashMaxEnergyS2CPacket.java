package net.tntninja2.mtmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.tntninja2.mtmod.mixinInterface.IMixinEntity;

public class SyncDashMaxEnergyS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        //Everything here ONLY happens on the client
        ClientPlayerEntity player = client.player;
        int value = buf.readInt();

        ((IMixinEntity) player).getMTModData().putInt("dash_max_energy", value);
    }
}
