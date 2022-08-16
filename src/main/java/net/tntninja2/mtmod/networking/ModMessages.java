package net.tntninja2.mtmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.networking.packet.DashingC2SPacket;
import net.tntninja2.mtmod.networking.packet.PetSpawnC2SPacket;
import net.tntninja2.mtmod.networking.packet.SyncDashMaxEnergyS2CPacket;
import net.tntninja2.mtmod.networking.packet.SyncInvulnerabilityTicksC2SPacket;

public class ModMessages {
    public static final Identifier DASH_ID = new Identifier(MTMod.MOD_ID, "dashing");
    public static final Identifier DASH_COOL_DOWN_ID = new Identifier(MTMod.MOD_ID, "dashing");
    public static final Identifier PET_SPAWN_ID = new Identifier(MTMod.MOD_ID, "pet_spawn");
    public static final Identifier EXAMPLE_ID = new Identifier(MTMod.MOD_ID, "example");
    public static final Identifier INVULNERABILITY_TICKS_ID = new Identifier(MTMod.MOD_ID, "invulnerability_ticks");

    public static final Identifier SYNC_DASH_MAX_ENERGY_ID = new Identifier(MTMod.MOD_ID, "sync_player_data_int");


    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(DASH_ID, DashingC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PET_SPAWN_ID, PetSpawnC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(INVULNERABILITY_TICKS_ID, SyncInvulnerabilityTicksC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SYNC_DASH_MAX_ENERGY_ID, SyncDashMaxEnergyS2CPacket::receive);

    }



}
