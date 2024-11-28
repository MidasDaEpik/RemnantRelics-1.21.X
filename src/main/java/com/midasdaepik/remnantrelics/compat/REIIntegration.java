package com.midasdaepik.remnantrelics.compat;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.RRItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.client.BuiltinClientPlugin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

@REIPluginClient
public class REIIntegration implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry pDisplayRegistry) {
        addInfo(RRItems.ELDER_SPINE.get());

        addInfo(RRItems.PIGLIN_WARAXE.get());

        addInfo(RRItems.FIRESTORM_KATANA.get());
        addInfo(RRItems.SEARING_STAFF.get());
        addInfo(RRItems.WITHERBLADE_UPGRADE_SMITHING_TEMPLATE.get());

        addInfo(RRItems.WITHERBLADE.get());
        addInfo(RRItems.REFINED_WITHERBLADE.get());

        addInfo(RRItems.CREEPING_CRIMSON.get());
        addInfo(RRItems.SOULEATING_SLASHER.get());
        addInfo(RRItems.WARPING_WITHER.get());

        addInfo(RRItems.CATALYST_CHALICE.get());
        addInfo(RRItems.SCYLLA.get());
        addInfo(RRItems.LYRE_OF_ECHOES.get());
    }

    private static void addInfo(Item pItem) {
        BuiltinClientPlugin.getInstance().registerInformation(
                EntryStacks.of(pItem),
                Component.translatable(BuiltInRegistries.ITEM.getKey(pItem).toString()),
                (text) -> {
                    text.add(Component.translatable("hint." + RemnantRelics.MOD_ID + "." + BuiltInRegistries.ITEM.getKey(pItem).getPath()));
                    return text;
                });
    }
}
