package com.example;


import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;


@Mod(BlacksmithEpicPatch.MODID)
public class BlacksmithEpicPatch {
    public static final String MODID = "blacksmith_epic_patch";
    private static final Logger LOGGER = LogUtils.getLogger();
    
    // Namespace chính xác của mod Epic Knights theo tài liệu mã nguồn
    public static final String EPIC_KNIGHTS_ID = "magistuarmory";


    public BlacksmithEpicPatch() {
        // TUYỆT ĐỐI KHÔNG gọi bất kỳ logic tìm kiếm Item nào ở đây hoặc trong static block.
        // Giữ Constructor hoàn toàn sạch sẽ để bảo vệ Forge Registry Lifecycle.
        LOGGER.info("Blacksmith Epic Patch initialized successfully without early registry polling.");
    }


    /**
     * Hàm lấy Item an toàn từ Forge Registry tại thời điểm Runtime (Lazy Loading).
     * Tuyệt đối không lưu trữ kết quả vào biến static toàn cục.
     */
    public static Item getEpicKnightsItem(String id) {
        // Kiểm tra xem mod Epic Knights có được cài đặt hay không trước khi tìm kiếm
        if (!ModList.get().isLoaded(EPIC_KNIGHTS_ID)) {
            return null;
        }
        
        ResourceLocation location = new ResourceLocation(EPIC_KNIGHTS_ID, id);
        if (ForgeRegistries.ITEMS.containsKey(location)) {
            Item item = ForgeRegistries.ITEMS.getValue(location);
            if (item != null) {
                LOGGER.info("Loaded Epic Knights item: {}", location);
                return item;
            }
        }
        
        LOGGER.warn("Missing Epic Knights item: {}", location);
        return null;
    }


    /**
     * Hàm xử lý thay đổi trang bị cho Blacksmith. 
     * Gọi hàm này khi thực hiện sinh (spawn) hoặc cập nhật thực thể Blacksmith tại runtime.
     * 
     * @param entity Thực thể Blacksmith cần thay đổi đồ.
     * @param tier Cấp độ trang bị cần cấu hình ("leather" hoặc "iron").
     */
    public static void applyEpicArmorPatch(LivingEntity entity, String tier) {
        if (entity == null || entity.level().isClientSide()) {
            return;
        }


        // Tự động disable nhẹ nhàng nếu thiếu mod Epic Knights
        if (!ModList.get().isLoaded(EPIC_KNIGHTS_ID)) {
            return;
        }


        if ("leather".equalsIgnoreCase(tier)) {
            // Xác minh registry chính xác từ mã nguồn Magistu/Epic-Knights
            equipItemSafely(entity, EquipmentSlot.HEAD, "kettle_hat");
            equipItemSafely(entity, EquipmentSlot.CHEST, "reinforced_chainmail_armor");
            equipItemSafely(entity, EquipmentSlot.LEGS, "reinforced_chainmail_leggings");
            equipItemSafely(entity, EquipmentSlot.FEET, "reinforced_chainmail_boots");
            
        } else if ("iron".equalsIgnoreCase(tier)) {
            // Xác minh registry chính xác từ mã nguồn Magistu/Epic-Knights
            equipItemSafely(entity, EquipmentSlot.HEAD, "decorated_armet");
            equipItemSafely(entity, EquipmentSlot.CHEST, "knight_armor");
            equipItemSafely(entity, EquipmentSlot.LEGS, "knight_leggings");
            equipItemSafely(entity, EquipmentSlot.FEET, "knight_boots");
        }
    }


    /**
     * Hàm hỗ trợ mặc trang bị an toàn tuyệt đối, không gây crash nếu thiếu item hoặc đổi registry name
     */
    private static void equipItemSafely(LivingEntity entity, EquipmentSlot slot, String itemId) {
        Item targetItem = getEpicKnightsItem(itemId);
        if (targetItem != null) {
            entity.setItemSlot(slot, new ItemStack(targetItem));
        }
        // Nếu targetItem == null, game sẽ tự động bỏ qua (skip) trang bị đó và KHÔNG crash game.
    }
}