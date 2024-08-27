package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.loot.AddLootModifer;
import com.midasdaepik.remnantrelics.loot.AddMultipleLootModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class RRGlobalLootModifers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, RemnantRelics.MOD_ID);

    public static final Supplier<MapCodec<? extends  IGlobalLootModifier>> ADD_LOOT_MODIFER =
            LOOT_MODIFIER_SERIALIZERS.register("add", AddLootModifer.MAP_CODEC);

    public static final Supplier<MapCodec<? extends  IGlobalLootModifier>> ADD_MULTIPLE_LOOT_MODIFER =
            LOOT_MODIFIER_SERIALIZERS.register("add_multiple", AddMultipleLootModifier.MAP_CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
