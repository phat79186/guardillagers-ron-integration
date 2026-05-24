package com.codex.guardillagersronintegration.ron;

import com.codex.guardillagersronintegration.GuardillagersRonIntegrationMod;
import com.codex.guardillagersronintegration.GuardillagersRonIntegrationValues;
import com.solegendary.reignofnether.building.buildings.placements.ProductionPlacement;
import com.solegendary.reignofnether.building.production.ProductionItem;
import com.solegendary.reignofnether.building.production.StartProductionButton;
import com.solegendary.reignofnether.building.production.StopProductionButton;
import com.solegendary.reignofnether.hud.buttons.UnitSpawnButton;
import com.solegendary.reignofnether.keybinds.Keybinding;
import com.solegendary.reignofnether.resources.ResourceCosts;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static com.codex.guardillagersronintegration.GuardillagersRonIntegrationValues.*;

public class GuardIllagerProd implements ProductionItem {
    
    public static final String ITEM_NAME = "Guard Illager";
    public static final com.solegendary.reignofnether.resources.ResourceCost RESOURCE_COST = 
        new com.solegendary.reignofnether.resources.ResourceCost(80, 40, 0, 0);
    
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
        BiConsumer<Level, ProductionPlacement> onComplete = (level, pl) -> spawnUnit(level, pl);
        return new StartProductionButton(
            "units.villagers.reignofnether.guard_illager",
            GuardIllagerProductionItems.GUARD_ILLAGER_ID,
            getUnitTooltip(),
            hotkey,
            () -> (Supplier<Boolean>) () -> true,
            () -> (Supplier<String>) () -> ResourceCosts.getFormattedCost(RESOURCE_COST) + " • " + "25s",
            getUnitTooltip(),
            this,
            placement,
            onComplete
        );
    }
    
    @Override
    public StopProductionButton getCancelButton(ProductionPlacement placement, boolean isPrimary) {
        return new StopProductionButton(
            "units.villagers.reignofnether.guard_illager",
            GuardIllagerProductionItems.GUARD_ILLAGER_ID,
            ICON_TEXTURE,
            placement,
            this,
            isPrimary
        );
    }
    
    private List<Component> getUnitTooltip() {
        return List.of(
            Component.literal("Guard Illager"),
            Component.literal("Uses the original Guard Illager entity, model, animation and AI."),
            Component.literal("A powerful illager guard trained at the Barracks.")
        );
    }
    
    @Override
    public void spawnUnit(Level level, ProductionPlacement placement) {
        EntityType<?> entityType = resolveSourceEntityType();
        if (entityType == null) {
            GuardillagersRonIntegrationMod.LOGGER.error("Could not resolve source Guard Illager entity type {}");
            return;
        }
        produceUnit((net.minecraft.server.level.ServerLevel) level, entityType, placement.ownerName, true);
    }
    
    public static Entity produceUnit(net.minecraft.server.level.ServerLevel level, EntityType<?> entityType, String ownerName, boolean isMale) {
        Entity entity = entityType.create(level);
        if (entity != null) {
            // The Unit interface methods will be called by the entity mixin
            // Unit spawning is handled by RoN's produceUnit system
        }
        return entity;
    }
}