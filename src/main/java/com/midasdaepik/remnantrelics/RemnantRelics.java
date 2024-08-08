package com.midasdaepik.remnantrelics;

import com.midasdaepik.remnantrelics.registries.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(RemnantRelics.MOD_ID)
public class RemnantRelics {
    public static final String MOD_ID = "remnantrelics";
    private static final Logger LOGGER = LoggerFactory.getLogger(RemnantRelics.class);

    public RemnantRelics(IEventBus eventBus) {
        ArmorMaterials.register(eventBus);
        CreativeTabs.register(eventBus);
        DataComponents.register(eventBus);
        Effects.register(eventBus);
        Entities.register(eventBus);
        GlobalLootModifers.register(eventBus);
        Items.register(eventBus);
        Sounds.register(eventBus);

        eventBus.addListener(FMLClientSetupEvent.class, (fmlClientSetupEvent -> {
            fmlClientSetupEvent.enqueueWork(() -> {
                ModList.get().getModContainerById(MOD_ID).ifPresent(modContainer -> {
                    LOGGER.info("Using Remnant Relics {}", modContainer.getModInfo().getVersion());
                });
            });
        }));
    }
}
