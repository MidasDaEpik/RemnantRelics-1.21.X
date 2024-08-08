package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerHeartTypeEvent;

@EventBusSubscriber(modid = RemnantRelics.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public class ClientGameEvents {
    @SubscribeEvent
    public static void onPlayerHeartTypeEvent(PlayerHeartTypeEvent pEvent) {
        LivingEntity pLivingEntity = pEvent.getEntity();
        if (pLivingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.WHISPERWIND.get()) {
            pEvent.setType(EnumExtensions.HEART_FROSTBITE.getValue());
        }
    }
}
