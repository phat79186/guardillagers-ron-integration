package com.codex.guardillagersronintegration.mixin;

import com.codex.guardillagersronintegration.GuardillagersRonIntegrationValues;
import com.solegendary.reignofnether.registrars.EntityRegistrar;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRegistrar.class)
public class EntityRegistrarMixin {
    
    @Inject(method = "getEntityType", at = @At("HEAD"), cancellable = true)
    private static void guardillagersIntegration$mapGuardIllager(String unitName, CallbackInfoReturnable<EntityType<?>> cir) {
        if (unitName != null && unitName.equals("Guard Illager")) {
            EntityType<?> entityType = (EntityType<?>) GuardillagersRonIntegrationValues.resolveSourceEntityType();
            if (entityType != null) {
                cir.setReturnValue(entityType);
            }
        }
    }
}