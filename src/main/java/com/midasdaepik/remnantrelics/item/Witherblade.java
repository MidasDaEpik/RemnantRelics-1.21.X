package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.registries.EnumExtensions;
import com.midasdaepik.remnantrelics.registries.ItemUtil;
import com.midasdaepik.remnantrelics.registries.Sounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Witherblade extends SwordItem {
    public Witherblade(Properties pProperties) {
        super(new Tier() {
            public int getUses() {
                return 1777;
            }

            public float getSpeed() {
                return 9f;
            }

            public float getAttackDamageBonus() {
                return 7f;
            }

            public TagKey<Block> getIncorrectBlocksForDrops() {
                return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(net.minecraft.world.item.Items.NETHERITE_SCRAP);
            }
        }, pProperties.fireResistant().attributes(Witherblade.createAttributes()).rarity(EnumExtensions.RARITY_WITHERBLADE.getValue()));
    }

    public static @NotNull ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID,  7, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID,  -2.6, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean hurtEnemy(ItemStack pItemstack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pAttacker instanceof Player pPlayer) {
            if (pPlayer.getAttackStrengthScale(0) >= 0.9F) {
                if (!pAttacker.level().isClientSide() && Mth.nextInt(RandomSource.create(), 1, 10) == 1) {
                    pTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 160, 1, false, true));
                    pTarget.level().playSeededSound(null, pTarget.getEyePosition().x, pTarget.getEyePosition().y, pTarget.getEyePosition().z, Sounds.ITEM_WITHERBLADE_WITHER.get(), SoundSource.PLAYERS, 1f, 1f,0);
                }
            }
        } else {
            if (!pAttacker.level().isClientSide() && Mth.nextInt(RandomSource.create(), 1, 10) == 1) {
                pTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 160, 1, false, true));
                pTarget.level().playSeededSound(null, pTarget.getEyePosition().x, pTarget.getEyePosition().y, pTarget.getEyePosition().z, Sounds.ITEM_WITHERBLADE_WITHER.get(), SoundSource.HOSTILE, 1f, 1f,0);
            }
        }

        return super.hurtEnemy(pItemstack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack pItemstack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (ItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.witherblade.shift_desc_1"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        if (pItemstack.isEnchanted()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
        }
        super.appendHoverText(pItemstack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
