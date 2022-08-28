package net.tntninja2.mtmod.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;

import java.util.EnumSet;

public class FaceTowardTargetGoal extends Goal {
    private final MobEntity mob;
    private int ticksLeft;

    public FaceTowardTargetGoal(MobEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.LOOK));
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null) {
            return false;
        } else {
            return false;
        }
    }

    public void start() {
        this.ticksLeft = toGoalTicks(300);
        super.start();
    }

    public boolean shouldContinue() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null) {
            return false;
        } else if (!this.mob.canTarget(livingEntity)) {
            return false;
        } else {
            return --this.ticksLeft > 0;
        }
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null) {
            this.mob.lookAtEntity(livingEntity, 10.0F, 10.0F);
        }

    }
}
