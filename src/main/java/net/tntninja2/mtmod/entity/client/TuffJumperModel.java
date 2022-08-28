package net.tntninja2.mtmod.entity.client;

import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.AcidSlimeEntity;
import net.tntninja2.mtmod.entity.custom.TuffJumperEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TuffJumperModel extends AnimatedGeoModel<TuffJumperEntity> {

    @Override
    public Identifier getModelLocation(TuffJumperEntity object) {
        return new Identifier(MTMod.MOD_ID, "geo/tuff_jumper.geo.json");
    }

    @Override
    public Identifier getTextureLocation(TuffJumperEntity object) {
        return new Identifier(MTMod.MOD_ID, "textures/entities/tuff_jumper/tuff_jumper.png");
    }

    @Override
    public Identifier getAnimationFileLocation(TuffJumperEntity animatable) {
        return new Identifier(MTMod.MOD_ID, "animations/tuff_jumper.animation.json");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void setLivingAnimations(TuffJumperEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

    }
}
