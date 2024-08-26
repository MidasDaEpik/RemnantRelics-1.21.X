package com.midasdaepik.remnantrelics.event;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.client.model.ElderChestplateModel;
import com.midasdaepik.remnantrelics.client.model.ElderChestplateRetractedModel;
import com.midasdaepik.remnantrelics.entity.renderer.FirestormRenderer;
import com.midasdaepik.remnantrelics.entity.renderer.SonicBlastRenderer;
import com.midasdaepik.remnantrelics.registries.Entities;
import com.midasdaepik.remnantrelics.registries.ItemProperties;
import com.midasdaepik.remnantrelics.registries.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

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

    @SubscribeEvent
    public static void onRegisterColorHandlersEventItem(RegisterColorHandlersEvent.Item pEvent) {
        pEvent.register(
                (pItemStack, pTintIndex) ->  pTintIndex == 1 ? DyedItemColor.getOrDefault(pItemStack, 6448520) : -1,
                Items.ELDER_CHESTPLATE
        );
    }

    @SubscribeEvent
    public static void onRegisterClientExtensionsEvent(RegisterClientExtensionsEvent pEvent) {
        pEvent.registerItem(
                new IClientItemExtensions() {
                    public int getDefaultDyeColor(ItemStack pItemStack) {
                        return pItemStack.is(ItemTags.DYEABLE) ? FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(pItemStack, 6448520)) : -1;
                    }

                    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity pLivingEntity, ItemStack pItemStack, EquipmentSlot pEquipmentSlot, HumanoidModel<?> pDefaultModel) {
                        if (pLivingEntity instanceof Player pPlayer && pPlayer.getCooldowns().isOnCooldown(pItemStack.getItem())) {
                            HumanoidModel pArmorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of(
                                    "body", new ElderChestplateRetractedModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateRetractedModel.LAYER_LOCATION)).Body,
                                    "left_arm", new ElderChestplateRetractedModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateRetractedModel.LAYER_LOCATION)).LeftArm,
                                    "right_arm", new ElderChestplateRetractedModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateRetractedModel.LAYER_LOCATION)).RightArm,
                                    "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
                            )));
                            pArmorModel.crouching = pDefaultModel.crouching;
                            pArmorModel.riding = pDefaultModel.riding;
                            pArmorModel.swimAmount = pDefaultModel.swimAmount;
                            pArmorModel.young = pDefaultModel.young;
                            return pArmorModel;
                        } else {
                            HumanoidModel pArmorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of(
                                    "body", new ElderChestplateModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateModel.LAYER_LOCATION)).Body,
                                    "left_arm", new ElderChestplateModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateModel.LAYER_LOCATION)).LeftArm,
                                    "right_arm", new ElderChestplateModel(Minecraft.getInstance().getEntityModels().bakeLayer(ElderChestplateModel.LAYER_LOCATION)).RightArm,
                                    "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                                    "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
                            )));
                            pArmorModel.crouching = pLivingEntity.isShiftKeyDown();
                            pArmorModel.riding = pDefaultModel.riding;
                            pArmorModel.young = pLivingEntity.isBaby();
                            pArmorModel.swimAmount = pDefaultModel.swimAmount;
                            return pArmorModel;
                        }
                    }
                },
                Items.ELDER_CHESTPLATE.get()
        );
    }
}
