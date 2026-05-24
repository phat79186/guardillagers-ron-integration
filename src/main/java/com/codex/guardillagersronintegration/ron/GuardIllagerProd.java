package com.codex.guardillagersronintegration.ron;

import com.codex.guardillagersronintegration.GuardillagersRonIntegrationMod;
import com.solegendary.reignofnether.building.buildings.placements.ProductionPlacement;
import com.solegendary.reignofnether.building.production.ProductionItem;
import com.solegendary.reignofnether.building.production.StartProductionButton;
import com.solegendary.reignofnether.building.production.StopProductionButton;
import com.solegendary.reignofnether.hud.buttons.UnitSpawnButton;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.resources.ResourceCost;
import com.solegendary.reignofnether.resources.ResourceCosts;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public class GuardIllagerProd implements ProductionItem {
    
    public static final String ITEM_NAME = "Guard Illager";
    public static final ResourceLocation ICON_TEXTURE = 
        new ResourceLocation("guardillagers_ron_integration", "textures/icons/guardillager.png");
    public static final ResourceCost RESOURCE_COST = new ResourceCost(80, 40, 0, 0);
    
    public GuardIllagerProd() {}
    
    @Override
    public String getItemName() {
        return ITEM_NAME;
    }
    
    @Override
    public UnitSpawnButton getPlaceButton() {
        return null;
    }
    
    @Override
    public StartProductionButton getStartButton(ProductionPlacement placement, Keybinding hotkey) {
        ResourceLocation id = new ResourceLocation("guardillagers_ron_integration", "guard_illager");
        return this.createStartButton(ITEM_NAME, id, getTooltip(), hotkey, placement);
    }
    
    @Override
    public StopProductionButton getCancelButton(ProductionPlacement placement, boolean isPrimary) {
        ResourceLocation id = new ResourceLocation("guardillagers_ron_integration", "guard_illager");
        return this.createCancelButton(ITEM_NAME, id, ICON_TEXTURE, placement, isPrimary);
    }
    
    private List<Component> getTooltip() {
        return List.of(
            Component.literal("Guard Illager"),
            Component.literal("HP: 50"),
            Component.literal("Melee fighter unit"),
            Component.literal("Summoned from Barracks")
        );
    }
    
    private StartProductionButton createStartButton(String name, ResourceLocation id, 
            List<Component> tooltip, Keybinding hotkey, ProductionPlacement placement) {
        try {
            Class<?> clazz = Class.forName("com.solegendary.reignofnether.building.production.StartProductionButton");
            java.lang.reflect.Constructor<?> ctor = clazz.getConstructor(
                String.class, ResourceLocation.class, List.class, Keybinding.class,
                java.util.function.Supplier.class, java.util.function.Supplier.class,
                List.class, ProductionItem.class, ProductionPlacement.class
            );
            return (StartProductionButton) ctor.newInstance(
                name, id, tooltip, hotkey,
                () -> true,
                () -> ResourceCosts.getFormattedCost(RESOURCE_COST) + " - 25s",
                tooltip, this, placement
            );
        } catch (Exception e) {
            GuardillagersRonIntegrationMod.LOGGER.warn("Failed to create StartProductionButton: {}", e.getMessage());
            return null;
        }
    }
    
    private StopProductionButton createCancelButton(String name, ResourceLocation id,
            ResourceLocation texture, ProductionPlacement placement, boolean isPrimary) {
        try {
            Class<?> clazz = Class.forName("com.solegendary.reignofnether.building.production.StopProductionButton");
            java.lang.reflect.Constructor<?> ctor = clazz.getConstructor(
                String.class, ResourceLocation.class, ResourceLocation.class,
                ProductionPlacement.class, ProductionItem.class, boolean.class
            );
            return (StopProductionButton) ctor.newInstance(
                name, id, texture, placement, this, isPrimary
            );
        } can catch (Exception e) {
            GuardillagersRonIntegrationMod.LOGGER.warn("Failed to create StopProductionButton: {}", e.getMessage());
            return null;
        }
    }
    
    @Override
    public void spawnUnit(Level level, ProductionPlacement placement) {
        EntityType<?> entityType = resolveEntityType();
        if (entityType == null) {
            GuardillagersRonIntegrationMod.LOGGER.warn("Could not resolve Guard Illager entity type");
            return;
        }
        Entity entity = entityType.create(level);
        if (entity != null) {
            entity.moveTo(placement.pos, 0, 0);
        }
    }
    
    private static EntityType<?> resolveEntityType() {
        try {
            ResourceLocation loc = new ResourceLocation("guardillagers", "guard_illager");
            return net.minecraft.core.Registry.ENTITY_TYPE.get(loc);
        } catch (Exception e) {
            GuardillagersRonIntegrationMod.LOGGER.warn("Failed to resolve Guard Illager entity: {}", e.getMessage());
            return null;
        }
    }
}
