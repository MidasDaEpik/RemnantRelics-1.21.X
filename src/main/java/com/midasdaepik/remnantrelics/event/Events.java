package com.midasdaepik.remnantrelics.event;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = RemnantRelics.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class Events {
    @SubscribeEvent
    public static void onLivingDamageEventPost(LivingDamageEvent.Post pEvent) {
        LivingEntity pLivingEntity = pEvent.getEntity();
        if (pLivingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == Items.ELDER_CHESTPLATE.get()) {
            Entity pDamageSourceEntity = pEvent.getSource().getEntity();
            if (pDamageSourceEntity instanceof LivingEntity pDamageSourceLivingEntity && !pDamageSourceLivingEntity.isInvulnerable() && pLivingEntity.level() instanceof ServerLevel pServerLevel) {
                if (pLivingEntity instanceof Player pPlayer) {
                    if (!pPlayer.getCooldowns().isOnCooldown(Items.ELDER_CHESTPLATE.get())) {
                        pDamageSourceLivingEntity.hurt(new DamageSource(pServerLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.THORNS), pLivingEntity), pEvent.getOriginalDamage() * 0.8f);
                        pPlayer.getCooldowns().addCooldown(Items.ELDER_CHESTPLATE.get(), 80);
                    }
                } else if (Mth.nextInt(RandomSource.create(), 1, 3) == 1) {
                    pDamageSourceLivingEntity.hurt(new DamageSource(pServerLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.THORNS), pLivingEntity), pEvent.getOriginalDamage() * 0.8f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent pEvent) {
        if (!pEvent.loadedFromDisk()) {
            Entity pEntity = pEvent.getEntity();
            if (pEntity instanceof PiglinBrute pPiglinBrute) {
                if (pPiglinBrute.getMainHandItem().getItem() == net.minecraft.world.item.Items.GOLDEN_AXE && Mth.nextInt(RandomSource.create(), 1, 3) == 1) {
                    pPiglinBrute.setItemSlot(EquipmentSlot.MAINHAND, Items.PIGLIN_WARAXE.toStack());
                    pPiglinBrute.setDropChance(EquipmentSlot.MAINHAND, 0.25f);
                }
            }
        }
    }
}
