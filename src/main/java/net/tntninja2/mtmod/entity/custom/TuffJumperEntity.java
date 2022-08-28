package net.tntninja2.mtmod.entity.custom;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.block.ModBlocks;
import net.tntninja2.mtmod.goal.EscapeAcidGoal;
import net.tntninja2.mtmod.goal.TuffJumperJumpGoal;
import net.tntninja2.mtmod.networking.ModMessages;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TuffJumperEntity extends ModMobEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public boolean shouldJump;


    public TuffJumperEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }



    public static DefaultAttributeContainer.Builder setAttributes() {
    return AnimalEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0f)
            .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f);
}

    public void tick() {
        super.tick();



    }




    protected void initGoals() {
        this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 50));
        this.goalSelector.add(1, new TuffJumperJumpGoal(this, 1f));


        this.targetSelector.add(0, new ActiveTargetGoal(this, PlayerEntity.class, true));



//
//        this.goalSelector.add(5, new LookAroundGoal(this));



    }

    public void startJump() {
        this.shouldJump = true;
        MTMod.LOGGER.info("startedJump");
        if (!world.isClient()) {
            for (PlayerEntity playerEntity: world.getPlayers() ) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(this.getId());
                ServerPlayNetworking.send((ServerPlayerEntity) playerEntity, ModMessages.ANIM_TUFF_JUMPER_JUMP_ID, buf);

            }
        }

    }

    private  <E extends IAnimatable> PlayState predicate(@NotNull AnimationEvent<E> event) {
//        Logic for controlling animations
        if (this.shouldJump) {
            MTMod.LOGGER.info("Set animation on " + this.world.getClass().descriptorString());
            event.getController().clearAnimationCache();
            event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.tuff_jumper.jump", true)));

            this.shouldJump = false;
        } else {
        }

        return PlayState.CONTINUE;

    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController((new AnimationController(this, "controller",
                0, this::predicate)));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1f);
    }

    protected EntityNavigation createNavigation(World world) {
        return new Navigation(this, world);
    }


    private static class Navigation extends MobNavigation{

        public Navigation(MobEntity mobEntity, World world) {
            super(mobEntity, world);
        }

        protected void adjustPath() {
            super.adjustPath();
            if (world.getBlockState(new BlockPos(this.entity.getX(), this.entity.getY() + 0.5, this.entity.getZ())) == ModBlocks.ACID_BLOCK.getDefaultState()) {
                return;
            }

            for(int i = 0; i < this.currentPath.getLength(); ++i) {
                PathNode pathNode = this.currentPath.getNode(i);
                if (this.world.getBlockState(new BlockPos(this.entity.getX(), this.entity.getY() + 0.5, this.entity.getZ())) == ModBlocks.ACID_BLOCK.getDefaultState()) {
                    this.currentPath.setLength(i);
                    return;
                }
            }


        }
    }

}
