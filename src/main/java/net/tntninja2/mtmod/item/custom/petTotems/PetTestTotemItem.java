package net.tntninja2.mtmod.item.custom.petTotems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.ModEntities;
import net.tntninja2.mtmod.entity.custom.PetEntity;
import net.tntninja2.mtmod.entity.custom.PetTestEntity;
import net.tntninja2.mtmod.item.custom.PetTotemItem;

import javax.swing.*;

public class PetTestTotemItem extends PetTotemItem {
    public PetTestTotemItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {

        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public void offhandTick(World world, PlayerEntity playerEntity, ItemStack itemStack) {
        if (itemStack.getOrCreateNbt().getBoolean("has_entity")) {

        } else {
            if (!world.isClient()) {
                PetTestEntity petTestEntity = ModEntities.PET_TEST.spawn(((ServerWorld) world), null, Text.of("PetTest"),
                        playerEntity, playerEntity.getBlockPos(), SpawnReason.TRIGGERED, true, true);
                itemStack.getOrCreateNbt().putBoolean("has_entity", true);

                petTestEntity.setOwner(playerEntity);
            }

        }

    }

    @Override
    public void nonOffhandTick(World world, PlayerEntity playerEntity, ItemStack itemStack) {
        if (!world.isClient()) {
            itemStack.getOrCreateNbt().putBoolean("has_entity", false);
        }
    }
}
