package net.tntninja2.mtmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.tntninja2.mtmod.client.DashHudOverlay;
import net.tntninja2.mtmod.entity.ModEntities;
import net.tntninja2.mtmod.entity.client.*;
import net.tntninja2.mtmod.event.KeyInputHandler;
import net.tntninja2.mtmod.networking.ModMessages;
import software.bernie.geckolib3.GeckoLib;



public class MTModClient implements ClientModInitializer {
        @Override
        public void onInitializeClient() {


            GeckoLib.initialize();
            EntityRendererRegistry.register(ModEntities.MYTHRIL_GOLEM, MythrilGolemRenderer::new);
            EntityRendererRegistry.register(ModEntities.ACID_SLIME, AcidSlimeRenderer::new);
            EntityRendererRegistry.register(ModEntities.WINGED_BEAST, WingedBeastRenderer::new);
            EntityRendererRegistry.register(ModEntities.PET_TEST, PetTestRenderer::new);
            EntityRendererRegistry.register(ModEntities.SLATE_JUMPER, SlateJumperRenderer::new);

            HudRenderCallback.EVENT.register(new DashHudOverlay());

            ModMessages.registerS2CPackets();


            KeyInputHandler.register();
            MTModTickEvents.registerClientEndTickEvent();

        }




}

