package net.tntninja2.mtmod.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tntninja2.mtmod.entity.ModEntities;
import net.tntninja2.mtmod.mixin.ModEntityDataSaver;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WingedBeastEntity extends PathAwareEntity implements IAnimatable {

    private final WingedBeastPart[] parts;
    public final WingedBeastPart head = new WingedBeastPart(this, "head", 1F, 0.5F);
    public final WingedBeastPart tail = new WingedBeastPart(this, "tail", 0.5F, 1F);


    private final AnimationFactory factory = new AnimationFactory(this);


    public WingedBeastEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(ModEntities.WINGED_BEAST, world);
        this.parts = new WingedBeastPart[]{this.head,this.tail};

    }

    private void movePart(WingedBeastPart wingedBeastPart, double dx, double dy, double dz) {
        wingedBeastPart.setPosition(this.getX() + dx, this.getY() + dy, this.getZ() + dz);
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

    public void tickMovement() {

    }

    public boolean collides() {
        return false;
    }
    public boolean damagePart(WingedBeastPart part, DamageSource source, float amount) {
        return true;
    }




    protected void initGoals() {


        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new WanderAroundFarGoal(this, 0.9f));
        this.goalSelector.add(1, new WanderAroundGoal(this, 0.9f));
//
//        this.goalSelector.add(5, new LookAroundGoal(this));



    }

    private  <E extends IAnimatable> PlayState predicate(@NotNull AnimationEvent<E> event) {
//        Logic for controlling animations

        event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.acid_slime.move", true)));
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
