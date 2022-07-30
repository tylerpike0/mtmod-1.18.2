package net.tntninja2.mtmod.entity.client;

import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.AcidSlimeEntity;
import net.tntninja2.mtmod.entity.custom.WingedBeastEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WingedBeastModel extends AnimatedGeoModel<WingedBeastEntity> {

    @Override
    public Identifier getModelLocation(WingedBeastEntity object) {
        return new Identifier(MTMod.MOD_ID, "geo/acid_slime.geo.json");
    }

    @Override
    public Identifier getTextureLocation(WingedBeastEntity object) {
        return new Identifier(MTMod.MOD_ID, "textures/entities/acid_slime/acid_slime.png");
    }

    @Override
    public Identifier getAnimationFileLocation(WingedBeastEntity animatable) {
        return new Identifier(MTMod.MOD_ID, "animations/acid_slime.animation.json");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void setLivingAnimations(WingedBeastEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

    }
}
