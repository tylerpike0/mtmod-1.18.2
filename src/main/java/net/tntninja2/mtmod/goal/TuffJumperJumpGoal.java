package net.tntninja2.mtmod.goal;

import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.TuffJumperEntity;

import java.util.EnumSet;
import java.util.List;

public class TuffJumperJumpGoal extends Goal {
        private final MobEntity mob;

        private Vec3d oldTargetPos;
        private Vec3d currentTargetPos;
        private LivingEntity target;
        private final float velocity;
        private int ticks;

        public TuffJumperJumpGoal(MobEntity mob, float velocity) {
            this.mob = mob;
            this.velocity = velocity;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE, Control.LOOK));
            this.ticks = 0;
        }


    public boolean canStart() {
            if (this.mob.hasPassengers()) {
                return false;
            } else {
                this.target = this.mob.getTarget();
                if (this.target == null) {
                    return false;
                } else {

                    return this.mob.isOnGround();
                }
            }
        }

        public boolean shouldContinue() {
            MTMod.LOGGER.info("ticks: " + this.ticks);

            return !this.mob.isOnGround() || this.ticks < 10;
        }

    @Override
    public void tick() {
        super.tick();

        Vec3d pos = this.mob.getPos();


        if (this.ticks == 5){
            this.target = this.mob.getTarget();
            if (this.target != null) {
                currentTargetPos = this.target.getPos();
                Vec3d targetPos = currentTargetPos;
                Vec3d targetPosChange = currentTargetPos.subtract(oldTargetPos);
                while (targetPosChange.distanceTo(Vec3d.ZERO) > 5){
                    targetPosChange = targetPosChange.multiply(0.9);
                }
                targetPos = targetPos.add(targetPosChange);





                MTMod.LOGGER.info("target's velocity is " + targetPosChange);


                Vec3d jumpVector = targetPos.subtract(pos);
                double distance = pos.distanceTo(targetPos);
                double sqrtDistance = Math.sqrt(distance);
                jumpVector = jumpVector.multiply(0.07 * sqrtDistance);
                this.mob.setVelocity(jumpVector.x, 0.6, jumpVector.z);
                this.mob.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, targetPos);
            }
        }

        if (!this.mob.isOnGround()) {
            Box hitBox = new Box(pos.x - 0.5, pos.y - 0.5, pos.z - 0.5, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5);
            List<PlayerEntity> players = this.mob.world.getEntitiesByClass(PlayerEntity.class, hitBox, playerEntity -> true);
            for (PlayerEntity playerEntity: players) {
                this.mob.tryAttack(playerEntity);
            }
        }

        this.ticks++;


    }

    public void start() {
            this.ticks = 0;
            if (this.mob instanceof TuffJumperEntity mob) {
                MTMod.LOGGER.info("started");
                mob.startJump();
                oldTargetPos = this.mob.getTarget().getPos();



            }
        }
}
