package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.neoforged.neoforge.registries.DeferredItem;

public class RRTrimPatterns {
    public static final ResourceKey<TrimPattern> ATROPHY = ResourceKey.create(Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "atrophy"));

    public static final ResourceKey<TrimPattern> TYRANT = ResourceKey.create(Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "tyrant"));

    public static void bootstrap(BootstrapContext<TrimPattern> context) {
        register(context, RRItems.ATROPHY_ARMOR_TRIM_SMITHING_TEMPLATE, ATROPHY);
        register(context, RRItems.TYRANT_ARMOR_TRIM_SMITHING_TEMPLATE, TYRANT);
    }

    public static void register(BootstrapContext<TrimPattern> context, DeferredItem<Item> item, ResourceKey<TrimPattern> key) {
        TrimPattern trimpattern = new TrimPattern(key.location(), item.getDelegate(),
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())), false);
        context.register(key, trimpattern);
    }
}
