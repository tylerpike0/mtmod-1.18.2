package net.tntninja2.mtmod.item.custom.weapons;

import com.eliotlash.mclib.math.functions.classic.Mod;
import joptsimple.internal.Classes;
import net.fabricmc.mappings.model.CommentEntry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tntninja2.mtmod.MTMod;

import java.util.ArrayList;
import java.util.List;

import static net.tntninja2.mtmod.event.KeyInputHandler.getRotationVector;

public class KnockbackHammer1Item extends ModWeapon {

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

    public void lightAttack(ItemStack itemStack, PlayerEntity player) {
            World world = player.getWorld();
            if (!world.isClient()) {
                Box box = new Box(-100, -100, -100, 100, 100, 100);
                LivingEntity nearestEntity = world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, player, player.getX(), player.getY(), player.getZ(), box);
                if (nearestEntity != null) {
                    nearestEntity.kill();
                }
            }
    }

    public void heavyAttack(ItemStack itemStack, PlayerEntity player) {
        MTMod.LOGGER.info("a heavy attack was used");
    }


}
