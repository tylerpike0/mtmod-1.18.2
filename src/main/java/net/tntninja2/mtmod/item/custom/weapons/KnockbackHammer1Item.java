package net.tntninja2.mtmod.item.custom.weapons;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.Vec3d;

import static net.tntninja2.mtmod.event.KeyInputHandler.getRotationVector;

public class KnockbackHammer1Item extends AxeItem {

    public KnockbackHammer1Item(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Vec3d rotationVector;
        rotationVector = getRotationVector(attacker.getPitch(), attacker.getYaw());
        target.addVelocity(rotationVector.x, 0, rotationVector.z);
        super.postHit(stack, target, attacker);
        return false;

    }



}
