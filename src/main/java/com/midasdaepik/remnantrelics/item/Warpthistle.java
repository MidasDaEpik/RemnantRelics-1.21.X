package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
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

public class Warpthistle extends SwordItem {
    public Warpthistle(Properties pProperties) {
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
        }, pProperties.fireResistant().attributes(Warpthistle.createAttributes()).rarity(RREnumExtensions.RARITY_WARPTHISTLE.getValue()));
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
    public int getUseDuration(ItemStack pItemStack, LivingEntity pLivingEntity) {
        return 15;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pItemStack) {
        return UseAnim.BOW;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pItemStack, Level pLevel, LivingEntity pLivingEntity) {
        this.releaseUsing(pItemStack, pLevel, pLivingEntity, 0);
        return pItemStack;
    }

    @Override
    public boolean hurtEnemy(ItemStack pItemStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pAttacker instanceof Player pPlayer) {
            if (pPlayer.getAttackStrengthScale(0) >= 0.9F) {
                attackEffects(pItemStack, pTarget, pAttacker);
            }
        } else {
            attackEffects(pItemStack, pTarget, pAttacker);
        }

        return super.hurtEnemy(pItemStack, pTarget, pAttacker);
    }

    public void attackEffects(ItemStack pItemStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (!pAttacker.level().isClientSide() && Mth.nextInt(RandomSource.create(), 1, 8) == 1) {
            pTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 120, 2));
            pTarget.level().playSeededSound(null, pTarget.getEyePosition().x, pTarget.getEyePosition().y, pTarget.getEyePosition().z, RRSounds.ITEM_WITHERBLADE_WITHER.get(), SoundSource.HOSTILE, 1f, 1.2f,0);
        }
    }

    @Override
    public void releaseUsing(ItemStack pItemStack, Level pLevel, LivingEntity pLivingEntity, int pTimeLeft) {
        int pTimeUsing = this.getUseDuration(pItemStack, pLivingEntity) - pTimeLeft;
        boolean pDamageToggle = pItemStack.getOrDefault(RRDataComponents.ITEM_TOGGLE, true);

        if (pTimeUsing >= 10) {
            if (pLevel instanceof ServerLevel pServerLevel) {
                pServerLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 16, 0.5, 0.5, 0.5, 0.02);
                pServerLevel.sendParticles(ParticleTypes.LARGE_SMOKE, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 8, 0.5, 0.5, 0.5, 0.01);
                pServerLevel.sendParticles(ParticleTypes.FLASH, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 1, 0, 0, 0, 0);
                if (pDamageToggle) {
                    pServerLevel.sendParticles(ParticleTypes.RAID_OMEN, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 8, 0.5, 0.5, 0.5, 0.01);
                }
            }

            pLivingEntity.level().playSeededSound(null, pLivingEntity.getEyePosition().x, pLivingEntity.getEyePosition().y, pLivingEntity.getEyePosition().z, RRSounds.ITEM_WITHERBLADE_TELEPORT.get(), SoundSource.PLAYERS, 1f, 1f, 0);

            BlockHitResult pRaytrace = RRUtil.blockHitRaycast(pLevel, pLivingEntity, ClipContext.Fluid.NONE, 12);
            BlockPos pLookPos = pRaytrace.getBlockPos().relative(pRaytrace.getDirection());
            pLivingEntity.setPos(pLookPos.getX() + 0.5, pLookPos.getY(), pLookPos.getZ() + 0.5);
            pLivingEntity.fallDistance = pLivingEntity.fallDistance - 10.0F;

            if (pDamageToggle) {
                final Vec3 AABBCenter = new Vec3(pLivingEntity.getEyePosition().x, pLivingEntity.getEyePosition().y, pLivingEntity.getEyePosition().z);
                List<LivingEntity> pFoundTarget = pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(AABBCenter, AABBCenter).inflate(2.5d), e -> true).stream().sorted(Comparator.comparingDouble(DistanceComparer -> DistanceComparer.distanceToSqr(AABBCenter))).toList();
                for (LivingEntity pEntityIterator : pFoundTarget) {
                    if (!(pEntityIterator == pLivingEntity)) {
                        pEntityIterator.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 50, 3));
                        pEntityIterator.hurt(new DamageSource(pLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "magic"))), pLivingEntity), 14);
                    }
                }
            }

            if (pLevel instanceof ServerLevel pServerLevel) {
                pServerLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 16, 0.5, 0.5, 0.5, 0.02);
                pServerLevel.sendParticles(ParticleTypes.LARGE_SMOKE, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 8, 0.5, 0.5, 0.5, 0.01);
                pServerLevel.sendParticles(ParticleTypes.FLASH, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 1, 0, 0, 0, 0);
                if (pDamageToggle) {
                    pServerLevel.sendParticles(ParticleTypes.RAID_OMEN, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 8, 0.5, 0.5, 0.5, 0.01);
                }
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
    public boolean overrideOtherStackedOnMe(ItemStack pItemStack, ItemStack pOtherItemStack, Slot pSlot, ClickAction pClickAction, Player pPlayer, SlotAccess pSlotAccess) {
        if (pClickAction == ClickAction.SECONDARY && pOtherItemStack.isEmpty()) {
            if (pPlayer.level().isClientSide) {
                pPlayer.level().playSeededSound(null, pPlayer.getEyePosition().x, pPlayer.getEyePosition().y, pPlayer.getEyePosition().z, SoundEvents.UI_BUTTON_CLICK, SoundSource.PLAYERS, 1f, 1f, 0);
            }
            if (pItemStack.getOrDefault(RRDataComponents.ITEM_TOGGLE, true)) {
                pItemStack.set(RRDataComponents.ITEM_TOGGLE, false);
            } else {
                pItemStack.set(RRDataComponents.ITEM_TOGGLE, true);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void appendHoverText(ItemStack pItemStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (RRUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.warpthistle.shift_desc_1"));
            pTooltipComponents.add(Component.empty());
            pTooltipComponents.add(Component.translatable("item.remnantrelics.warpthistle.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.warpthistle.shift_desc_3"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.warpthistle.shift_desc_4"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.warpthistle.shift_desc_5"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
            pTooltipComponents.add(Component.empty());
            pTooltipComponents.add(Component.translatable("item.remnantrelics.warpthistle.lore_damage_toggle", pItemStack.getOrDefault(RRDataComponents.ITEM_TOGGLE, true) ? "§aEnabled" : "§cDisabled"));
        }
        if (pItemStack.isEnchanted()) {
            pTooltipComponents.add(Component.empty());
        }
        super.appendHoverText(pItemStack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
