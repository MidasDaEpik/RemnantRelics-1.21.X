package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.registries.DataComponents;
import com.midasdaepik.remnantrelics.registries.EnumExtensions;
import com.midasdaepik.remnantrelics.registries.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class CatalystChalice extends Item {
    public CatalystChalice(Properties pProperties) {
        super(pProperties.stacksTo(1).rarity(EnumExtensions.RARITY_SCULK.getValue()).component(DataComponents.EXPERIENCE.get(), 0).component(DataComponents.MAXIMUM_EXPERIENCE.get(), 1395).component(DataComponents.CHALICE_STATE.get(), true));
    }

    @Override
    public int getUseDuration(ItemStack pItemStack, LivingEntity pEntity) {
        return 12000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pItemStack) {
        if (pItemStack.getOrDefault(DataComponents.CHALICE_STATE, true)) {
            return UseAnim.BOW;
        } else {
            return UseAnim.DRINK;
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pItemStack, Level pLevel, LivingEntity pLivingEntity) {
        this.releaseUsing(pItemStack, pLevel, pLivingEntity, 0);
        return pItemStack;
    }

    @Override
    public void onStopUsing(ItemStack pItemStack, LivingEntity pLivingEntity, int pTimeLeft) {
        this.releaseUsing(pItemStack, pLivingEntity.level(), pLivingEntity, pTimeLeft);
    }

    @Override
    public void releaseUsing(ItemStack pItemStack, Level pLevel, LivingEntity pLivingEntity, int pTimeLeft) {
        if (pLivingEntity instanceof Player pPlayer) {
            pPlayer.getCooldowns().addCooldown(this, 10);
        }
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pItemStack, int pTimeLeft) {
        int pTimeUsing = this.getUseDuration(pItemStack, pLivingEntity) - pTimeLeft;

        if (pLivingEntity instanceof Player pPlayer) {
            int ItemExperience = pItemStack.getOrDefault(DataComponents.EXPERIENCE, 0.0).intValue();
            int ItemMaxExperience = pItemStack.getOrDefault(DataComponents.MAXIMUM_EXPERIENCE, 0.0).intValue();
            boolean ItemAssimilationState = pItemStack.getOrDefault(DataComponents.CHALICE_STATE, true);
            int PlayerExperience = ItemUtil.getPlayerXP(pPlayer);
            int XPDrainPercent = this.XPModifyPercent(pPlayer);

            if (ItemAssimilationState) {
                if (PlayerExperience >= XPDrainPercent) {
                    if (ItemExperience + XPDrainPercent > ItemMaxExperience) {
                        int XPDrain = ItemMaxExperience - ItemExperience;
                        ItemUtil.modifyPlayerXP(pPlayer, XPDrain * -1);
                        pItemStack.set(DataComponents.EXPERIENCE, ItemExperience + XPDrain);
                    } else {
                        ItemUtil.modifyPlayerXP(pPlayer, XPDrainPercent * -1);
                        pItemStack.set(DataComponents.EXPERIENCE, ItemExperience + XPDrainPercent);
                    }

                } else if (PlayerExperience > 0) {
                    if (ItemExperience + PlayerExperience > ItemMaxExperience) {
                        int XPDrain = ItemMaxExperience - ItemExperience;
                        ItemUtil.modifyPlayerXP(pPlayer, XPDrain * -1);
                        pItemStack.set(DataComponents.EXPERIENCE, ItemExperience + XPDrain);
                    } else {
                        ItemUtil.modifyPlayerXP(pPlayer, PlayerExperience * -1);
                        pItemStack.set(DataComponents.EXPERIENCE, ItemExperience + PlayerExperience);
                    }

                } else {
                    pPlayer.getCooldowns().addCooldown(this, 10);
                    pPlayer.stopUsingItem();
                }
            } else {
                if (ItemExperience >= XPDrainPercent) {
                    pItemStack.set(DataComponents.EXPERIENCE, ItemExperience - XPDrainPercent);
                    ItemUtil.modifyPlayerXP(pPlayer, XPDrainPercent);

                } else if (ItemExperience > 0) {
                    pItemStack.set(DataComponents.EXPERIENCE, 0);
                    ItemUtil.modifyPlayerXP(pPlayer, ItemExperience);

                } else {
                    pPlayer.getCooldowns().addCooldown(this, 10);
                    pPlayer.stopUsingItem();
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack pItemStack = pPlayer.getItemInHand(pHand);
        int ItemExperience = pItemStack.getOrDefault(DataComponents.EXPERIENCE, 0.0).intValue();
        boolean ItemAssimilationState = pItemStack.getOrDefault(DataComponents.CHALICE_STATE, true);
        int PlayerExperience = ItemUtil.getPlayerXP(pPlayer);
        int XPDrainPercent = this.XPModifyPercent(pPlayer);

        if (pPlayer.isCrouching()) {
            if (ItemAssimilationState) {
                pItemStack.set(DataComponents.CHALICE_STATE, false);
            } else {
                pItemStack.set(DataComponents.CHALICE_STATE, true);
            }
            pPlayer.getCooldowns().addCooldown(this, 10);
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pHand));

        } else if (ItemAssimilationState) {
            if (PlayerExperience >= XPDrainPercent) {
                pPlayer.startUsingItem(pHand);
                return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));

            } else if (PlayerExperience > 0) {
                pPlayer.startUsingItem(pHand);
                return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
            }
        } else {
            if (ItemExperience >= XPDrainPercent) {
                pPlayer.startUsingItem(pHand);
                return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));

            } else if (ItemExperience > 0) {
                pPlayer.startUsingItem(pHand);
                return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
            }
        }

        return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
    }

    private int XPModifyPercent(Player pPlayer) {
        return Mth.ceil((float) (ItemUtil.getExperienceForLevel(pPlayer.experienceLevel + 1) - ItemUtil.getExperienceForLevel(pPlayer.experienceLevel)) / 8);
    }

    @Override
    public void appendHoverText(ItemStack pItemStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (ItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.shift_desc_3"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.shift_desc_4"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.shift_desc_5"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.lore_desc_1", pItemStack.getOrDefault(DataComponents.CHALICE_STATE, true) ? "§cAssimilation" : "§aDischarge"));
            if (ItemUtil.ItemKeys.isHoldingSpace()) {
                pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.lore_desc_2_levels", "§a" + ItemUtil.getLevelForExperience(pItemStack.getOrDefault(DataComponents.EXPERIENCE, 0.0).intValue()), "§a" + ItemUtil.getLevelForExperience(pItemStack.getOrDefault(DataComponents.MAXIMUM_EXPERIENCE, 0.0).intValue())));
            } else {
                pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.lore_desc_2", "§a" + pItemStack.getOrDefault(DataComponents.EXPERIENCE, 0.0).intValue(), "§a" + pItemStack.getOrDefault(DataComponents.MAXIMUM_EXPERIENCE, 0.0).intValue()));
                pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.lore_desc_2_info"));
            }
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        if (pItemStack.isEnchanted()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
        }
        super.appendHoverText(pItemStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
