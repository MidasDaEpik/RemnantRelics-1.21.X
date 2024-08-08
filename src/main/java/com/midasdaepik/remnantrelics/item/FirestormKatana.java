package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.entity.custom.Firestorm;
import com.midasdaepik.remnantrelics.registries.EnumExtensions;
import com.midasdaepik.remnantrelics.registries.ItemUtil;
import com.midasdaepik.remnantrelics.registries.Items;
import com.midasdaepik.remnantrelics.registries.Sounds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FirestormKatana extends SwordItem {
    public FirestormKatana(Properties pProperties) {
        super(new Tier() {
            public int getUses() {
                return 1366;
            }

            public float getSpeed() {
                return 8f;
            }

            public float getAttackDamageBonus() {
                return 5f;
            }

            public TagKey<Block> getIncorrectBlocksForDrops() {
                return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
            }

            public int getEnchantmentValue() {
                return 12;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(net.minecraft.world.item.Items.BLAZE_ROD);
            }
        }, pProperties.fireResistant().attributes(FirestormKatana.createAttributes()).rarity(EnumExtensions.RARITY_BLAZE.getValue()));
    }

    public static @NotNull ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID,  5, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID,  -2.6, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "entity_interaction_range"), 0.5, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public boolean hurtEnemy(ItemStack pItemStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pAttacker instanceof Player pPlayer) {
            if (pPlayer.getAttackStrengthScale(0) >= 0.9F) {
                pTarget.setRemainingFireTicks(EnchantmentHelper.getEnchantmentLevel(this.getEnchantmentHolder(Enchantments.FIRE_ASPECT, pAttacker.level()), pAttacker) * 3 + 4);
            }
        } else {
            pTarget.setRemainingFireTicks(EnchantmentHelper.getEnchantmentLevel(this.getEnchantmentHolder(ResourceLocation.withDefaultNamespace("fire_aspect"), pAttacker.level()), pAttacker) * 3 + 4);
        }

        return super.hurtEnemy(pItemStack, pTarget, pAttacker);
    }

    public static Holder<Enchantment> getEnchantmentHolder(ResourceKey<Enchantment> pEnchantment, Level level) {
        return level.registryAccess().registry(Registries.ENCHANTMENT).get().getHolder(pEnchantment).orElse(null);
    }

    public static Holder<Enchantment> getEnchantmentHolder(ResourceLocation pEnchantment, Level level) {
        return level.registryAccess().registry(Registries.ENCHANTMENT).get().getHolder(pEnchantment).orElse(null);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            Firestorm firestorm = new Firestorm(pLevel, pPlayer, 300, 40, false);
            firestorm.setPos(pPlayer.getEyePosition().x, pPlayer.getEyePosition().y, pPlayer.getEyePosition().z);
            firestorm.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.2f, 0.5f, 0.1f);
            pLevel.addFreshEntity(firestorm);
        }

        pPlayer.level().playSeededSound(null, pPlayer.getEyePosition().x, pPlayer.getEyePosition().y, pPlayer.getEyePosition().z, Sounds.ITEM_FIRESTORM_KATANA_ABILITY.get(), SoundSource.PLAYERS, 1f, 1f,0);

        pPlayer.getItemInHand(pUsedHand).hurtAndBreak(4, pPlayer, pUsedHand == net.minecraft.world.InteractionHand.MAIN_HAND ? net.minecraft.world.entity.EquipmentSlot.MAINHAND : net.minecraft.world.entity.EquipmentSlot.OFFHAND);

        pPlayer.awardStat(Stats.ITEM_USED.get(this));

        pPlayer.getCooldowns().addCooldown(this, 400);
        pPlayer.getCooldowns().addCooldown(Items.CREEPING_CRIMSON.get(), 400);
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pItemStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (ItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.firestorm_katana.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.firestorm_katana.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.firestorm_katana.shift_desc_3"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.firestorm_katana.shift_desc_4"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        if (pItemStack.isEnchanted()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
        }
        super.appendHoverText(pItemStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
