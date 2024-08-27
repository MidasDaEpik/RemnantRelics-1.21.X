package com.midasdaepik.remnantrelics.event;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.RRItems;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = RemnantRelics.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent pEvent) {
        CauldronInteraction.WATER.map().put(RRItems.ELDER_CHESTPLATE.get(), CauldronInteraction.DYED_ITEM);
    }
}
