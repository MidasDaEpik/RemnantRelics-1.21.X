package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.client.model.ElderChestplateModel;
import com.midasdaepik.remnantrelics.client.model.ElderChestplateRetractedModel;
import com.midasdaepik.remnantrelics.entity.renderer.FirestormRenderer;
import com.midasdaepik.remnantrelics.entity.renderer.SonicBlastRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = RemnantRelics.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientModEvents {
    @SubscribeEvent
    private static void clientSetup(FMLClientSetupEvent pEvent) {
        EntityRenderers.register(Entities.NO_DAMAGE_FIREBALL.get(), ThrownItemRenderer::new);

        ItemProperties.addCustomItemProperties();
    }

    @SubscribeEvent
    private static void entityRenderers(EntityRenderersEvent.RegisterRenderers pEvent) {
        pEvent.registerEntityRenderer(Entities.SONIC_BLAST.get(), SonicBlastRenderer::new);
        pEvent.registerEntityRenderer(Entities.FIRESTORM.get(), FirestormRenderer::new);
    }

    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions pEvent) {
        pEvent.registerLayerDefinition(ElderChestplateModel.LAYER_LOCATION, ElderChestplateModel::createBodyLayer);
        pEvent.registerLayerDefinition(ElderChestplateRetractedModel.LAYER_LOCATION, ElderChestplateRetractedModel::createBodyLayer);
    }
}
