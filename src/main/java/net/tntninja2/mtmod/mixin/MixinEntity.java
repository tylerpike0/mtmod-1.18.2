package net.tntninja2.mtmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.tntninja2.mtmod.mixinInterface.IMixinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity extends Object implements IMixinEntity {
    public NbtCompound mTModData;


    @Override
    public NbtCompound getMTModData() {
        if (this.mTModData == null) {
            this.mTModData = new NbtCompound();
        }
        return mTModData;
    }



    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable info) {
        if(mTModData != null) {
            nbt.put("mtmod_data", mTModData);
        }
    }


    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("mtmod_data", 10)) {
            mTModData = nbt.getCompound("mtmod_data");
        }
    }




}
