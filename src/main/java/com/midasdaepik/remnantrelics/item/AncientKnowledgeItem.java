package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.EnumExtensions;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
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
        super(pProperties.stacksTo(1));
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
                AdvancementHolder pAdvancementHolder = pAdvancementManager.get(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "nether/ancient_tablet_forging"));
                if (pAdvancementHolder != null && !pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolder).isDone()) {
                    pServerPlayer.getAdvancements().award(pAdvancementHolder, "activate_item");
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    pPlayer.getItemInHand(pHand).consume(1, pPlayer);
                    pPlayer.getCooldowns().addCooldown(this, 40);
                    return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
                }
            } else if (this.KnowledgeTypeKey == 2) {
                AdvancementHolder pAdvancementHolder = pAdvancementManager.get(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "nether/ancient_tablet_infusion"));
                if (pAdvancementHolder != null && !pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolder).isDone()) {
                    pServerPlayer.getAdvancements().award(pAdvancementHolder, "activate_item");
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    pPlayer.getItemInHand(pHand).consume(1, pPlayer);
                    pPlayer.getCooldowns().addCooldown(this, 40);
                    return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
                }
            } else if (this.KnowledgeTypeKey == 3) {
                AdvancementHolder pAdvancementHolder = pAdvancementManager.get(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "nether/ancient_tablet_refining"));
                if (pAdvancementHolder != null && !pServerPlayer.getAdvancements().getOrStartProgress(pAdvancementHolder).isDone()) {
                    pServerPlayer.getAdvancements().award(pAdvancementHolder, "activate_item");
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    pPlayer.getItemInHand(pHand).consume(1, pPlayer);
                    pPlayer.getCooldowns().addCooldown(this, 40);
                    return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
                }
            }
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
    }

    @Override
    public void appendHoverText(ItemStack pItemStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (this.KnowledgeTypeKey == 1) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_forging.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_forging.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_forging.shift_desc_3"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_forging.shift_desc_4"));
        } else if (this.KnowledgeTypeKey == 2) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_infusion.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_infusion.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_infusion.shift_desc_3"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_infusion.shift_desc_4"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_infusion.shift_desc_5"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_infusion.shift_desc_6"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_infusion.shift_desc_7"));
        } else if (this.KnowledgeTypeKey == 3) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_refining.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_refining.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_refining.shift_desc_3"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.ancient_tablet_refining.shift_desc_4"));
        }
        super.appendHoverText(pItemStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
