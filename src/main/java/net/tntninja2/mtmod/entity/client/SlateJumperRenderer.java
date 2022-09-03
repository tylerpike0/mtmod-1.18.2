package net.tntninja2.mtmod.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.SlateJumperEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SlateJumperRenderer extends GeoEntityRenderer<SlateJumperEntity> {

    public SlateJumperRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, (new SlateJumperModel()));
    }


    @Override
    public Identifier getTextureLocation(SlateJumperEntity instance) {
        return new Identifier(MTMod.MOD_ID, "textures/entity/slate_jumper/slate_jumper.png");
    }


}

