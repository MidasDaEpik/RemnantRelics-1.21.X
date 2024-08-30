package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class RRAttachmentTypes {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, RemnantRelics.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> TIME_SINCE_LAST_ATTACK = ATTACHMENT_TYPES.register(
            "time_since_last_attack", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    public static final Supplier<AttachmentType<Integer>> DRAGONS_RAGE_CHARGE = ATTACHMENT_TYPES.register(
            "dragons_rage_charge", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    public static final Supplier<AttachmentType<Integer>> SPECIAL_ARROW_TYPE = ATTACHMENT_TYPES.register(
            "special_arrow_type", () -> AttachmentType.builder(() -> -1).serialize(Codec.INT).build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
