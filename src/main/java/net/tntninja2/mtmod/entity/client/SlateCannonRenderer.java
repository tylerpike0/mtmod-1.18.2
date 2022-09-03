package net.tntninja2.mtmod.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.SlateCannonEntity;
import net.tntninja2.mtmod.entity.custom.SlateJumperEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SlateCannonRenderer extends GeoEntityRenderer<SlateCannonEntity> {

    public SlateCannonRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, (new SlateCannonModel()));
    }


    @Override
    public Identifier getTextureLocation(SlateCannonEntity instance) {
        return new Identifier(MTMod.MOD_ID, "textures/entity/slate_cannon/slate_cannon.png");
    }


}

