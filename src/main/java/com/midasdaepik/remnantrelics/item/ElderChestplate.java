package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.RRArmorMaterials;
import com.midasdaepik.remnantrelics.registries.RREnumExtensions;
import com.midasdaepik.remnantrelics.registries.RRItemUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElderChestplate extends ArmorItem {
    public ElderChestplate(Properties pProperties) {
        super(RRArmorMaterials.ELDER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, pProperties.durability(ArmorItem.Type.CHESTPLATE.getDurability(35)).attributes(ElderChestplate.createAttributes()).rarity(RREnumExtensions.RARITY_ELDER.getValue()));
    }

    public static @NotNull ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ARMOR,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "armor"),  6, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.CHEST)
                .add(Attributes.ARMOR_TOUGHNESS,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "armor_toughness"),  1.5, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.CHEST)
                .add(Attributes.MINING_EFFICIENCY,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "mining_speed"),  6, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.CHEST)
                .add(Attributes.SUBMERGED_MINING_SPEED,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "submerged_mining_speed"),  2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                        EquipmentSlotGroup.CHEST)
                .build();
    }

    @Override
    public void appendHoverText(ItemStack pItemStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (RRItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.elder_chestplate.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.elder_chestplate.shift_desc_2"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        ArmorTrim pComponent = pItemStack.get(DataComponents.TRIM);
        if (pItemStack.isEnchanted() || (pComponent != null && pComponent.pattern().isBound())) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
        }
        super.appendHoverText(pItemStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
