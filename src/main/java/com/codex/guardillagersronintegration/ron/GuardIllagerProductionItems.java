package com.codex.guardillagersronintegration.ron;

import com.codex.guardillagersronintegration.GuardillagersRonIntegrationMod;
import com.solegendary.reignofnether.resources.ResourceCost;
import net.minecraft.resources.ResourceLocation;

public class GuardIllagerProductionItems {
    
    public static final String GUARD_ILLAGER_ID = "guard_illager";
    
    public static final GuardIllagerProd GUARD_ILLAGER = new GuardIllagerProd();
    
    public static void init() {
        // Just initialize - registration is done by BarracksMixin
        GuardillagersRonIntegrationMod.LOGGER.info("Initialized Guard Illager production item");
    }
}
