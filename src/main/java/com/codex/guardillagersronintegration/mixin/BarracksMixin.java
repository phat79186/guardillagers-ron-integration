package com.codex.guardillagersronintegration.mixin;

import com.codex.guardillagersronintegration.ron.GuardIllagerProductionItems;
import com.solegendary.reignofnether.building.buildings.villagers.Barracks;
import com.solegendary.reignofnether.building.production.ProductionItemList;
import com.solegendary.reignofnether.keybinds.Keybindings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Barracks.class)
public class BarracksMixin {
    
    @Inject(method = "giveProductionItems", at = @At("RETURN"))
    private static void guardillagersIntegration$addProduction(CallbackInfo ci) {
        ProductionItemList.addProductionItem(GuardIllagerProductionItems.GUARD_ILLAGER, Keybindings.G);
    }
}