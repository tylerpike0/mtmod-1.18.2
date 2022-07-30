package net.tntninja2.mtmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.tntninja2.mtmod.entity.ModEntities;
import net.tntninja2.mtmod.entity.client.AcidSlimeRenderer;
import net.tntninja2.mtmod.entity.client.MythrilGolemRenderer;
import net.tntninja2.mtmod.entity.client.WingedBeastRenderer;
import net.tntninja2.mtmod.event.KeyInputHandler;
import net.tntninja2.mtmod.networking.ModMessages;
import software.bernie.geckolib3.GeckoLib;


public class MTModClient implements ClientModInitializer {
        @Override
        public void onInitializeClient() {
            KeyInputHandler.register();

            GeckoLib.initialize();
            EntityRendererRegistry.register(ModEntities.MYTHRIL_GOLEM, MythrilGolemRenderer::new);
            EntityRendererRegistry.register(ModEntities.ACID_SLIME, AcidSlimeRenderer::new);
            EntityRendererRegistry.register(ModEntities.WINGED_BEAST, WingedBeastRenderer::new);
            ModMessages.registerS2CPackets();
        }


    }

