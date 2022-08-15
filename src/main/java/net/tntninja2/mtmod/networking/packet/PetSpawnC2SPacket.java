package net.tntninja2.mtmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.tntninja2.mtmod.entity.ModEntities;
import net.tntninja2.mtmod.entity.custom.PetTestEntity;

import javax.swing.text.html.parser.Entity;

public class PetSpawnC2SPacket {
    public static void receive(MinecraftServer minecraftServer, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf packetByteBuf, PacketSender packetSender) {
        //Everything here ONLY happens on the server
        BlockPos blockPos = packetByteBuf.readBlockPos();
        ServerWorld world = player.getWorld();
        ModEntities.PET_TEST.create(world, null, null, player, blockPos, SpawnReason.SPAWN_EGG, true, false);






    }



}
