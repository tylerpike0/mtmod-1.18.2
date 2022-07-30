package net.tntninja2.mtmod.networking.packet;

import com.eliotlash.mclib.math.functions.classic.Mod;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.tntninja2.mtmod.entity.ModEntities;

public class ExampleC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        //Everything here ONLY happens on the server
        ModEntities.MYTHRIL_GOLEM.spawn(((ServerWorld) player.world), null, null, player, player.getBlockPos(),
                SpawnReason.TRIGGERED, true, false);
    }

}
