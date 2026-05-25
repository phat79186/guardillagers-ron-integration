package com.codex.guardillagersronintegration;

import com.codex.guardillagersronintegration.ron.GuardIllagerProductionItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod("guardillagers_ron_integration")
public class GuardillagersRonIntegrationMod {
    public static final String MOD_ID = "guardillagers_ron_integration";
    public static final Logger LOGGER = com.mojang.logging.LogUtils.getLogger();

    public GuardillagersRonIntegrationMod() {
        IEventBus modEventBus = net.minecraftforge.fml.common.Mod.EventBusSubscriber.bus();
        modEventBus.register(com.codex.guardillagersronintegration.ModEntityAttributes.class);
        GuardIllagerProductionItems.register();
        LOGGER.info("RoN Guard Illager Integration loaded - Using Forge Events for safe registration");
    }
}