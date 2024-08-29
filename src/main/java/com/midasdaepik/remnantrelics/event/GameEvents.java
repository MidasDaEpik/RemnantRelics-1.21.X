package com.midasdaepik.remnantrelics.event;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.networking.DragonsRageSyncS2CPacket;
import com.midasdaepik.remnantrelics.registries.RRItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.midasdaepik.remnantrelics.registries.RRAttachmentTypes.DRAGONS_RAGE_CHARGE;
import static com.midasdaepik.remnantrelics.registries.RRAttachmentTypes.TIME_SINCE_LAST_ATTACK;

@EventBusSubscriber(modid = RemnantRelics.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class GameEvents {
    @SubscribeEvent
    public static void onLivingDamageEventPost(LivingDamageEvent.Post pEvent) {
        LivingEntity pLivingEntity = pEvent.getEntity();
        if (pLivingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() == RRItems.ELDER_CHESTPLATE.get() && pLivingEntity.level() instanceof ServerLevel pServerLevel) {
            Entity pDamageSourceEntity = pEvent.getSource().getEntity();
            if (pDamageSourceEntity instanceof LivingEntity pDamageSourceLivingEntity && !pDamageSourceLivingEntity.isInvulnerable()) {
                if (pLivingEntity instanceof Player pPlayer) {
                    if (!pPlayer.getCooldowns().isOnCooldown(RRItems.ELDER_CHESTPLATE.get())) {
                        pDamageSourceLivingEntity.hurt(new DamageSource(pServerLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.THORNS), pLivingEntity), pEvent.getOriginalDamage() * 0.8f);
                        pPlayer.getCooldowns().addCooldown(RRItems.ELDER_CHESTPLATE.get(), 80);
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
                    pPiglinBrute.setItemSlot(EquipmentSlot.MAINHAND, RRItems.PIGLIN_WARAXE.toStack());
                    pPiglinBrute.setDropChance(EquipmentSlot.MAINHAND, 0.2f);
                }
            } else if (pEntity instanceof WitherSkeleton pWitherSkeleton) {
                if (pWitherSkeleton.getMainHandItem().getItem() == net.minecraft.world.item.Items.STONE_SWORD && Mth.nextInt(RandomSource.create(), 1, 3) == 1) {
                    pWitherSkeleton.setItemSlot(EquipmentSlot.MAINHAND, RRItems.WITHERBLADE.toStack());
                    pWitherSkeleton.setDropChance(EquipmentSlot.MAINHAND, 0.15f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCriticalHitEvent(CriticalHitEvent pEvent) {
        Player pPlayer = pEvent.getEntity();
        Level pLevel = pPlayer.level();

        if (pLevel instanceof ServerLevel pServerLevel) {
            pPlayer.setData(TIME_SINCE_LAST_ATTACK, 0);
        }
    }

    @SubscribeEvent
    public static void onPlayerTickEventPost(PlayerTickEvent.Post pEvent) {
        Player pPlayer = pEvent.getEntity();
        Level pLevel = pPlayer.level();

        if (pLevel instanceof ServerLevel pServerLevel && pPlayer instanceof ServerPlayer pServerPlayer) {
            int TimeSinceLastAttack = pPlayer.getData(TIME_SINCE_LAST_ATTACK);
            pPlayer.setData(TIME_SINCE_LAST_ATTACK, pPlayer.getData(TIME_SINCE_LAST_ATTACK) + 1);

            int RageCharge = pPlayer.getData(DRAGONS_RAGE_CHARGE);
            if (TimeSinceLastAttack >= 100 && RageCharge > -100) {
                RageCharge = Mth.clamp(RageCharge - 3, -100, 1200);
                pPlayer.setData(DRAGONS_RAGE_CHARGE, RageCharge);
                PacketDistributor.sendToPlayer(pServerPlayer, new DragonsRageSyncS2CPacket(RageCharge));
            }
        }

        if (pLevel instanceof ClientLevel pClientLevel) {
            ItemStack pMainHandItem = pPlayer.getMainHandItem();

            int RageCharge = pPlayer.getData(DRAGONS_RAGE_CHARGE);
            if (pMainHandItem.getItem() == RRItems.DRAGONS_RAGE.get() && RageCharge > -100) {
                int PercentageRageCharge = Mth.clamp(Mth.ceil((float) RageCharge / 100), 0, 12);

                String Full_Charges = "";
                for (int Loop = 1; Loop <= PercentageRageCharge; Loop++) {
                    if (Loop == 1) {
                        Full_Charges = Full_Charges.concat("\uD83D\uDDE1");
                    } else {
                        Full_Charges = Full_Charges.concat(" \uD83D\uDDE1");
                    }
                }

                String Blank_Charges = "";
                for (int Loop = 1; Loop <= 12 - PercentageRageCharge; Loop++) {
                    if (PercentageRageCharge == 0 && Loop == 1) {
                        Blank_Charges = Blank_Charges.concat("\uD83D\uDDE1");
                    } else {
                        Blank_Charges = Blank_Charges.concat(" \uD83D\uDDE1");
                    }
                }

                pPlayer.displayClientMessage(Component.literal(Full_Charges).withColor(13719531).append(Component.literal(Blank_Charges).withStyle(ChatFormatting.DARK_GRAY)), true);
            }
        }
    }
}
