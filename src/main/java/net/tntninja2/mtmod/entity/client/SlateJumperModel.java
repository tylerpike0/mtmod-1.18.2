package net.tntninja2.mtmod.entity.client;

import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.SlateJumperEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SlateJumperModel extends AnimatedGeoModel<SlateJumperEntity> {

    @Override
    public Identifier getModelLocation(SlateJumperEntity object) {
        return new Identifier(MTMod.MOD_ID, "geo/slate_jumper.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SlateJumperEntity object) {
        return new Identifier(MTMod.MOD_ID, "textures/entity/slate_jumper/slate_jumper.png");
    }

    @Override
    public Identifier getAnimationFileLocation(SlateJumperEntity animatable) {
        return new Identifier(MTMod.MOD_ID, "animations/slate_jumper.animation.json");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void setLivingAnimations(SlateJumperEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

    }
}
