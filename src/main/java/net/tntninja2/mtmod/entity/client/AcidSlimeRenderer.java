package net.tntninja2.mtmod.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.AcidSlimeEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AcidSlimeRenderer extends GeoEntityRenderer<AcidSlimeEntity> {

    public AcidSlimeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new AcidSlimeModel());
    }


    @Override
    public Identifier getTextureLocation(AcidSlimeEntity instance) {
        return new Identifier(MTMod.MOD_ID, "textures/entity/acid_slime/acid_slime.png");
    }


}

