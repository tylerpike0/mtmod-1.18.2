package net.tntninja2.mtmod.block.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tntninja2.mtmod.entity.custom.AcidSlimeEntity;

import java.util.Random;



public class AcidBlock extends CarpetBlock {
    private int decayProgress = 0;


    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (decayProgress == 5) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        } else {
            decayProgress++;
        }


    }


    public AcidBlock(Settings settings) {
        super(settings);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof AcidSlimeEntity) {
        } else {
            entity.damage(DamageSource.MAGIC, 1f);
        }
    }





}
