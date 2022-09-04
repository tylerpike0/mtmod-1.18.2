package net.tntninja2.mtmod.entity.client;

import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.SlateCannonEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SlateCannonModel extends AnimatedGeoModel<SlateCannonEntity> {

    @Override
    public Identifier getModelLocation(SlateCannonEntity object) {
        return new Identifier(MTMod.MOD_ID, "geo/slate_cannon.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SlateCannonEntity object) {
        return new Identifier(MTMod.MOD_ID, "textures/entity/slate_cannon/slate_cannon.png");
    }

    @Override
    public Identifier getAnimationFileLocation(SlateCannonEntity animatable) {
        return new Identifier(MTMod.MOD_ID, "animations/slate_cannon.animation.json");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void setLivingAnimations(SlateCannonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180f));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180f));
        }

    }
}
