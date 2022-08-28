package net.tntninja2.mtmod.networking.packet.animation;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.network.PacketByteBuf;
import net.tntninja2.mtmod.entity.custom.TuffJumperEntity;
import net.tntninja2.mtmod.mixinInterface.IMixinEntity;
import software.bernie.geckolib3.core.IAnimatable;

public class TuffJumperJumpS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        //Everything here ONLY happens on the client
        ClientPlayerEntity player = client.player;
        int mobId = buf.readInt();
        if (client.world != null) {
            TuffJumperEntity tuffJumper = (TuffJumperEntity) client.world.getEntityById(mobId);
            if (tuffJumper != null) {
                tuffJumper.startJump();
            }
        }
    }
}
