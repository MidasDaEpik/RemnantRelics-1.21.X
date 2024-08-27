package com.midasdaepik.remnantrelics.registries;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class RRItemProperties {
    public static void addCustomItemProperties() {
        ChaliceState(RRItems.CATALYST_CHALICE.get());
        Experience(RRItems.CATALYST_CHALICE.get());
        Pull(RRItems.CHARYBDIS.get());
        Pulling(RRItems.CHARYBDIS.get());
        Pull(RRItems.LYRE_OF_ECHOES.get());
        Pulling(RRItems.LYRE_OF_ECHOES.get());
        Pull(RRItems.WHISPERWIND.get());
        Pulling(RRItems.WHISPERWIND.get());
    }

    private static void Pull(Item pItem) {
        net.minecraft.client.renderer.item.ItemProperties.register(
                pItem,
                ResourceLocation.withDefaultNamespace("pull"),
                (pItemstack, pLevel, pLivingEntity, pSeed) -> {
                    if (pLivingEntity == null) {
                        return 0.0F;
                    } else {
                        return pLivingEntity.getUseItem() != pItemstack ? 0.0f : (float) (pItemstack.getUseDuration(pLivingEntity) - pLivingEntity.getUseItemRemainingTicks()) / 20.0f;
                    }
                }
        );
    }

    private static void Pulling(Item pItem) {
        net.minecraft.client.renderer.item.ItemProperties.register(
                pItem,
                ResourceLocation.withDefaultNamespace("pulling"),
                (pItemstack, pLevel, pLivingEntity, pSeed) -> pLivingEntity != null && pLivingEntity.isUsingItem() && pLivingEntity.getUseItem() == pItemstack ? 1.0f : 0.0f
        );
    }

    private static void Experience(Item pItem) {
        net.minecraft.client.renderer.item.ItemProperties.register(
                pItem,
                ResourceLocation.withDefaultNamespace("experience"),
                (pItemstack, pLevel, pLivingEntity, pSeed) -> {
                    if (pLivingEntity != null) {
                        return (float) pItemstack.getOrDefault(RRDataComponents.EXPERIENCE, 0.0).intValue() / pItemstack.getOrDefault(RRDataComponents.MAXIMUM_EXPERIENCE, 1.0).intValue();
                    } else {
                        return 0.0f;
                    }
                }
        );
    }

    private static void ChaliceState(Item pItem) {
        net.minecraft.client.renderer.item.ItemProperties.register(
                pItem,
                ResourceLocation.withDefaultNamespace("chalice_state"),
                (pItemstack, pLevel, pLivingEntity, pSeed) -> {
                    if (pLivingEntity != null) {
                        return pItemstack.getOrDefault(RRDataComponents.CHALICE_STATE, true) ? 1.0f : 0.0f;
                    } else {
                        return 1.0f;
                    }
                }
        );
    }
}
