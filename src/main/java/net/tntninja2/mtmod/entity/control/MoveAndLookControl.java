package net.tntninja2.mtmod.entity.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.mob.MobEntity;

public class MoveAndLookControl extends MoveControl {
    protected float targetYaw;
    protected float targetPitch;
    public MoveAndLookControl(MobEntity entity) {
        super(entity);
    }

    public void look(float targetYaw, float targetPitch) {
        this.targetYaw = targetYaw;
        this.targetPitch = targetPitch;
    }

    @Override
    public void tick() {
        this.entity.setYaw(this.targetYaw);
        this.entity.setPitch(this.targetPitch);
        super.tick();
    }
}
