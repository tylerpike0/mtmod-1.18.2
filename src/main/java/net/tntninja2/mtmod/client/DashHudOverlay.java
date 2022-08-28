package net.tntninja2.mtmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.tntninja2.mtmod.MTMod;
import net.tntninja2.mtmod.mixinInterface.IMixinEntity;

import java.util.jar.Attributes;

public class DashHudOverlay implements HudRenderCallback {
    private static final Identifier FILLED_DASH_ENERGY = new Identifier(MTMod.MOD_ID,
            "textures/dash/filled_dash_energy.png");
    private static final Identifier EMPTY_DASH_ENERGY = new Identifier(MTMod.MOD_ID,
            "textures/dash/empty_dash_energy.png");

    private static final Identifier EMPTY_OOMPH_BAR = new Identifier(MTMod.MOD_ID,
            "textures/oomph/empty_oomph_bar.png");

    private static final Identifier LONG_OOMPH_LINE = new Identifier(MTMod.MOD_ID,
            "textures/oomph/oomph_line.png");

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width / 2;
            y = height;
        }

        int verticalOffset = 0;

        if (MinecraftClient.getInstance().player.getAttributeValue(EntityAttributes.GENERIC_ARMOR) > 0) {
            verticalOffset += 10;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, EMPTY_DASH_ENERGY);
        for (int i = 0; i < ((IMixinEntity) client.player).getMTModData().getInt("dash_max_energy"); i++) {
            DrawableHelper.drawTexture(matrixStack, x - 94 + (i * 9), y - 54 - verticalOffset, 0, 0, 10, 12,
                    12, 12);
        }

        RenderSystem.setShaderTexture(0, FILLED_DASH_ENERGY);

        for (int i = 0; i < ((IMixinEntity) client.player).getMTModData().getInt("dash_energy"); i++) {
                DrawableHelper.drawTexture(matrixStack, x - 94 + (i * 9), y - 54 - verticalOffset, 0, 0, 10, 12,
                        12, 12);

        }


        RenderSystem.setShaderTexture(0, EMPTY_OOMPH_BAR);
        DrawableHelper.drawTexture(matrixStack, x + 10, y - 54, 0, 0, 81, 9,
                81, 9);


        RenderSystem.setShaderTexture(0, LONG_OOMPH_LINE);


        int oomph = client.player.getMainHandStack().getOrCreateSubNbt("mtmod:weapon_attacks").getInt("oomph");
        int oomphLines = oomph * 79  / 100;
        for (int i = 0; i < oomphLines; i++) {
            if (i == 0 || i == 78) {
                DrawableHelper.drawTexture(matrixStack, x + 11  + i, y - 51 - verticalOffset, 0, 0, 1, 3,
                        1, 3);
            } else if (i == 1 || i == 77) {
                DrawableHelper.drawTexture(matrixStack, x + 11  + i, y - 52 - verticalOffset, 0, 0, 1, 5,
                        1, 5);
            } else {
                DrawableHelper.drawTexture(matrixStack, x + 11 + i, y - 53 - verticalOffset, 0, 0, 1, 7,
                        1, 7);
            }

        }



    }
}
