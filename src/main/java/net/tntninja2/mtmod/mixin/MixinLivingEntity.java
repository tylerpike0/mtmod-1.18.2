package net.tntninja2.mtmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.event.LivingEntityDamageCancelCallback;
import net.tntninja2.mtmod.event.PlayerHitEntityCallback;
import net.tntninja2.mtmod.mixinInterface.IMixinEntity;
import net.tntninja2.mtmod.mixinInterface.IMixinLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements IMixinLivingEntity {


    protected MixinLivingEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }




    @Inject(method = "tick", at = @At("HEAD"))
    public void injectTick(CallbackInfo info) {
        int invulnerabilityTicks = ((IMixinEntity) this).getMTModData().getInt("invulnerability_ticks");
        if (invulnerabilityTicks > 0) {
            invulnerabilityTicks--;
        }
        ((IMixinEntity) this).getMTModData().putInt("invulnerability_ticks", invulnerabilityTicks);
    }


@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void injectDamage(DamageSource source, float amount, CallbackInfoReturnable info) {
    int invulnerabilityTicks = ((IMixinEntity) this).getMTModData().getInt("invulnerability_ticks");
    if (invulnerabilityTicks > 0) {
        ActionResult result = LivingEntityDamageCancelCallback.EVENT.invoker().damageCancel((LivingEntity) (Object) this, source, amount);
        info.cancel();
    }
}





}
