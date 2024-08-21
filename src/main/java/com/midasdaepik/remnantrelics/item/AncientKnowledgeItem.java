package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.DataComponents;
import com.midasdaepik.remnantrelics.registries.EnumExtensions;
import com.midasdaepik.remnantrelics.registries.ItemUtil;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AncientKnowledgeItem extends Item {
    private final Integer KnowledgeTypeKey;

    public AncientKnowledgeItem(Properties pProperties, Integer pKnowledgeTypeKey) {
        super(pProperties.stacksTo(64));
        this.KnowledgeTypeKey = pKnowledgeTypeKey;
    }

    public static AncientKnowledgeItem createAncientTabletForging() {
        return new AncientKnowledgeItem(new Properties().rarity(EnumExtensions.RARITY_WITHERBLADE.getValue()), 1);
    }

    public static AncientKnowledgeItem createAncientTabletInfusion() {
        return new AncientKnowledgeItem(new Properties().rarity(EnumExtensions.RARITY_BLAZE.getValue()), 2);
    }

    public static AncientKnowledgeItem createAncientTabletRefining() {
        return new AncientKnowledgeItem(new Properties().rarity(EnumExtensions.RARITY_GOLD.getValue()), 3);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (pPlayer instanceof ServerPlayer pServerPlayer) {
            ServerAdvancementManager pAdvancementManager = pServerPlayer.server.getAdvancements();
            if (this.KnowledgeTypeKey == 1) {
                AdvancementHolder pAdvancementHolder = pAdvancementManager.get(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "ancient_tablet_forging"));
                AdvancementHolder pAdvancementHolderFragment = pAdvancementManager.get(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "witherblades"));
                if (pAdvancementHolder != null && !pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolder).isDone()) {
                    pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolder).grantProgress("activate_item");
                    if (pAdvancementHolderFragment != null) {
                        pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolderFragment).grantProgress("ancient_tablet_forging");
                    }
                    pPlayer.getItemInHand(pHand).consume(1, pPlayer);
                    return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
                }
            } else if (this.KnowledgeTypeKey == 2) {
                AdvancementHolder pAdvancementHolder = pAdvancementManager.get(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "ancient_tablet_infusion"));
                AdvancementHolder pAdvancementHolderFragment = pAdvancementManager.get(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "witherblades"));
                if (pAdvancementHolder != null && !pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolder).isDone()) {
                    pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolder).grantProgress("activate_item");
                    if (pAdvancementHolderFragment != null) {
                        pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolderFragment).grantProgress("ancient_tablet_infusion");
                    }
                    pPlayer.getItemInHand(pHand).consume(1, pPlayer);
                    return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
                }
            } else if (this.KnowledgeTypeKey == 3) {
                AdvancementHolder pAdvancementHolder = pAdvancementManager.get(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "ancient_tablet_refining"));
                AdvancementHolder pAdvancementHolderFragment = pAdvancementManager.get(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "witherblades"));
                if (pAdvancementHolder != null && !pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolder).isDone()) {
                    pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolder).grantProgress("activate_item");
                    if (pAdvancementHolderFragment != null) {
                        pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolderFragment).grantProgress("ancient_tablet_refining");
                    }
                    pPlayer.getItemInHand(pHand).consume(1, pPlayer);
                    return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
                }
            }
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
    }

    @Override
    public void appendHoverText(ItemStack pItemStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (ItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.catalyst_chalice.shift_desc_3"));
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
        super.appendHoverText(pItemStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
