package net.tntninja2.mtmod.networking.packet.animation;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.tntninja2.mtmod.entity.custom.SlateJumperEntity;

public class SlateJumperCrouchS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        //Everything here ONLY happens on the client
        ClientPlayerEntity player = client.player;
        int mobId = buf.readInt();
        if (client.world != null) {
            SlateJumperEntity slateJumper = (SlateJumperEntity) client.world.getEntityById(mobId);
            if (slateJumper != null) {
                slateJumper.startCrouch();
            }
        }
    }
}
