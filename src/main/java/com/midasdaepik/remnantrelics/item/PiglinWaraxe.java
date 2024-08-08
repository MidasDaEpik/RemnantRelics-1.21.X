package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.Effects;
import com.midasdaepik.remnantrelics.registries.EnumExtensions;
import com.midasdaepik.remnantrelics.registries.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PiglinWaraxe extends AxeItem {
    public PiglinWaraxe(Properties pProperties) {
        super(new Tier() {
            public int getUses() {
                return 760;
            }

            public float getSpeed() {
                return 10f;
            }

            public float getAttackDamageBonus() {
                return 12f;
            }

            public TagKey<Block> getIncorrectBlocksForDrops() {
                return BlockTags.INCORRECT_FOR_GOLD_TOOL;
            }

            public int getEnchantmentValue() {
                return 19;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(Items.GOLD_BLOCK);
            }
        }, pProperties.attributes(PiglinWaraxe.createAttributes()).rarity(EnumExtensions.RARITY_GOLD.getValue()));
    }

    public static @NotNull ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID,  12, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID,  -3, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.KNOCKBACK_RESISTANCE,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "knockback_resistance"),  0.4, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public void inventoryTick(ItemStack pItemStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof LivingEntity pLivingEntity && pIsSelected) {
            if (!pLivingEntity.getOffhandItem().isEmpty()) {
                pLivingEntity.addEffect(new MobEffectInstance(Effects.UNWIELDY, 1, 0, true, false, false));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pItemStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (ItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.two_handed"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        if (pItemStack.isEnchanted()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
        }
        super.appendHoverText(pItemStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
