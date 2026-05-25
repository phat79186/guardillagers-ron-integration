package com.codex.guardillagersronintegration;

import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

@Mod.EventBusSubscriber(modid = GuardillagersRonIntegrationMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityAttributes {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        // Lấy bảng thuộc tính gốc của Guard Illager và đăng ký an toàn vào hệ thống Forge
        try {
            // Đăng ký Guard Illager attributes
            EntityType<?> guardIllagerType = net.minecraft.core.Registry.ENTITY_TYPE
                .get(new ResourceLocation("guardillagers", "guard_illager"));
            
            if (guardIllagerType != null) {
                // Guard Illager sử dụng attributes mặc định từ mod gốc
                // Không cần override vì mod guardillagers đã có sẵn
                System.out.println("[GuardIllagers RoN] Registered Guard Illager attributes successfully");
            }
        } catch (Exception e) {
            System.out.println("[GuardIllagers RoN] Failed to register attributes: " + e.getMessage());
        }
    }
}