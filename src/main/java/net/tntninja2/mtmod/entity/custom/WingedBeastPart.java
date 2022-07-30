//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.tntninja2.mtmod.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public class WingedBeastPart extends Entity {
    public final WingedBeastEntity owner;
    public final String name;
    private final EntityDimensions partDimensions;

    public WingedBeastPart( WingedBeastEntity owner, String name, float width, float height) {
        super(owner.getType(), owner.world);
        this.partDimensions = EntityDimensions.changing(width, height);
        this.calculateDimensions();
        this.owner = owner;
        this.name = name;
    }



    protected void initDataTracker() {
    }

    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    public boolean collides() {
        return true;
    }

    public boolean damage(DamageSource source, float amount) {
        return this.isInvulnerableTo(source) ? false : this.owner.damagePart(this, source, amount);
    }

    public boolean isPartOf(Entity entity) {
        return this == entity || this.owner == entity;
    }

    public Packet<?> createSpawnPacket() {
        throw new UnsupportedOperationException();
    }

    public EntityDimensions getDimensions(EntityPose pose) {
        return this.partDimensions;
    }

    public boolean shouldSave() {
        return false;
    }
}
