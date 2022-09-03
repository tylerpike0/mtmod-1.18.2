package net.tntninja2.mtmod.entity.client;

import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.SlateCannonEntity;
import net.tntninja2.mtmod.entity.custom.SlateJumperEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SlateCannonModel extends AnimatedGeoModel<SlateCannonEntity> {

    @Override
    public Identifier getModelLocation(SlateCannonEntity object) {
        return new Identifier(MTMod.MOD_ID, "geo/slate_jumper.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SlateCannonEntity object) {
        return new Identifier(MTMod.MOD_ID, "textures/entity/slate_jumper/slate_jumper.png");
    }

    @Override
    public Identifier getAnimationFileLocation(SlateCannonEntity animatable) {
        return new Identifier(MTMod.MOD_ID, "animations/slate_jumper.animation.json");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void setLivingAnimations(SlateCannonEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

    }
}
