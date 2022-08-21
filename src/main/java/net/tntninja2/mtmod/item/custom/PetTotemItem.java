package net.tntninja2.mtmod.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.PetEntity;

public abstract class PetTotemItem extends Item {

    PetEntity petEntity;

    public PetTotemItem(Settings settings) {
        super(settings);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity) {
            if (((LivingEntity) entity).getOffHandStack().equals(stack)) {
                this.offhandTick(world, ((PlayerEntity) entity), stack);
            } else {
                this.nonOffhandTick(world, ((PlayerEntity) entity), stack);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public abstract void offhandTick(World world, PlayerEntity playerEntity, ItemStack itemStack);

    public abstract void nonOffhandTick(World world, PlayerEntity playerEntity, ItemStack itemStack);


}
