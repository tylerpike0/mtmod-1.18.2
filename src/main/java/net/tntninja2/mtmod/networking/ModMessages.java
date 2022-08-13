package net.tntninja2.mtmod.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.networking.packet.DashingC2SPacket;

public class ModMessages {
    public static final Identifier DASHING_ID = new Identifier(MTMod.MOD_ID, "dashing");
    public static final Identifier DASH_COOL_DOWN_ID = new Identifier(MTMod.MOD_ID, "dashing");
    public static final Identifier EXAMPLE_ID = new Identifier(MTMod.MOD_ID, "example");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(DASHING_ID, DashingC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }

}
