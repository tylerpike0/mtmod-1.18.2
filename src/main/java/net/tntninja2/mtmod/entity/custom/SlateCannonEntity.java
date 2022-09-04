package net.tntninja2.mtmod.entity.custom;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tntninja2.mtmod.networking.ModMessages;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class SlateCannonEntity extends ModMobEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    boolean shouldLaser;


    public SlateCannonEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
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
        this.goalSelector.add(0, new SlateCannonAttackGoal(this));


        this.targetSelector.add(0, new ActiveTargetGoal(this, PlayerEntity.class, true));


//
//        this.goalSelector.add(5, new LookAroundGoal(this));

    }


    public void startLaser(){
        this.shouldLaser = true;
        if (!world.isClient()) {
            for (PlayerEntity playerEntity : world.getPlayers()) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(this.getId());
                ServerPlayNetworking.send((ServerPlayerEntity) playerEntity, ModMessages.ANIM_SLATE_CANNON_LASER_ID, buf);

            }
        }
    }



    private  <E extends IAnimatable> PlayState predicate(@NotNull AnimationEvent<E> event) {
//        Logic for controlling animations
        if (shouldLaser) {
            event.getController().clearAnimationCache();
            event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.slate_cannon.heatup")));
            this.shouldLaser = false;
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



    class SlateCannonAttackGoal extends Goal {

        int ticks;

        final SlateCannonEntity slateCannonEntity;

        LivingEntity target;

        SlateCannonAttackGoal(SlateCannonEntity slateCannonEntity) {
            this.slateCannonEntity = slateCannonEntity;
        }


        @Override
        public boolean canStart() {
            this.target = this.slateCannonEntity.getTarget();
            if (this.target != null) {
                Vec3d cannonPos = this.slateCannonEntity.getPos();
                Vec3d targetPos = this.target.getPos();
                double distance = cannonPos.distanceTo(targetPos);
                return distance < 40 && distance > 5;
            } else {
                return false;
            }

        }

        @Override
        public boolean shouldContinue() {
            return ticks < 100;

        }

        @Override
        public void tick() {
            super.tick();

            if (ticks < 80) {
                this.slateCannonEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, this.target.getPos());
            }

            this.ticks++;
        }

        @Override
        public void start() {
            this.target = this.slateCannonEntity.getTarget();

            this.ticks = 0;

            this.slateCannonEntity.startLaser();

            super.start();
        }


        @Override
        public void stop() {
            Vec3d pos = this.slateCannonEntity.getPos();
            Vec3d rotationVector = this.slateCannonEntity.getRotationVector();
            rotationVector = rotationVector.multiply(0.1);

            for (int i = 0; i < 500; i++) {

                Vec3d distance = rotationVector.multiply(i);
                Vec3d hitBoxPos = pos.add(distance);
                Box hitBox = new Box(hitBoxPos.x - 0.5, hitBoxPos.y - 0.5, hitBoxPos.z - 0.5,
                        hitBoxPos.x + 0.5, hitBoxPos.y + 0.5, hitBoxPos.z + 0.5);

                List<LivingEntity> livingEntities = this.slateCannonEntity.world.getEntitiesByClass(LivingEntity.class, hitBox, livingEntity -> {
                   return !(livingEntity instanceof SlateCannonEntity);
                });
                for (LivingEntity livingEntity : livingEntities) {
                    this.slateCannonEntity.tryAttack(livingEntity);
                }


            }

            super.stop();
        }
    }
}
