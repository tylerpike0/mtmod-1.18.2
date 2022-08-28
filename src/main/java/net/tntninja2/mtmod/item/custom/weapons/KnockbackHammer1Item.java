package net.tntninja2.mtmod.item.custom.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tntninja2.mtmod.MTMod;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

import static net.tntninja2.mtmod.event.KeyInputHandler.getRotationVector;

public class KnockbackHammer1Item extends ModWeapon {

    public KnockbackHammer1Item(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        Vec3d rotationVector;
        rotationVector = getRotationVector(attacker.getPitch(), attacker.getYaw());
        target.addVelocity(rotationVector.x, 0, rotationVector.z);
        changeOomph(5, itemStack);
        super.postHit(itemStack, target, attacker);
        return false;

    }

    public void lightAttack(ItemStack itemStack, PlayerEntity player) {
        World world = player.getWorld();
        Vec3d playerPos = player.getPos();
        Vec3d rotationVector = player.getRotationVector();
        Vec3d hitBoxPos = playerPos.add(rotationVector.multiply(2, 0, 2));
        Box hitBox = new Box(-2 + hitBoxPos.x, hitBoxPos.y - 1, -2 + hitBoxPos.z,
                2 + hitBoxPos.x, 2 + hitBoxPos.y, 2 + hitBoxPos.z);

        Vec3d knockBackVector = rotationVector.rotateY((float) Math.toRadians(
                player.getMainArm().name() == "RIGHT" ? 45: -45));

        if (checkOomph(20, itemStack)) {
            changeOomph(-20, itemStack);
            if (!world.isClient()) {
                List<Entity> entities =  world.getOtherEntities(player, hitBox);
                for (Entity entity: entities) {
                    if (entity instanceof LivingEntity) {
                        entity.damage(DamageSource.player(player), 5);
                        entity.addVelocity(knockBackVector.x, 0.1, knockBackVector.z);
                    }
                }


            } else {
                world.addParticle(DustParticleEffect.DEFAULT, hitBoxPos.x - knockBackVector.x * 1.5, hitBoxPos.y + 1, hitBoxPos.z - knockBackVector.z * 1.5, knockBackVector.x, 0, knockBackVector.z);
                world.addParticle(DustParticleEffect.DEFAULT, hitBoxPos.x, hitBoxPos.y, hitBoxPos.z, 0, 0, 0);
                world.addParticle(DustParticleEffect.DEFAULT, hitBoxPos.x, hitBoxPos.y, hitBoxPos.z, 0, 0, 0);

                for (int i = 0; i < 20; i++) {
                    world.addParticle(DustParticleEffect.DEFAULT, hitBoxPos.x - knockBackVector.x * 1.5 + RandomGenerator.getDefault().nextDouble(-1, 1),
                            hitBoxPos.y + 1, hitBoxPos.z - knockBackVector.z * 1.5 + RandomGenerator.getDefault().nextDouble(-1, 1),
                            knockBackVector.x, 0, knockBackVector.z);

                }
            }
        }
    }

    public void heavyAttack(ItemStack itemStack, PlayerEntity player) {
    }


}
