package com.codex.guardillagersronintegration.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "guardillagers_ron_integration", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GuardIllagerClientHooks {
    
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Client setup if needed
    }
}