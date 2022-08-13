package net.tntninja2.mtmod.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.task.MeleeAttackTask;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.TriggerCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.test.TimedTaskRunner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class MythrilGolemEntity extends PathAwareEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);


    public MythrilGolemEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }


//    public static DefaultAttributeContainer.Builder setAttributes() {
//        return AnimalEntity.createMobAttributes()
//                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10f)
//                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2f)
//                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10f)
//                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
//                .add(EntityAttributes.GENERIC_ARMOR, 5f)
//                .add(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, 5f);
//    }
public static DefaultAttributeContainer.Builder setAttributes() {
    return AnimalEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0f)
            .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f);

}

    protected void initGoals() {

        this.goalSelector.add(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.add(0, new PounceAtTargetGoal(this, 0.5f));
        this.goalSelector.add(10, new FleeEntityGoal<>(this, MythrilGolemEntity.class, 4f, 0.75d, 1.2d));
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new EscapeDangerGoal(this, 2f));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.9f));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));

        this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal(this, MythrilGolemEntity.class, true));

    }

    private  <E extends IAnimatable> PlayState predicate(@NotNull AnimationEvent<E> event) {
//        Logic for controlling animations
        if (event.isMoving()) {
            event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.mythril_golem.walk", true)));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.mythril_golem.idle", true)));
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

}
