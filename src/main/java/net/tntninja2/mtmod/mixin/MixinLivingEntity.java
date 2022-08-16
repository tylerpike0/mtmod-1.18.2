package net.tntninja2.mtmod.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.event.LivingEntityDamageCancelCallback;
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
    public void injectDamage(DamageSource source, float amount, CallbackInfoReturnable info) throws CommandSyntaxException {
    int invulnerabilityTicks = ((IMixinEntity) this).getMTModData().getInt("invulnerability_ticks");
    if (invulnerabilityTicks > 0) {
        ActionResult result = LivingEntityDamageCancelCallback.EVENT.invoker().damageCancel((LivingEntity) (Object) this, source, amount);
        if (((Object) this) instanceof ServerPlayerEntity) {
            (((ServerPlayerEntity) (Object) this)).networkHandler.sendPacket(new PlaySoundIdS2CPacket(SoundEvents.BLOCK_NOTE_BLOCK_CHIME.getId(), SoundCategory.MASTER, this.getPos(), 10, 1));
            MTMod.LOGGER.info("mixin living entity IS a serverplayerentity");

        } else {
            MTMod.LOGGER.info("mixin living entity is NOT a serverplayerentity");
        }
        info.cancel();
    }
}





}
