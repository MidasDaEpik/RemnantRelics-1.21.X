package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.entity.custom.SonicBlast;
import com.midasdaepik.remnantrelics.registries.EnumExtensions;
import com.midasdaepik.remnantrelics.registries.ItemUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class LyreOfEchoes extends Item {
    public LyreOfEchoes(Properties pProperties) {
        super(pProperties.durability(128).rarity(EnumExtensions.RARITY_SCULK.getValue()));
    }

    @Override
    public int getEnchantmentValue(ItemStack pItemStack) {
        return 10;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pItemStack, ItemStack pRepairCandidate) {
        return pRepairCandidate.is(Items.ECHO_SHARD);
    }

    @Override
    public int getUseDuration(ItemStack pItemStack, LivingEntity pLivingEntity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pItemStack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack pItemStack, Level pLevel, LivingEntity pLivingEntity, int pTimeLeft) {
        int pTimeUsing = this.getUseDuration(pItemStack, pLivingEntity) - pTimeLeft;
        if (pTimeUsing >= 300) {
            pTimeUsing = 300;
        }

        if (pTimeUsing >= 60) {
            if (!pLevel.isClientSide) {
                int pDamage = pTimeUsing / 12 + 10;
                SonicBlast sonicBlast = new SonicBlast(pLevel, pLivingEntity, pTimeUsing * 2 + 30, pDamage);
                sonicBlast.setPos(pLivingEntity.getEyePosition().x, pLivingEntity.getEyePosition().y, pLivingEntity.getEyePosition().z);
                sonicBlast.shootFromRotation(pLivingEntity, pLivingEntity.getXRot(), pLivingEntity.getYRot(), 0.2f, 1f, 0.0f);
                pLevel.addFreshEntity(sonicBlast);
            }

            pLivingEntity.setDeltaMovement(pLivingEntity.getDeltaMovement().x - pLivingEntity.getLookAngle().x * pTimeUsing * 0.006, pLivingEntity.getDeltaMovement().y - pLivingEntity.getLookAngle().y * pTimeUsing * 0.006, pLivingEntity.getDeltaMovement().z - pLivingEntity.getLookAngle().z * pTimeUsing * 0.006);

            pLevel.playSeededSound(null, pLivingEntity.getEyePosition().x, pLivingEntity.getEyePosition().y, pLivingEntity.getEyePosition().z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2f, 1.2f - pTimeUsing * 0.001f,0);

            pItemStack.hurtAndBreak(1, pLivingEntity, pLivingEntity.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

            if (pLivingEntity instanceof Player pPlayer) {
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pItemStack, int pTimeLeft) {
        int pTimeUsing = this.getUseDuration(pItemStack, pLivingEntity) - pTimeLeft;
        if (pLevel instanceof ServerLevel pServerLevel) {
            if (pTimeUsing >= 60 && pTimeUsing <= 180) {
                pServerLevel.sendParticles(ParticleTypes.SCULK_CHARGE_POP, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 1, 0.3, 0.3, 0.3, 0.05);
            }
            if (pTimeUsing >= 180) {
                pServerLevel.sendParticles(ParticleTypes.SCULK_SOUL, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 1, 0.3, 0.3, 0.3, 0.05);
            }
            if (pTimeUsing >= 300) {
                pServerLevel.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 1, 0.3, 0.3, 0.3, 0);
            }
        }

        if (pTimeUsing % 10 == 0) {
            int NoteNumber = (pTimeUsing / 10) % 6;
            if (NoteNumber <= 2) {
                if (NoteNumber == 0) {
                    pLevel.playSeededSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.NOTE_BLOCK_HARP, SoundSource.RECORDS, 1.5f, 0.561231f,0);
                } else if (NoteNumber == 1) {
                    pLevel.playSeededSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.NOTE_BLOCK_HARP, SoundSource.RECORDS, 1.5f, 0.667420f,0);
                } else {
                    pLevel.playSeededSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.NOTE_BLOCK_HARP, SoundSource.RECORDS, 1.5f, 0.840896f,0);
                }
            } else {
                if (NoteNumber == 3) {
                    pLevel.playSeededSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.NOTE_BLOCK_HARP, SoundSource.RECORDS, 1.5f, 0.890899f,0);
                } else if (NoteNumber == 4) {
                    pLevel.playSeededSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.NOTE_BLOCK_HARP, SoundSource.RECORDS, 1.5f, 0.840896f,0);
                } else {
                    pLevel.playSeededSound(null, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), SoundEvents.NOTE_BLOCK_HARP, SoundSource.RECORDS, 1.5f, 0.667420f,0);
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
    }

    @Override
    public void appendHoverText(ItemStack pItemStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (ItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.lyre_of_echoes.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.lyre_of_echoes.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.lyre_of_echoes.shift_desc_3"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        if (pItemStack.isEnchanted()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
        }
        super.appendHoverText(pItemStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
