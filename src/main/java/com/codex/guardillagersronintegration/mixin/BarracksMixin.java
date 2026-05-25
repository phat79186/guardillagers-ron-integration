package com.codex.guardillagersronintegration.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.EntityType;
import java.util.List;

@Mixin(targets = "com.solegendary.reignofnether.block.entity.BarracksBlockEntity")
public class BarracksMixin {

    @Shadow
    private List<EntityType<?>> productions;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectGuardIllagerToBarracks(CallbackInfo ci) {
        try {
            EntityType<?> guardIllager = net.minecraft.core.Registry.ENTITY_TYPE
                .get(new ResourceLocation("guardillagers", "guard_illager"));
            
            if (guardIllager != null && this.productions != null && !this.productions.contains(guardIllager)) {
                this.productions.add(guardIllager);
                System.out.println("[GuardIllagers RoN] Successfully added Guard Illager to Barracks");
            }
        } catch (Exception e) {
            System.out.println("[GuardIllagers RoN] Failed to inject Guard Illager to Barracks: " + e.getMessage());
        }
    }
}
