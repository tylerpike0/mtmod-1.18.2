package net.tntninja2.mtmod.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tntninja2.mtmod.block.ModBlocks;
import net.tntninja2.mtmod.goal.EscapeAcidGoal;
import net.tntninja2.mtmod.item.custom.petTotems.PetTestTotemItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class PetTestEntity extends PetEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    public PetTestEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }




    public static DefaultAttributeContainer.Builder setAttributes() {
    return AnimalEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0f)
            .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f);
}

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            LivingEntity owner = this.getOwner();
            if (owner != null) {
                ItemStack itemStack = owner.getOffHandStack();
                if (itemStack.getItem().getClass() != PetTestTotemItem.class) {
                    this.kill();
                }
            }
        }



    }





    protected void initGoals() {


        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.9f));
        this.goalSelector.add(5, new WanderAroundGoal(this, 0.9f));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(2, new AttackWithOwnerGoal(this));

//
//        this.goalSelector.add(5, new LookAroundGoal(this));



    }

    private  <E extends IAnimatable> PlayState predicate(@NotNull AnimationEvent<E> event) {
//        Logic for controlling animations

        event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.pet_test.walk", true)));
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

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
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
