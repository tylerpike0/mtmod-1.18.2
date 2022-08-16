package net.tntninja2.mtmod.item.armorSkillsAndNbt;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.tntninja2.mtmod.damage.ModDamageSource;
import net.tntninja2.mtmod.event.PlayerHitEntityCallback;
import net.tntninja2.mtmod.mixinInterface.IMixinEntity;
import net.tntninja2.mtmod.networking.ModMessages;
import net.tntninja2.mtmod.util.nbtUtil.AttributeUtil;

import java.util.Iterator;
import java.util.UUID;

public class ArmorSkillAbilities {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            PlayerEntity[] playerEntities = server.getPlayerManager().getPlayerList().toArray(new ServerPlayerEntity[0]);
            for (PlayerEntity playerEntity: playerEntities) {
                try {
                    tickModArmorSkills(server, playerEntity);
                } catch (CommandSyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (entity instanceof PlayerEntity) {
                onKillAbilities(world, ((PlayerEntity) entity), killedEntity);
            }
        });

        PlayerHitEntityCallback.EVENT.register((playerEntity, entity) -> {
            onHitAbilities(playerEntity,entity);
            return ActionResult.PASS;
        });
    }


    public static void onHitAbilities(PlayerEntity playerEntity, Entity entity) {
        Iterator<ItemStack> armorItems = playerEntity.getArmorItems().iterator();
        int combo = 0;
        while (armorItems.hasNext()) {
            ItemStack armorStack = armorItems.next();
            NbtCompound armorSkills = armorStack.getOrCreateSubNbt("mtmod:armor_skills");

            combo += armorSkills.getInt("combo");
        }


//        combo
        float comboCharge = ((IMixinEntity) playerEntity).getMTModData().getFloat("combo_charge");
        comboCharge += ((float) combo) / 2;
        ((IMixinEntity) playerEntity).getMTModData().putFloat("combo_charge", comboCharge);


//        shattering
        float shatteringCharge = 0;
        ((IMixinEntity) playerEntity).getMTModData().putFloat("shattering_charge", shatteringCharge);

    }

    private static void onKillAbilities(ServerWorld world, PlayerEntity playerEntity, LivingEntity killedEntity) {
        Iterator<ItemStack> armorItems = playerEntity.getArmorItems().iterator();

        int leeching = 0;

        while (armorItems.hasNext()) {
            //        TODO: add sound effects??


            ItemStack armorStack = armorItems.next();
            NbtCompound armorSkills = armorStack.getOrCreateSubNbt("mtmod:armor_skills");

            leeching += armorSkills.getInt("leeching");
        }
        playerEntity.setHealth(playerEntity.getHealth() + leeching);

    }

    private static void tickModArmorSkills(MinecraftServer server, PlayerEntity playerEntity) throws CommandSyntaxException {
        Iterator<ItemStack> armorItems = playerEntity.getArmorItems().iterator();


        int healthBoost = 0;
        int meleeBoost = 0;
        int peakPerformance = 0;
        int berserker = 0;
        int painForPower = 0;
        int healing = 0;
        int luck = 0;
        int combo = 0;
        int shattering = 0;
        int evasion = 0;

        while (armorItems.hasNext()){

            ItemStack armorStack = armorItems.next();
            NbtCompound armorSkills = armorStack.getOrCreateSubNbt("mtmod:armor_skills");

            healthBoost += armorSkills.getInt("health_boost");
            meleeBoost += armorSkills.getInt("melee_boost");
            peakPerformance += armorSkills.getInt("peak_performance");
            berserker += armorSkills.getInt("berserker");
            painForPower += armorSkills.getInt("pain_for_power");
            healing += armorSkills.getInt("healing");
            luck += armorSkills.getInt("luck");
            combo += armorSkills.getInt("combo");
            shattering += armorSkills.getInt("shattering");
            evasion += armorSkills.getInt("evasion");

        }


//        Health Boost
        healthBoost *= 2;
        if (server.getTicks() % 5 == 0) {
            AttributeUtil.attributeModifierAddOrReplace(playerEntity, EntityAttributes.GENERIC_MAX_HEALTH, UUID.fromString("3a9b56c3-96ae-4ee5-990e-5888c190a5f4"), "health_boost", healthBoost, EntityAttributeModifier.Operation.ADDITION);
            if (playerEntity.getMaxHealth() < playerEntity.getHealth()) {
                playerEntity.setHealth(playerEntity.getMaxHealth());
            }
        }
//        Attack Boost
        if (server.getTicks() % 5 == 0) {
            AttributeUtil.attributeModifierAddOrReplace(playerEntity, EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.fromString("ed1967dd-855f-487a-bc59-f362bc72a8b3"), "melee_boost", meleeBoost, EntityAttributeModifier.Operation.ADDITION);
        }

//        Peak Performance
//        TODO: add sound effects
        if (playerEntity.getHealth() == playerEntity.getMaxHealth()) {
            AttributeUtil.attributeModifierAddOrReplace(playerEntity, EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.fromString("352b7f49-e266-4522-8805-b47ee6b55126"), "peak_performance", peakPerformance, EntityAttributeModifier.Operation.ADDITION);
        } else {
            AttributeUtil.attributeModifierRemove(playerEntity, EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.fromString("352b7f49-e266-4522-8805-b47ee6b55126"));
        }

//        Berserker
//        TODO: add sound effects
        if (playerEntity.getHealth() / playerEntity.getMaxHealth() <= 0.25) {
            berserker *= 2;
        }
        if (playerEntity.getHealth() / playerEntity.getMaxHealth() <= 0.5) {
            AttributeUtil.attributeModifierAddOrReplace(playerEntity, EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.fromString("56b3cf75-fe06-4ce3-af49-d7178cfb2d73"), "berserker", berserker, EntityAttributeModifier.Operation.ADDITION);
        } else {
            AttributeUtil.attributeModifierRemove(playerEntity, EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.fromString("56b3cf75-fe06-4ce3-af49-d7178cfb2d73"));
        }

//        Pain for Power
        painForPower *= 2;
        AttributeUtil.attributeModifierAddOrReplace(playerEntity, EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.fromString("6447430e-a929-4c30-bc15-7107837e11e0"), "pain_for_power", painForPower, EntityAttributeModifier.Operation.ADDITION);

        if (painForPower > 0) {
            if (server.getTicks() % (480 / painForPower) == 0) {
                playerEntity.damage((DamageSource) ModDamageSource.ARMOR_SKILL, 1);
            }
        }

//        Luck
        AttributeUtil.attributeModifierAddOrReplace(playerEntity, EntityAttributes.GENERIC_LUCK, UUID.fromString("5073dc27-4b09-4d05-8a41-72a7d2dfb6b6"), "luck", luck, EntityAttributeModifier.Operation.ADDITION);

//        Healing
        if (healing > 0) {
            if (server.getTicks() % (960 / healing) == 0) {
                playerEntity.heal(1);
            }
        }


//        Combo
        float comboCharge = ((IMixinEntity) playerEntity).getMTModData().getFloat("combo_charge");
        AttributeUtil.attributeModifierAddOrReplace(playerEntity, EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.fromString("8d12e73f-8b19-4451-acbc-0ccca756bdf1"), "combo", (comboCharge * 2 -1 > 0)? comboCharge * 2 -1: 0, EntityAttributeModifier.Operation.ADDITION);
        comboCharge *= 0.99;
        ((IMixinEntity) playerEntity).getMTModData().putFloat("combo_charge", comboCharge);

//        Shattering
        float shatteringCharge = ((IMixinEntity) playerEntity).getMTModData().getFloat("shattering_charge");
        shatteringCharge += 1;
//        TODO: add sound effects
        if (shatteringCharge > 100) {
            if (shatteringCharge > 200) {
                AttributeUtil.attributeModifierAddOrReplace(playerEntity, EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.fromString("be4167dd-855f-487a-bc59-f362bc728fe6"), "shattering", shattering * 5, EntityAttributeModifier.Operation.ADDITION);
            } else {
                AttributeUtil.attributeModifierAddOrReplace(playerEntity, EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.fromString("be4167dd-855f-487a-bc59-f362bc728fe6"), "shattering", shattering * 3, EntityAttributeModifier.Operation.ADDITION);
            }
        } else {
            AttributeUtil.attributeModifierRemove(playerEntity, EntityAttributes.GENERIC_ATTACK_DAMAGE, UUID.fromString("be4167dd-855f-487a-bc59-f362bc728fe6"));
        }


//        Evasion
        int originalDashMaxEnergy = ((IMixinEntity) playerEntity).getMTModData().getInt("dash_max_energy");
        int newDashMaxEnergy = evasion + 3;
        if (originalDashMaxEnergy != newDashMaxEnergy) {
            ((IMixinEntity) playerEntity).getMTModData().putInt("dash_max_energy", newDashMaxEnergy);
            PacketByteBuf packetByteBuf = PacketByteBufs.create();
            packetByteBuf.writeInt(newDashMaxEnergy);
            ServerPlayNetworking.send((ServerPlayerEntity) playerEntity, ModMessages.SYNC_DASH_MAX_ENERGY_ID, packetByteBuf);
        }
    }



}
