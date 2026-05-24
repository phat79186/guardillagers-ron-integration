package com.codex.guardillagersronintegration.ron;

import com.codex.guardillagersronintegration.GuardillagersRonIntegrationMod;
import com.solegendary.reignofnether.api.ReignOfNetherRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class GuardIllagerProductionItems {
    public static final ResourceLocation GUARD_ILLAGER_ID = 
        new ResourceLocation("guardillagers_ron_integration", "guard_illager");
    
    public static final GuardIllagerProd GUARD_ILLAGER = new GuardIllagerProd();
    
    public static void register() {
        Registry.register(
            ReignOfNetherRegistries.PRODUCTION_ITEM, 
            GUARD_ILLAGER_ID, 
            GUARD_ILLAGER
        );
        GuardillagersRonIntegrationMod.LOGGER.info("Registered RoN production item {}", GUARD_ILLAGER_ID);
    }
}