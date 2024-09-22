package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.entity.Firestorm;
import com.midasdaepik.remnantrelics.registries.RREnumExtensions;
import com.midasdaepik.remnantrelics.registries.RRItemUtil;
import com.midasdaepik.remnantrelics.registries.RRItems;
import com.midasdaepik.remnantrelics.registries.RRSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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

public class CreepingCrimson extends SwordItem {
    public CreepingCrimson(Properties pProperties) {
        super(new Tier() {
            public int getUses() {
                return 1777;
            }

            public float getSpeed() {
                return 9f;
            }

            public float getAttackDamageBonus() {
                return 7.5f;
            }

            public TagKey<Block> getIncorrectBlocksForDrops() {
                return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
            }

            public int getEnchantmentValue() {
                return 16;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(net.minecraft.world.item.Items.NETHERITE_SCRAP);
            }
        }, pProperties.fireResistant().attributes(CreepingCrimson.createAttributes()).rarity(RREnumExtensions.RARITY_CREEPING_CRIMSON.getValue()));
    }

    public static @NotNull ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID,  7.5, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID,  -2.6, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "entity_interaction_range"), 1, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean hurtEnemy(ItemStack pItemStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pAttacker instanceof Player pPlayer) {
            if (!pPlayer.getCooldowns().isOnCooldown(this) && pPlayer.getAttackStrengthScale(0) >= 0.9F) {
                if (!pAttacker.level().isClientSide() && Mth.nextInt(RandomSource.create(), 1, 8) == 1) {
                    Firestorm firestorm = new Firestorm(pAttacker.level(), pAttacker, 160, 20, true);
                    firestorm.setPos(pAttacker.getEyePosition().x, pAttacker.getEyePosition().y, pAttacker.getEyePosition().z);
                    pAttacker.level().addFreshEntity(firestorm);

                    pAttacker.level().playSeededSound(null, pAttacker.getEyePosition().x, pAttacker.getEyePosition().y, pAttacker.getEyePosition().z, RRSounds.ITEM_WITHERBLADE_CLOUD.get(), SoundSource.PLAYERS, 1f, 1f,0);

                    pPlayer.getCooldowns().addCooldown(this, 160);
                    pPlayer.getCooldowns().addCooldown(RRItems.FIRESTORM_KATANA.get(), 160);
                }
            }
        } else {
            if (!pAttacker.level().isClientSide() && Mth.nextInt(RandomSource.create(), 1, 8) == 1) {
                Firestorm firestorm = new Firestorm(pAttacker.level(), pAttacker, 160, 20, true);
                firestorm.setPos(pAttacker.getEyePosition().x, pAttacker.getEyePosition().y, pAttacker.getEyePosition().z);
                pAttacker.level().addFreshEntity(firestorm);

                pAttacker.level().playSeededSound(null, pAttacker.getEyePosition().x, pAttacker.getEyePosition().y, pAttacker.getEyePosition().z, RRSounds.ITEM_WITHERBLADE_CLOUD.get(), SoundSource.HOSTILE, 1f, 1f,0);
            }
        }

        return super.hurtEnemy(pItemStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack pItemStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (RRItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.creeping_crimson.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.creeping_crimson.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.creeping_crimson.shift_desc_3"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        if (pItemStack.isEnchanted()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
        }
        super.appendHoverText(pItemStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
