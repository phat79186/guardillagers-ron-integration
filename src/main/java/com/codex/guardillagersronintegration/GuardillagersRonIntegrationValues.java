package com.codex.guardillagersronintegration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class GuardillagersRonIntegrationValues {
    public static final String MOD_ID = "guardillagers_ron_integration";
    
    public static final String DISPLAY_NAME = "Guard Illager";
    public static final ResourceLocation SOURCE_ENTITY_ID = new ResourceLocation("guardillagers", "guard_illager");
    public static final ResourceLocation ICON_TEXTURE = new ResourceLocation(MOD_ID, "textures/icons/guardillager.png");
    
    public static final float MAX_HEALTH = 24.0F;
    public static final float MOVEMENT_SPEED = 0.35F;
    
    public static final int POPULATION_COST = 1;
    public static final com.solegendary.reignofnether.resources.ResourceCost RESOURCE_COST = 
        new com.solegendary.reignofnether.resources.ResourceCost(80, 40, 0, 0);
    
    public static EntityType<?> resolveSourceEntityType() {
        return ForgeRegistries.ENTITY_TYPES.getValue(SOURCE_ENTITY_ID);
    }
}