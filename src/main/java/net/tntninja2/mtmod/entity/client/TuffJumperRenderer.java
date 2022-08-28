package net.tntninja2.mtmod.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.AcidSlimeEntity;
import net.tntninja2.mtmod.entity.custom.TuffJumperEntity;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TuffJumperRenderer extends GeoEntityRenderer<TuffJumperEntity> {

    public TuffJumperRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, (new TuffJumperModel()));
    }


    @Override
    public Identifier getTextureLocation(TuffJumperEntity instance) {
        return new Identifier(MTMod.MOD_ID, "textures/entity/tuff_jumper/tuff_jumper.png");
    }


}

