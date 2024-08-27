package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RRSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, RemnantRelics.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_FIRESTORM_KATANA_ABILITY = SOUND_EVENTS.register("item_firestorm_katana_ability",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "item_firestorm_katana_ability"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_WARPED_RAPIER_ABILITY = SOUND_EVENTS.register("item_warped_rapier_ability",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "item_warped_rapier_ability"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_WITHERBLADE_WITHER = SOUND_EVENTS.register("item_witherblade_wither",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "item_witherblade_wither"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_WITHERBLADE_AURA = SOUND_EVENTS.register("item_witherblade_aura",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "item_witherblade_aura"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_WITHERBLADE_ABSORPTION = SOUND_EVENTS.register("item_witherblade_absorption",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "item_witherblade_absorption"))
    );
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_WITHERBLADE_TELEPORT = SOUND_EVENTS.register("item_witherblade_teleport",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "item_witherblade_teleport"))
    );

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
