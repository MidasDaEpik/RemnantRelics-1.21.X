package com.midasdaepik.remnantrelics.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class BlueprintScroll extends SmithingTemplateItem {
    private static final Component BLUEPRINT_SCROLL_WITHERBLADES = Component.translatable("item.remnantrelics.blueprint_scroll_witherblades_desc").withStyle(ChatFormatting.GRAY);
    private static final Component BLUEPRINT_SCROLL_WITHERBLADES_APPLIES_TO = Component.translatable("smithing_template.blueprint_scroll_witherblades.applies_to").withStyle(ChatFormatting.BLUE);
    private static final Component BLUEPRINT_SCROLL_WITHERBLADES_INGREDIENTS = Component.translatable("smithing_template.blueprint_scroll_witherblades.ingredients").withStyle(ChatFormatting.BLUE);
    private static final Component BLUEPRINT_SCROLL_WITHERBLADES_BASE_SLOT_DESCRIPTION = Component.translatable("smithing_template.blueprint_scroll_witherblades.base_slot_description");
    private static final Component BLUEPRINT_SCROLL_WITHERBLADES_ADDITIONS_SLOT_DESCRIPTION = Component.translatable("smithing_template.blueprint_scroll_witherblades.additions_slot_description");

    private static final Component BLUEPRINT_SCROLL_ELDER = Component.translatable("item.remnantrelics.blueprint_scroll_elder_desc").withStyle(ChatFormatting.GRAY);
    private static final Component BLUEPRINT_SCROLL_ELDER_APPLIES_TO = Component.translatable("smithing_template.blueprint_scroll_elder.applies_to").withStyle(ChatFormatting.BLUE);
    private static final Component BLUEPRINT_SCROLL_ELDER_INGREDIENTS = Component.translatable("smithing_template.blueprint_scroll_elder.ingredients").withStyle(ChatFormatting.BLUE);
    private static final Component BLUEPRINT_SCROLL_ELDER_BASE_SLOT_DESCRIPTION = Component.translatable("smithing_template.blueprint_scroll_elder.base_slot_description");
    private static final Component BLUEPRINT_SCROLL_ELDER_ADDITIONS_SLOT_DESCRIPTION = Component.translatable("smithing_template.blueprint_scroll_elder.additions_slot_description");

    private static final ResourceLocation EMPTY_SLOT_HELMET = ResourceLocation.parse("item/empty_armor_slot_helmet");
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = ResourceLocation.parse("item/empty_armor_slot_chestplate");
    private static final ResourceLocation EMPTY_SLOT_LEGGINGS = ResourceLocation.parse("item/empty_armor_slot_leggings");
    private static final ResourceLocation EMPTY_SLOT_BOOTS = ResourceLocation.parse("item/empty_armor_slot_boots");
    private static final ResourceLocation EMPTY_SLOT_HOE = ResourceLocation.parse("item/empty_slot_hoe");
    private static final ResourceLocation EMPTY_SLOT_AXE = ResourceLocation.parse("item/empty_slot_axe");
    private static final ResourceLocation EMPTY_SLOT_SWORD = ResourceLocation.parse("item/empty_slot_sword");
    private static final ResourceLocation EMPTY_SLOT_SHOVEL = ResourceLocation.parse("item/empty_slot_shovel");
    private static final ResourceLocation EMPTY_SLOT_PICKAXE = ResourceLocation.parse("item/empty_slot_pickaxe");

    private static final ResourceLocation EMPTY_SLOT_INGOT = ResourceLocation.parse("item/empty_slot_ingot");
    private static final ResourceLocation EMPTY_SLOT_REDSTONE_DUST = ResourceLocation.parse("item/empty_slot_redstone_dust");
    private static final ResourceLocation EMPTY_SLOT_QUARTZ = ResourceLocation.parse("item/empty_slot_quartz");
    private static final ResourceLocation EMPTY_SLOT_EMERALD = ResourceLocation.parse("item/empty_slot_emerald");
    private static final ResourceLocation EMPTY_SLOT_DIAMOND = ResourceLocation.parse("item/empty_slot_diamond");
    private static final ResourceLocation EMPTY_SLOT_LAPIS_LAZULI = ResourceLocation.parse("item/empty_slot_lapis_lazuli");
    private static final ResourceLocation EMPTY_SLOT_AMETHYST_SHARD = ResourceLocation.parse("item/empty_slot_amethyst_shard");

    private static final ResourceLocation EMPTY_SLOT_NETHER_STAR = ResourceLocation.parse("remnantrelics:item/empty_slot_nether_star");
    private static final ResourceLocation EMPTY_SLOT_ELDER_SPINE = ResourceLocation.parse("remnantrelics:item/empty_slot_elder_spine");

    public BlueprintScroll(Component pAppliesTo, Component pIngredients, Component pUpgradeDescription, Component pBaseSlotDescription, Component pAdditionsSlotDescription, List<ResourceLocation> pBaseSlotEmptyIcons, List<ResourceLocation> pAdditonalSlotEmptyIcons) {
        super(pAppliesTo, pIngredients, pUpgradeDescription, pBaseSlotDescription, pAdditionsSlotDescription, pBaseSlotEmptyIcons, pAdditonalSlotEmptyIcons);
    }

    public static BlueprintScroll createScrollWitherblades() {
        return new BlueprintScroll(BLUEPRINT_SCROLL_WITHERBLADES_APPLIES_TO, BLUEPRINT_SCROLL_WITHERBLADES_INGREDIENTS, BLUEPRINT_SCROLL_WITHERBLADES, BLUEPRINT_SCROLL_WITHERBLADES_BASE_SLOT_DESCRIPTION, BLUEPRINT_SCROLL_WITHERBLADES_ADDITIONS_SLOT_DESCRIPTION, createBlueprintScrollWitherbladesIconList(), createBlueprintScrollWitherbladesMaterialList());
    }

    private static List<ResourceLocation> createBlueprintScrollWitherbladesIconList() {
        return List.of(EMPTY_SLOT_SWORD);
    }

    private static List<ResourceLocation> createBlueprintScrollWitherbladesMaterialList() {
        return List.of(EMPTY_SLOT_NETHER_STAR);
    }

    public static BlueprintScroll createScrollElder() {
        return new BlueprintScroll(BLUEPRINT_SCROLL_ELDER_APPLIES_TO, BLUEPRINT_SCROLL_ELDER_INGREDIENTS, BLUEPRINT_SCROLL_ELDER, BLUEPRINT_SCROLL_ELDER_BASE_SLOT_DESCRIPTION, BLUEPRINT_SCROLL_ELDER_ADDITIONS_SLOT_DESCRIPTION, createBlueprintScrollElderIconList(), createBlueprintScrollElderMaterialList());
    }

    private static List<ResourceLocation> createBlueprintScrollElderIconList() {
        return List.of(EMPTY_SLOT_SWORD, EMPTY_SLOT_CHESTPLATE);
    }

    private static List<ResourceLocation> createBlueprintScrollElderMaterialList() {
        return List.of(EMPTY_SLOT_ELDER_SPINE);
    }

    @Override
    public String getDescriptionId() {
        return Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this));
    }
}
