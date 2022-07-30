package net.tntninja2.mtmod.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.tntninja2.mtmod.util.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class ModEntityDataSaver implements IEntityDataSaver {
    private NbtCompound dashData;

    @Override
    public NbtCompound getPersistentData() {
        if (this.dashData == null) {
            this.dashData = new NbtCompound();
        }
        return dashData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectedWriteMethod(NbtCompound nbt, CallbackInfoReturnable info) {
        if(dashData != null) {
            nbt.put("mtmod.tnt_ninja2_data", dashData);
        }
    }


    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("mtmod.tntninja2_data", 10)) {
            dashData = nbt.getCompound("mtmod.tntninja2_data");
        }
    }




}
