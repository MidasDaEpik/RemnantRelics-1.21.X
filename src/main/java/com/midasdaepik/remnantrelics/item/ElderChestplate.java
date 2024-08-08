package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.client.model.ElderChestplateModel;
import com.midasdaepik.remnantrelics.client.model.ElderChestplateRetractedModel;
import com.midasdaepik.remnantrelics.registries.ArmorMaterials;
import com.midasdaepik.remnantrelics.registries.EnumExtensions;
import com.midasdaepik.remnantrelics.registries.ItemUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ElderChestplate extends ArmorItem {
    public ElderChestplate(Properties pProperties) {
        super(ArmorMaterials.ELDER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, pProperties.durability(ArmorItem.Type.CHESTPLATE.getDurability(32)).attributes(ElderChestplate.createAttributes()).rarity(EnumExtensions.RARITY_ELDER.getValue()));
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
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            @OnlyIn(Dist.CLIENT)
            public HumanoidModel getHumanoidArmorModel(LivingEntity pLivingEntity, ItemStack pItemStack, EquipmentSlot pEquipmentSlot, HumanoidModel pDefaultModel) {
                if (pLivingEntity instanceof Player pPlayer && pPlayer.getCooldowns().isOnCooldown(pItemStack.getItem())) {
                    HumanoidModel pArmorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of(
                            "body", new ElderChestplateRetractedModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateRetractedModel.LAYER_LOCATION)).Body,
                            "left_arm", new ElderChestplateRetractedModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateRetractedModel.LAYER_LOCATION)).LeftArm,
                            "right_arm", new ElderChestplateRetractedModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateRetractedModel.LAYER_LOCATION)).RightArm,
                            "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                            "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                            "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                            "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
                    )));
                    pArmorModel.crouching = pDefaultModel.crouching;
                    pArmorModel.riding = pDefaultModel.riding;
                    pArmorModel.swimAmount = pDefaultModel.swimAmount;
                    pArmorModel.young = pDefaultModel.young;
                    return pArmorModel;
                } else {
                    HumanoidModel pArmorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of(
                            "body", new ElderChestplateModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateModel.LAYER_LOCATION)).Body,
                            "left_arm", new ElderChestplateModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateModel.LAYER_LOCATION)).LeftArm,
                            "right_arm", new ElderChestplateModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateModel.LAYER_LOCATION)).RightArm,
                            "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                            "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                            "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                            "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
                    )));
                    pArmorModel.crouching = pLivingEntity.isShiftKeyDown();
                    pArmorModel.riding = pDefaultModel.riding;
                    pArmorModel.young = pLivingEntity.isBaby();
                    pArmorModel.swimAmount = pDefaultModel.swimAmount;
                    return pArmorModel;
                }
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack pItemStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (ItemUtil.ItemKeys.isHoldingShift()) {
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
