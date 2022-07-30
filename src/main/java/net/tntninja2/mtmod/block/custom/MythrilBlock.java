package net.tntninja2.mtmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tntninja2.mtmod.entity.custom.AcidSlimeEntity;

import java.util.Random;


public class MythrilBlock extends Block {
    private int decayProgress = 0;





    public MythrilBlock(Settings settings) {
        super(settings);
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        double yawRad = Math.toRadians(entity.getYaw());
        double deltaX = Math.sin(yawRad) * -0.1;
        double deltaZ = Math.cos(yawRad) * 0.1;
        entity.addVelocity(deltaX, 0,deltaZ);

    }





}
