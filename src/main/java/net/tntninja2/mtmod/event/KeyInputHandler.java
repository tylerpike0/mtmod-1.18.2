package net.tntninja2.mtmod.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.tntninja2.mtmod.item.custom.armor.ModArmorItem;
import net.tntninja2.mtmod.networking.ModMessages;
import net.tntninja2.mtmod.mixinInterface.IMixinEntity;
import org.lwjgl.glfw.GLFW;

import java.util.Iterator;

public class KeyInputHandler {
    public static final String Key_Category_MT = "key.mtmod.category.mt";

    public static final String Key_Dash = "key.mtmod.dash";


    public static KeyBinding dashKey;


    public static final Vec3d getRotationVector(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = MathHelper.cos(g);
        float i = MathHelper.sin(g);
        float j = MathHelper.cos(f);
        float k = MathHelper.sin(f);
        return new Vec3d((double) (i * j), (double) (-k), (double) (h * j));
    }

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
                    if (dashKey.wasPressed()) {
//                This happens when custom key is pressed
                        Item item = (client.player.getStackInHand(Hand.MAIN_HAND).getItem());

                        ModArmorItem modArmorItem = item instanceof ModArmorItem ? ((ModArmorItem) item) : null;
                        if (modArmorItem != null) {
                        }


                        int dashEnergy = ((IMixinEntity) client.player).getMTModData().getInt("dash_energy");
                        if (dashEnergy >= 1) {
                            dashEnergy--;
                            dash(client);

                            ((IMixinEntity) client.player).getMTModData().putInt("dash_energy", dashEnergy);
                            client.player.sendMessage(Text.of(String.valueOf(dashEnergy)), false);

                        } else {
                            client.player.sendMessage(Text.of("You have no dodges!!!"), true);
                        }
                    }
                }
        );

    }


    public static void dash(MinecraftClient client) {

        if (client.player.isOnGround()) {

            Iterator<ItemStack> armorItems = client.player.getArmorItems().iterator();
            int evasion = 0;
            while (armorItems.hasNext()) {
                ItemStack armorStack = armorItems.next();
                NbtCompound armorSkills = armorStack.getOrCreateSubNbt("mtmod:armor_skills");
                evasion += armorSkills.getInt("evasion");
            }


            Vec3d rotationVector;
            rotationVector = getRotationVector(0, client.player.getYaw());
            rotationVector.multiply(1 + evasion / 6);

            ClientPlayNetworking.send(ModMessages.DASHING_ID, PacketByteBufs.create());

            if (client.player.input.pressingForward) {
                client.player.addVelocity(rotationVector.x, 0.1, rotationVector.z);
            }
            if (client.player.input.pressingRight) {
//                TODO: make this and next 2 directions move player in proper direction using rotationVector.rotate
                Vec3d rightRotationVector = rotationVector.rotateY((float) Math.toRadians(-90));
                client.player.addVelocity(rightRotationVector.x, 0.1, rightRotationVector.z);
            }
            if (client.player.input.pressingLeft) {
                Vec3d leftRotationVector = rotationVector.rotateY((float) Math.toRadians(90));
                client.player.addVelocity(leftRotationVector.x, 0.1, rotationVector.z);
            }
            if (client.player.input.pressingBack) {
                Vec3d backRotationVector = rotationVector.rotateY((float) Math.toRadians(180));
                client.player.addVelocity(backRotationVector.x, 0.1, rotationVector.z);
            }
        }

    }



    public static void register() {

        dashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                Key_Dash,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                Key_Category_MT
        ));
        registerKeyInputs();
    }


}
