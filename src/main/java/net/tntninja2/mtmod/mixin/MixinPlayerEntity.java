package net.tntninja2.mtmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.event.PlayerHitEntityCallback;
import net.tntninja2.mtmod.mixinInterface.IMixinPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity implements IMixinPlayerEntity {

    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    public void injectAttack(Entity target, CallbackInfo info) {
        MTMod.LOGGER.info(target.getClass().getName() + "was hurt");
        ActionResult result = PlayerHitEntityCallback.EVENT.invoker().hurtEntity((PlayerEntity) (Object) this, target);


    }


}
