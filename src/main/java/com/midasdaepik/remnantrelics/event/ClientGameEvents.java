package com.midasdaepik.remnantrelics.event;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.Items;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.event.entity.player.PlayerHeartTypeEvent;

@EventBusSubscriber(modid = RemnantRelics.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class ClientGameEvents {
    @SubscribeEvent
    public static void onPlayerHeartTypeEvent(PlayerHeartTypeEvent pEvent) {
        LivingEntity pLivingEntity = pEvent.getEntity();
        if (pLivingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.WHISPERWIND.get()) {
            pEvent.setType(Gui.HeartType.FROZEN);
        }
    }

    @SubscribeEvent
    public static void onComputeFovModifierEvent(ComputeFovModifierEvent pEvent) {
        Player pPlayer = pEvent.getPlayer();
        ItemStack pItemstack = pPlayer.getUseItem();

        if (pPlayer.isUsingItem()) {
            if (pItemstack.is(Items.WHISPERWIND)) {
                float pMDraw = pPlayer.getTicksUsingItem() / 15.0F;
                if (pMDraw > 1.0F) {
                    pMDraw = 1.0F;
                } else {
                    pMDraw *= pMDraw;
                }

                pEvent.setNewFovModifier(pEvent.getFovModifier() * (1.0F - pMDraw * 0.2F));
            } else if (pItemstack.is(Items.LYRE_OF_ECHOES)) {
                float pMDraw = pPlayer.getTicksUsingItem() / 300.0F;
                if (pMDraw > 1.0F) {
                    pMDraw = 1.0F;
                } else {
                    pMDraw *= pMDraw;
                }

                pEvent.setNewFovModifier(pEvent.getFovModifier() * (1.0F + pMDraw * 0.2F));
            }
        }
    }
}
