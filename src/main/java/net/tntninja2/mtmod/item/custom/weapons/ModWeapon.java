package net.tntninja2.mtmod.item.custom.weapons;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;

public class ModWeapon extends AxeItem {


    public ModWeapon(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public void changeOomph(int change, ItemStack itemStack) {
        int oomph = itemStack.getOrCreateSubNbt("mtmod:weapon_attacks").getInt("oomph");
        oomph += change;
        oomph = Math.min(oomph, 100);
        itemStack.getOrCreateSubNbt("mtmod:weapon_attacks").putInt("oomph", oomph);

    }

    public boolean checkOomph(int requiredOomph, ItemStack itemStack) {
        int oomph = itemStack.getOrCreateSubNbt("mtmod:weapon_attacks").getInt("oomph");
        if (oomph >=  requiredOomph) {
            return true;
        } else {
            return false;
        }
    }

    public void lightAttack(ItemStack itemStack, PlayerEntity player) {

    }

    public void heavyAttack(ItemStack itemStack, PlayerEntity player) {

    }
}
