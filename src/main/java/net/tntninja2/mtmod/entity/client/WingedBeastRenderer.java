package net.tntninja2.mtmod.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.AcidSlimeEntity;
import net.tntninja2.mtmod.entity.custom.WingedBeastEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WingedBeastRenderer extends GeoEntityRenderer<WingedBeastEntity> {

    public WingedBeastRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WingedBeastModel());
    }


    @Override
    public Identifier getTextureLocation(WingedBeastEntity instance) {
        return new Identifier(MTMod.MOD_ID, "textures/entity/acid_slime/acid_slime.png");
    }


}

