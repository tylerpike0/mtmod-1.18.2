package net.tntninja2.mtmod.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.tntninja2.mtmod.networking.ModMessages;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String Key_Category_MT = "key.mtmod.category.mt";
    public static final String Key_Dash = "key.mtmod.dash";

    public static KeyBinding dashKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(dashKey.wasPressed()) {
//                This happens when custom key is pressed
                if (client.player.isOnGround()) {
                    Vec3d rotationVector = client.player.getRotationVector();
                    client.player.addVelocity(rotationVector.x, 0.1, rotationVector.z);
//                    ClientPlayNetworking.send(ModMessages.EXAMPLE_ID, PacketByteBufs.create());
                }
            }
        });

    }


    public static void register() {
        dashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                Key_Dash,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                Key_Category_MT
        ));

        registerKeyInputs();
    }

}
