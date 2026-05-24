package com.codex.guardillagersronintegration;

import com.solegendary.reignofnether.resources.ResourceCost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuardillagersRonIntegrationValues {
    public static final Logger LOGGER = LoggerFactory.getLogger("guardillagers_ron_integration");
    
    public static final String MOD_ID = "guardillagers_ron_integration";
    public static final String SOURCE_ENTITY_ID = "guard_illager";
    
    public static final String DISPLAY_NAME = "Guard Illager";
    
    public static final ResourceLocation ICON_TEXTURE = 
        new ResourceLocation(MOD_ID, "textures/icons/guardillager.png");
    
    public static final ResourceLocation MOBHEAD_TEXTURE = 
        new ResourceLocation("reignofnether", "textures/mobheads/guardillager.png");
    
    public static final ResourceCost RESOURCE_COST = new ResourceCost(80, 40, 0, 0);
    
    public static final float MOVEMENT_SPEED = 0.3f;
    public static final float MAX_HEALTH = 50.0f;
    public static final int ARMOR = 2;
    
    private static EntityType<?> sourceEntityType = null;
    
    public static EntityType<?> resolveSourceEntityType() {
        if (sourceEntityType == null) {
            try {
                sourceEntityType = net.minecraft.core.Registry.ENTITY_TYPE.get(
                    new ResourceLocation("guardillagers", SOURCE_ENTITY_ID)
                );
                LOGGER.info("Resolved Guard Illager entity type: {}", sourceEntityType);
            } catch (Exception e) {
                LOGGER.warn("Could not resolve Guard Illager entity type {}: {}", 
                    SOURCE_ENTITY_ID, e.getMessage());
                
                // Fallback: try alternate registry name
                try {
                    sourceEntityType = net.minecraft.core.Registry.ENTITY_TYPE.get(
                        new ResourceLocation("guardillagers", "guard_illager")
                    );
                } catch (Exception e2) {
                    LOGGER.warn("Fallback也无法找到 Guard Illager entity");
                }
            }
        }
        return sourceEntityType;
    }
}
