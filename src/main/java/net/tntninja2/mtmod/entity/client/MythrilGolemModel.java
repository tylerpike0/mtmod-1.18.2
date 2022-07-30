package net.tntninja2.mtmod.entity.client;

import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.MythrilGolemEntity;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class MythrilGolemModel extends AnimatedGeoModel<MythrilGolemEntity> {

    @Override
    public Identifier getModelLocation(MythrilGolemEntity object) {
        return new Identifier(MTMod.MOD_ID, "geo/mythril_golem.geo.json");
    }

    @Override
    public Identifier getTextureLocation(MythrilGolemEntity object) {
        return new Identifier(MTMod.MOD_ID, "textures/entities/mythril_golem/mythril_golem.png");
    }

    @Override
    public Identifier getAnimationFileLocation(MythrilGolemEntity animatable) {
        return new Identifier(MTMod.MOD_ID, "animations/mythril_golem.animation.json");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void setLivingAnimations(MythrilGolemEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180f));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180f));
        }
    }
}
