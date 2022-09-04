package net.tntninja2.mtmod.entity.client;

import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.SlateCannonEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

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

    }
}
