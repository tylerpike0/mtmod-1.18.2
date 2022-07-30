package net.tntninja2.mtmod.entity.client;

import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.AcidSlimeEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class AcidSlimeModel extends AnimatedGeoModel<AcidSlimeEntity> {

    @Override
    public Identifier getModelLocation(AcidSlimeEntity object) {
        return new Identifier(MTMod.MOD_ID, "geo/acid_slime.geo.json");
    }

    @Override
    public Identifier getTextureLocation(AcidSlimeEntity object) {
        return new Identifier(MTMod.MOD_ID, "textures/entities/acid_slime/acid_slime.png");
    }

    @Override
    public Identifier getAnimationFileLocation(AcidSlimeEntity animatable) {
        return new Identifier(MTMod.MOD_ID, "animations/acid_slime.animation.json");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void setLivingAnimations(AcidSlimeEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

    }
}
