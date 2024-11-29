package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.RREnumExtensions;
import com.midasdaepik.remnantrelics.registries.RRUtil;
import com.midasdaepik.remnantrelics.registries.RRItems;
import com.midasdaepik.remnantrelics.registries.RRSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class WarpingWither extends SwordItem {
    public WarpingWither(Properties pProperties) {
        super(new Tier() {
            public int getUses() {
                return 2285;
            }

            public float getSpeed() {
                return 9f;
            }

            public float getAttackDamageBonus() {
                return 5.5f;
            }

            public TagKey<Block> getIncorrectBlocksForDrops() {
                return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
            }

            public int getEnchantmentValue() {
                return 13;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(net.minecraft.world.item.Items.NETHERITE_SCRAP);
            }
        }, pProperties.fireResistant().attributes(WarpingWither.createAttributes()).rarity(RREnumExtensions.RARITY_WARPING_WITHER.getValue()));
    }

    public static @NotNull ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID,  5.5, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID,  -2.2, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.MOVEMENT_SPEED,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "movement_speed"), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public int getUseDuration(ItemStack pItemstack, LivingEntity pLivingEntity) {
        return 15;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pItemstack) {
        return UseAnim.BOW;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pItemStack, Level pLevel, LivingEntity pLivingEntity) {
        this.releaseUsing(pItemStack, pLevel, pLivingEntity, 0);
        return pItemStack;
    }

    @Override
    public boolean hurtEnemy(ItemStack pItemstack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pAttacker instanceof Player pLivingEntity) {
            if (pLivingEntity.getAttackStrengthScale(0) >= 0.9F) {
                if (!pAttacker.level().isClientSide() && Mth.nextInt(RandomSource.create(), 1, 8) == 1) {
                    pTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 120, 2));
                    pTarget.level().playSeededSound(null, pTarget.getEyePosition().x, pTarget.getEyePosition().y, pTarget.getEyePosition().z, RRSounds.ITEM_WITHERBLADE_WITHER.get(), SoundSource.PLAYERS, 1f, 1.2f,0);
                }
            }
        } else {
            if (!pAttacker.level().isClientSide() && Mth.nextInt(RandomSource.create(), 1, 8) == 1) {
                pTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 120, 2));
                pTarget.level().playSeededSound(null, pTarget.getEyePosition().x, pTarget.getEyePosition().y, pTarget.getEyePosition().z, RRSounds.ITEM_WITHERBLADE_WITHER.get(), SoundSource.HOSTILE, 1f, 1.2f,0);
            }
        }

        return super.hurtEnemy(pItemstack, pTarget, pAttacker);
    }

    @Override
    public void releaseUsing(ItemStack pItemStack, Level pLevel, LivingEntity pLivingEntity, int pTimeLeft) {
        int pTimeUsing = this.getUseDuration(pItemStack, pLivingEntity) - pTimeLeft;

        if (pTimeUsing >= 10) {
            if (pLevel instanceof ServerLevel pServerLevel) {
                pServerLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 16, 0.5, 0.5, 0.5, 0.02);
                pServerLevel.sendParticles(ParticleTypes.LARGE_SMOKE, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 8, 0.5, 0.5, 0.5, 0.01);
                pServerLevel.sendParticles(ParticleTypes.RAID_OMEN, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 8, 0.5, 0.5, 0.5, 0.01);
                pServerLevel.sendParticles(ParticleTypes.FLASH, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 1, 0, 0, 0, 0);
            }

            pLivingEntity.level().playSeededSound(null, pLivingEntity.getEyePosition().x, pLivingEntity.getEyePosition().y, pLivingEntity.getEyePosition().z, RRSounds.ITEM_WITHERBLADE_TELEPORT.get(), SoundSource.PLAYERS, 1f, 1f, 0);

            BlockHitResult raytrace = RRUtil.blockHitRaycast(pLevel, pLivingEntity, ClipContext.Fluid.NONE, 12);
            BlockPos lookPos = raytrace.getBlockPos().relative(raytrace.getDirection());
            pLivingEntity.setPos(lookPos.getX() + 0.5, lookPos.getY(), lookPos.getZ() + 0.5);
            pLivingEntity.fallDistance = pLivingEntity.fallDistance - 10.0F;

            final Vec3 AABBCenter = new Vec3(pLivingEntity.getEyePosition().x, pLivingEntity.getEyePosition().y, pLivingEntity.getEyePosition().z);
            List<LivingEntity> pFoundTarget = pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(AABBCenter, AABBCenter).inflate(2.5d), e -> true).stream().sorted(Comparator.comparingDouble(DistanceComparer -> DistanceComparer.distanceToSqr(AABBCenter))).toList();
            for (LivingEntity pEntityIterator : pFoundTarget) {
                if (!(pEntityIterator == pLivingEntity)) {
                    pEntityIterator.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 3));
                    pEntityIterator.hurt(new DamageSource(pLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "magic"))), pLivingEntity), 14);
                }
            }

            if (pLevel instanceof ServerLevel pServerLevel) {
                pServerLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 16, 0.5, 0.5, 0.5, 0.02);
                pServerLevel.sendParticles(ParticleTypes.LARGE_SMOKE, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 8, 0.5, 0.5, 0.5, 0.01);
                pServerLevel.sendParticles(ParticleTypes.RAID_OMEN, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 8, 0.5, 0.5, 0.5, 0.01);
                pServerLevel.sendParticles(ParticleTypes.FLASH, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 1, 0, 0, 0, 0);
            }

            pLivingEntity.level().playSeededSound(null, pLivingEntity.getEyePosition().x, pLivingEntity.getEyePosition().y, pLivingEntity.getEyePosition().z, RRSounds.ITEM_WITHERBLADE_TELEPORT.get(), SoundSource.PLAYERS, 1f, 1f,0);

            if (pLivingEntity instanceof Player pPlayer) {
                pItemStack.hurtAndBreak(3, pLivingEntity, pLivingEntity.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

                pPlayer.awardStat(Stats.ITEM_USED.get(this));

                pPlayer.getCooldowns().addCooldown(this, 140);
                pPlayer.getCooldowns().addCooldown(RRItems.WARPED_RAPIER.get(), 140);
            }
        } else {
            if (pLivingEntity instanceof Player pPlayer) {
                pPlayer.getCooldowns().addCooldown(this, 10);
                pPlayer.getCooldowns().addCooldown(RRItems.WARPED_RAPIER.get(), 10);
            }
        }
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pItemStack, int pTimeLeft) {
        int pTimeUsing = this.getUseDuration(pItemStack, pLivingEntity) - pTimeLeft;
        if (pLevel instanceof ServerLevel pServerLevel) {
            pServerLevel.sendParticles(ParticleTypes.DRAGON_BREATH, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 1, 0.4, 0.4, 0.4, 0.01);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
    }

    @Override
    public void appendHoverText(ItemStack pItemstack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (RRUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.warping_wither.shift_desc_1"));
            pTooltipComponents.add(Component.empty());
            pTooltipComponents.add(Component.translatable("item.remnantrelics.warping_wither.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.warping_wither.shift_desc_3"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.warping_wither.shift_desc_4"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        if (pItemstack.isEnchanted()) {
            pTooltipComponents.add(Component.empty());
        }
        super.appendHoverText(pItemstack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
