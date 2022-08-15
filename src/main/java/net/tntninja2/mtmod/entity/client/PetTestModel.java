package net.tntninja2.mtmod.entity.client;

import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.PetTestEntity;
import net.tntninja2.mtmod.entity.custom.PetTestEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PetTestModel extends AnimatedGeoModel<PetTestEntity> {

    @Override
    public Identifier getModelLocation(PetTestEntity object) {
        return new Identifier(MTMod.MOD_ID, "geo/pet_test.geo.json");
    }

    @Override
    public Identifier getTextureLocation(PetTestEntity object) {
        return new Identifier(MTMod.MOD_ID, "textures/entities/pet_test/pet_test.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PetTestEntity animatable) {
        return new Identifier(MTMod.MOD_ID, "animations/pet_test.animation.json");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void setLivingAnimations(PetTestEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

    }
}
