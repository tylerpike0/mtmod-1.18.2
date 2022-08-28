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
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.item.custom.armor.ModArmorItem;
import net.tntninja2.mtmod.item.custom.weapons.ModWeapon;
import net.tntninja2.mtmod.mixinInterface.IMixinLivingEntity;
import net.tntninja2.mtmod.networking.ModMessages;
import net.tntninja2.mtmod.mixinInterface.IMixinEntity;
import org.lwjgl.glfw.GLFW;
import virtuoel.pehkui.api.ScaleModifier;
import virtuoel.pehkui.api.ScaleModifiers;

import java.util.Iterator;

public class KeyInputHandler {
    public static final String Key_Category_MT = "key.mtmod.category.mt";

    public static final String Key_Dash = "key.mtmod.dash";
    public static final String Key_Light_Attack = "key.mtmod.light_attack";
    public static final String Key_Heavy_Attack = "key.mtmod.heavy_attack";


    public static KeyBinding dashKey;
    public static KeyBinding lightAttackKey;
    public static KeyBinding heavyAttackKey;


    public static Vec3d getRotationVector(float pitch, float yaw) {
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
            if (lightAttackKey.wasPressed()) {
                ItemStack itemStack = client.player.getMainHandStack();
                Item item = itemStack.getItem();
                if (item instanceof ModWeapon) {
                    ((ModWeapon) item).lightAttack(itemStack, client.player);
                }
                ClientPlayNetworking.send(ModMessages.LIGHT_ATTACK_ID, PacketByteBufs.create());


            }
            if (heavyAttackKey.wasPressed()) {
                ItemStack itemStack = client.player.getMainHandStack();
                Item item = itemStack.getItem();

                if (item instanceof ModWeapon) {
                    ((ModWeapon) item).heavyAttack(itemStack, client.player);
                }
                ClientPlayNetworking.send(ModMessages.HEAVY_ATTACK_ID, PacketByteBufs.create());
            }

                    if (dashKey.wasPressed()) {
//                This happens when custom key is pressed
                        int dashEnergy = ((IMixinEntity) client.player).getMTModData().getInt("dash_energy");
                        if (dashEnergy >= 1) {
                            dashEnergy--;
                            dash(client);

                            ((IMixinEntity) client.player).getMTModData().putInt("dash_energy", dashEnergy);
//                            ModMessages.syncPlayerDataInt("dash_energy", dashEnergy, client.world, null);

                            int invulnerabilityTicks = 10;
                            ((IMixinEntity) client.player).getMTModData().putInt("invulnerability_ticks", invulnerabilityTicks);
                            PacketByteBuf packetByteBuf = PacketByteBufs.create();
                            packetByteBuf.writeInt(invulnerabilityTicks);
                            ClientPlayNetworking.send(ModMessages.INVULNERABILITY_TICKS_ID, packetByteBuf);

                        } else {
                            client.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_DIDGERIDOO, 10, 1);
                        }
                    }
                }
        );

    }


    public static void dash(MinecraftClient client) {


        if (client.player.isSubmergedInWater()) {
            Vec3d rotationVector;
            rotationVector = getRotationVector(client.player.getPitch(), client.player.getYaw());

            ClientPlayNetworking.send(ModMessages.DASH_ID, PacketByteBufs.create());

            dashMovementUnderWater(client, rotationVector);
        } else if (client.player.isOnGround()) {

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

            ClientPlayNetworking.send(ModMessages.DASH_ID, PacketByteBufs.create());

            dashMovementOnLand(client, rotationVector);
        }

    }

    private static void dashMovementOnLand(MinecraftClient client, Vec3d rotationVector) {
        if (client.player.input.pressingForward) {
            client.player.addVelocity(rotationVector.x, 0.1, rotationVector.z);
        }
        if (client.player.input.pressingRight) {
            Vec3d rightRotationVector = rotationVector.rotateY((float) Math.toRadians(-90));
            client.player.addVelocity(rightRotationVector.x, 0.1, rightRotationVector.z);
        }
        if (client.player.input.pressingLeft) {
            Vec3d leftRotationVector = rotationVector.rotateY((float) Math.toRadians(90));
            client.player.addVelocity(leftRotationVector.x, 0.1, leftRotationVector.z);
        }
        if (client.player.input.pressingBack) {
            Vec3d backRotationVector = rotationVector.rotateY((float) Math.toRadians(180));
            client.player.addVelocity(backRotationVector.x, 0.1, backRotationVector.z);
        }
    }

    private static void dashMovementUnderWater(MinecraftClient client, Vec3d rotationVector) {
        if (client.player.input.pressingForward) {
            client.player.addVelocity(rotationVector.x, rotationVector.y, rotationVector.z);
        }
        if (client.player.input.pressingRight) {
            Vec3d rightRotationVector = rotationVector.rotateY((float) Math.toRadians(-90));
            client.player.addVelocity(rightRotationVector.x, 0, rightRotationVector.z);
        }
        if (client.player.input.pressingLeft) {
            Vec3d leftRotationVector = rotationVector.rotateY((float) Math.toRadians(90));
            client.player.addVelocity(leftRotationVector.x, 0, leftRotationVector.z);
        }
        if (client.player.input.pressingBack) {
            Vec3d backRotationVector = rotationVector.rotateY((float) Math.toRadians(180));
            client.player.addVelocity(backRotationVector.x, -backRotationVector.y, backRotationVector.z);
        }
    }


    public static void register() {

        dashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                Key_Dash,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                Key_Category_MT
        ));

        lightAttackKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                Key_Light_Attack,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                Key_Category_MT
        ));

        heavyAttackKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                Key_Heavy_Attack,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                Key_Category_MT
        ));
        registerKeyInputs();
    }


}
