package net.tntninja2.mtmod.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.AcidSlimeEntity;
import net.tntninja2.mtmod.entity.custom.PetTestEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PetTestRenderer extends GeoEntityRenderer<PetTestEntity> {

    public PetTestRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PetTestModel());
    }


    @Override
    public Identifier getTextureLocation(PetTestEntity instance) {
        return new Identifier(MTMod.MOD_ID, "textures/entity/pet_test/pet_test.png");
    }


}

