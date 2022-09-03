package net.tntninja2.mtmod.entity.custom;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
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
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.networking.ModMessages;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class SlateJumperEntity extends ModMobEntity implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);

    private boolean isCrouched;
    private int crouchingTicks;
    private boolean shouldStartCrouching;

    public boolean shouldJump;
    public boolean shouldRoll;


    public SlateJumperEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.isCrouched = false;
    }


    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f);
    }

    public void tick() {
        if (this.crouchingTicks == 20) {
            this.isCrouched = true;
            crouchingTicks = -1;
        } else if (crouchingTicks > -1) {
            crouchingTicks++;
        }
        super.tick();


    }


    protected void initGoals() {
        this.goalSelector.add(0, new LookAtEntityGoal(this, PlayerEntity.class, 50));
        this.goalSelector.add(1, new SlateJumperJumpGoal(this, 1f));
        this.goalSelector.add(1, new SlateJumperCrouchGoal(this));
//        this.goalSelector.add(1, new SlateJumperRollAttackGoal(this));


        this.targetSelector.add(0, new ActiveTargetGoal(this, PlayerEntity.class, true));


//
//        this.goalSelector.add(5, new LookAroundGoal(this));


    }

    public void startJump() {
        this.shouldJump = true;
        this.isCrouched = false;
        if (!world.isClient()) {
            for (PlayerEntity playerEntity : world.getPlayers()) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(this.getId());
                ServerPlayNetworking.send((ServerPlayerEntity) playerEntity, ModMessages.ANIM_SLATE_JUMPER_JUMP_ID, buf);

            }
        }

    }
    public void startCrouch() {
        this.shouldStartCrouching = true;
        this.crouchingTicks = 0;
        if (!world.isClient()) {

            for (PlayerEntity playerEntity : world.getPlayers()) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(this.getId());
                ServerPlayNetworking.send((ServerPlayerEntity) playerEntity, ModMessages.ANIM_SLATE_JUMPER_CROUCH_ID, buf);

            }
        }

    }
    public void startRoll() {
        this.shouldRoll = true;
        this.isCrouched = false;
        MTMod.LOGGER.info("started Roll");
        if (!world.isClient()) {
            for (PlayerEntity playerEntity : world.getPlayers()) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(this.getId());
                ServerPlayNetworking.send((ServerPlayerEntity) playerEntity, ModMessages.ANIM_SLATE_JUMPER_ROLL_ID, buf);

            }
        }

    }

    private <E extends IAnimatable> PlayState predicate(@NotNull AnimationEvent<E> event) {
//        Logic for controlling animations
        if(this.shouldStartCrouching) {
            event.getController().clearAnimationCache();
            event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.slate_jumper.crouch")));
            this.shouldStartCrouching = false;
        } else if (this.shouldJump) {
            event.getController().clearAnimationCache();
            event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.slate_jumper.jump")));

            this.shouldJump = false;
        } else if (this.shouldRoll){
            MTMod.LOGGER.info("shouldRoll");
            event.getController().clearAnimationCache();
            event.getController().setAnimation((new AnimationBuilder().addAnimation("animation.slate_jumper.roll")));

            this.shouldRoll = false;
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
        return super.createNavigation(world);

    }

    static class SlateJumperJumpGoal extends Goal {
        private final SlateJumperEntity mob;

        private Vec3d oldTargetPos;
        private LivingEntity target;
        private final float velocity;
        private int ticks;
        private boolean isClose;

        public SlateJumperJumpGoal(SlateJumperEntity mob, float velocity) {
            this.mob = mob;
            this.velocity = velocity;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE, Control.LOOK));
            this.ticks = 0;
        }


        public boolean canStart() {

            this.target = this.mob.getTarget();
            if (this.target == null) {
                return false;
            } else {
                if (this.mob.isCrouched) {
                    return this.mob.isOnGround();
                } else {
                    return false;
                }
            }
        }

        public boolean shouldContinue() {

            return (!this.mob.isOnGround() || this.ticks < 10) && Math.random() < 0.5;
        }

        @Override
        public void tick() {
            super.tick();

            Vec3d pos = this.mob.getPos();

            if (this.ticks == 2) {
                if (this.isClose) {
                    if (Math.random() < 0.9) {
                        jumpAway();
                    } else {
                        jumpAtTarget(pos);
                    }
                } else {
                    jumpAtTarget(pos);
                }
            }

            if (!this.mob.isOnGround()) {
                Box hitBox = new Box(pos.x - 0.5, pos.y - 0.5, pos.z - 0.5, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5);
                List<PlayerEntity> players = this.mob.world.getEntitiesByClass(PlayerEntity.class, hitBox, playerEntity -> true);
                for (PlayerEntity playerEntity : players) {
                    this.mob.tryAttack(playerEntity);
                }
            }

            this.ticks++;


        }

        private void jumpAway() {
            this.target = this.mob.getTarget();
            if (this.target != null) {
                this.mob.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, this.target.getPos());

                Vec3d jumpVector = this.mob.getRotationVector();
                jumpVector = jumpVector.multiply(-1.5);
                this.mob.setVelocity(jumpVector.x, 0.6, jumpVector.z);
            }
        }

        private void jumpAtTarget(Vec3d pos) {
            if (this.target != null) {

                Vec3d currentTargetPos = this.target.getPos();
                Vec3d targetPos = currentTargetPos;
                Vec3d targetPosChange = currentTargetPos.subtract(oldTargetPos);
                while (targetPosChange.distanceTo(Vec3d.ZERO) > 5) {
                    targetPosChange = targetPosChange.multiply(4.9);
                }
                targetPos = targetPos.add(targetPosChange);


                MTMod.LOGGER.info("target's velocity is " + targetPosChange);


                Vec3d jumpVector = targetPos.subtract(pos);
                double distance = pos.distanceTo(targetPos);
                jumpVector = jumpVector.multiply(0.22);
                this.mob.setVelocity(jumpVector.x, 0.6, jumpVector.z);
                this.mob.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, targetPos);
            }
        }

        public void start() {
            this.ticks = 0;
            this.target = this.mob.getTarget();
            if (this.target != null) {
                this.mob.startJump();
                oldTargetPos = Objects.requireNonNull(this.target).getPos();
                this.isClose = this.mob.getPos().isInRange(oldTargetPos, 5);
            }
        }
    }

    class SlateJumperCrouchGoal extends Goal {
        private final SlateJumperEntity mob;

        private Vec3d oldTargetPos;
        private LivingEntity target;
        private int ticks;

        public SlateJumperCrouchGoal(SlateJumperEntity mob) {
            this.mob = mob;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE, Control.LOOK));
            this.ticks = 0;
        }


        public boolean canStart() {
            return this.mob.isOnGround() && !this.mob.isCrouched;
        }

        public boolean shouldContinue() {

            return !this.mob.isCrouched;
        }

        @Override
        public void tick() {
            if (this.ticks == 20){
                this.mob.isCrouched = true;
            }
            this.ticks++;
            super.tick();


        }

        public void start() {
            this.ticks = 0;
            this.mob.startCrouch();
        }
    }

    class SlateJumperRollAttackGoal extends Goal {
        private final SlateJumperEntity mob;

        private Vec3d movementDirection;
        private LivingEntity target;
        private int ticks;

        public SlateJumperRollAttackGoal(SlateJumperEntity mob) {
            this.mob = mob;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE, Control.LOOK));
            this.ticks = 0;
        }


        public boolean canStart() {
            return this.mob.isOnGround() && this.mob.isCrouched;
        }

        public boolean shouldContinue() {

            return this.ticks > 18;
        }

        @Override
        public void tick() {
            if (this.movementDirection != null) {
                this.mob.addVelocity(this.movementDirection.x, 0, this.movementDirection.z);
            }
            this.ticks++;
            super.tick();


        }

        public void start() {
            this.ticks = 0;
            this.mob.startRoll();
            this.target = this.mob.getTarget();
            if (this.target != null) {
                this.mob.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, this.target.getPos());
                Vec3d rotationVector = this.mob.getRotationVector();
                this.movementDirection = rotationVector.multiply(0.5);
            }



        }
    }
}

