package net.tntninja2.mtmod.item.custom.weapons;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;

public class ModWeapon extends AxeItem {


    protected ModWeapon(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public void lightAttack(ItemStack itemStack, PlayerEntity player) {

    }

    public void heavyAttack(ItemStack itemStack, PlayerEntity player) {

    }
}
