package net.tntninja2.mtmod.entity.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.entity.custom.MythrilGolemEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MythrilGolemRenderer extends GeoEntityRenderer<MythrilGolemEntity> {

    public MythrilGolemRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MythrilGolemModel());
    }


    @Override
    public Identifier getTextureLocation(MythrilGolemEntity instance) {
        return new Identifier(MTMod.MOD_ID, "textures/entity/mythril_golem/mythril_golem.png");
    }


}

