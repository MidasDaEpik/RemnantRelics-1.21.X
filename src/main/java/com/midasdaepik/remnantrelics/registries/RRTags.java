package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class RRTags {
    public static void initTags() {}

    public static final TagKey<Item> DUAL_WIELDED_WEAPONS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "dual_wielded_weapons"));
    public static final TagKey<Item> HIDE_OFFHAND_WHILE_USING_ITEMS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "hide_offhand_while_using_items"));
}
