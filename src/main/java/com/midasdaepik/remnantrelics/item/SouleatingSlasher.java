package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.*;
import com.midasdaepik.remnantrelics.registries.Items;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class SouleatingSlasher extends SwordItem {
    public SouleatingSlasher(Properties pProperties) {
        super(new Tier() {
            public int getUses() {
                return 1270;
            }

            public float getSpeed() {
                return 9f;
            }

            public float getAttackDamageBonus() {
                return 13f;
            }

            public TagKey<Block> getIncorrectBlocksForDrops() {
                return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
            }

            public int getEnchantmentValue() {
                return 18;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(net.minecraft.world.item.Items.NETHERITE_SCRAP);
            }
        }, pProperties.fireResistant().attributes(SouleatingSlasher.createAttributes()).rarity(EnumExtensions.RARITY_SOULEATING_SLASHER.getValue()));
    }

    public static @NotNull ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(BASE_ATTACK_DAMAGE_ID,  13, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID,  -3, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ARMOR,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "armor"), 6, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .add(Attributes.KNOCKBACK_RESISTANCE,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "knockback_resistance"), 0.3, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public int getUseDuration(ItemStack pItemstack, LivingEntity pEntity) {
        return 20;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pItemstack) {
        return UseAnim.BLOCK;
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
                if (!pAttacker.level().isClientSide() && Mth.nextInt(RandomSource.create(), 1, 7) == 1) {
                    final Vec3 _center = new Vec3(pTarget.getX(), pTarget.getY(), pTarget.getZ());
                    List<LivingEntity> pFoundTarget = pTarget.level().getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                    for (LivingEntity pEntityIterator : pFoundTarget) {
                        if (!(pEntityIterator == pAttacker)) {
                            pEntityIterator.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 0));
                        }
                    }
                    pTarget.level().playSeededSound(null, pTarget.getEyePosition().x, pTarget.getEyePosition().y, pTarget.getEyePosition().z, Sounds.ITEM_WITHERBLADE_WITHER.get(), SoundSource.PLAYERS, 1f, 1f,0);
                }
            }
        } else {
            if (!pAttacker.level().isClientSide() && Mth.nextInt(RandomSource.create(), 1, 8) == 1) {
                final Vec3 _center = new Vec3(pTarget.getX(), pTarget.getY(), pTarget.getZ());
                List<LivingEntity> pFoundTarget = pTarget.level().getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (LivingEntity pEntityIterator : pFoundTarget) {
                    if (!(pEntityIterator == pAttacker)) {
                        pEntityIterator.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 0));
                    }
                }
                pTarget.level().playSeededSound(null, pTarget.getEyePosition().x, pTarget.getEyePosition().y, pTarget.getEyePosition().z, Sounds.ITEM_WITHERBLADE_WITHER.get(), SoundSource.HOSTILE, 1f, 1f,0);
            }
        }

        return super.hurtEnemy(pItemstack, pTarget, pAttacker);
    }

    @Override
    public void releaseUsing(ItemStack pItemStack, Level pLevel, LivingEntity pLivingEntity, int pTimeLeft) {
        int pTimeUsing = this.getUseDuration(pItemStack, pLivingEntity) - pTimeLeft;
        if (pTimeUsing >= 20) {
            if (pLevel instanceof ServerLevel pServerLevel) {
                pServerLevel.sendParticles(ParticleTypes.SOUL, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 10, 0.5, 0.5, 0.5, 0.02);
                pServerLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 10, 0.3, 0.3, 0.3, 0.1);
            }

            pLivingEntity.level().playSeededSound(null, pLivingEntity.getEyePosition().x, pLivingEntity.getEyePosition().y, pLivingEntity.getEyePosition().z, Sounds.ITEM_WITHERBLADE_ABSORPTION.get(), SoundSource.PLAYERS, 1f, 1f,0);

            float AbsorptionShield = pLivingEntity.getAbsorptionAmount() + 3;
            if (AbsorptionShield > 6) {
                AbsorptionShield = 6;
            }

            int BulwarkEffectLevel = 3 - Mth.floor(pLivingEntity.getAbsorptionAmount() + 3 - AbsorptionShield);
            if (pLivingEntity.hasEffect(Effects.BULWARK)) {
                pLivingEntity.addEffect(new MobEffectInstance(Effects.BULWARK, 1200, pLivingEntity.getEffect(Effects.BULWARK).getAmplifier() + BulwarkEffectLevel, true, true));
            } else {
                pLivingEntity.addEffect(new MobEffectInstance(Effects.BULWARK, 1200, BulwarkEffectLevel - 1, true, true));
            }

            if (pLivingEntity.getAbsorptionAmount() < AbsorptionShield) {
                pLivingEntity.setAbsorptionAmount(AbsorptionShield);
            }

            int WitherTargets = 0;

            final Vec3 _center = new Vec3(pLivingEntity.getEyePosition().x, pLivingEntity.getEyePosition().y, pLivingEntity.getEyePosition().z);
            List<LivingEntity> pFoundTarget = pLevel.getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(8d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (LivingEntity pEntityIterator : pFoundTarget) {
                if (pEntityIterator.hasEffect(MobEffects.WITHER)) {
                    if (!(pEntityIterator == pLivingEntity)) {
                        WitherTargets = WitherTargets + 1;
                        pEntityIterator.hurt(new DamageSource(pLevel.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("remnantrelics:magic"))), pLivingEntity), (pEntityIterator.getEffect(MobEffects.WITHER).getAmplifier() + 2) * 6);
                        if (pLevel instanceof ServerLevel pServerLevel) {
                            pServerLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, pEntityIterator.getX(), pEntityIterator.getY() + 1, pEntityIterator.getZ(), 10, 0.3, 0.3, 0.3, 0.1);
                        }
                        pEntityIterator.removeEffect(MobEffects.WITHER);
                    }
                }
            }

            if (pLivingEntity.getAbsorptionAmount() + WitherTargets > 10) {
                AbsorptionShield = 10;
            } else {
                AbsorptionShield = pLivingEntity.getAbsorptionAmount() + WitherTargets;
            }

            BulwarkEffectLevel = WitherTargets - Mth.floor(pLivingEntity.getAbsorptionAmount() + WitherTargets - AbsorptionShield);
            if (pLivingEntity.hasEffect(Effects.BULWARK)) {
                pLivingEntity.addEffect(new MobEffectInstance(Effects.BULWARK, 1200, pLivingEntity.getEffect(Effects.BULWARK).getAmplifier() + BulwarkEffectLevel, true, true));
            } else {
                pLivingEntity.addEffect(new MobEffectInstance(Effects.BULWARK, 1200, BulwarkEffectLevel - 1, true, true));
            }

            if (pLivingEntity.getAbsorptionAmount() < AbsorptionShield) {
                pLivingEntity.setAbsorptionAmount(AbsorptionShield);
            }

            pItemStack.hurtAndBreak(10, pLivingEntity, pLivingEntity.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

            if (pLivingEntity instanceof Player pPlayer) {
                pPlayer.awardStat(Stats.ITEM_USED.get(this));

                pPlayer.getCooldowns().addCooldown(this, 400);
                pPlayer.getCooldowns().addCooldown(Items.OBSIDIAN_BULWARK.get(), 400);
            }
        } else {
            if (pLivingEntity instanceof Player pPlayer) {
                pPlayer.getCooldowns().addCooldown(this, 10);
                pPlayer.getCooldowns().addCooldown(Items.OBSIDIAN_BULWARK.get(), 10);
            }
        }
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pItemStack, int pTimeLeft) {
        if (pLevel instanceof ServerLevel pServerLevel) {
            pServerLevel.sendParticles(ParticleTypes.SOUL, pLivingEntity.getX(), pLivingEntity.getY() + 1, pLivingEntity.getZ(), 1, 0.4, 0.4, 0.4, 0.01);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
    }

    @Override
    public void inventoryTick(ItemStack pItemStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof LivingEntity pLivingEntity && pIsSelected) {
            if (!pLivingEntity.getOffhandItem().isEmpty()) {
                pLivingEntity.addEffect(new MobEffectInstance(Effects.UNWIELDY, 1, 0, true, false, false));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pItemstack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (ItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.two_handed"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.souleating_slasher.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.souleating_slasher.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.souleating_slasher.shift_desc_3"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.souleating_slasher.shift_desc_4"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.souleating_slasher.shift_desc_5"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.souleating_slasher.shift_desc_6"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        if (pItemstack.isEnchanted()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
        }
        super.appendHoverText(pItemstack, pContext, pTooltipComponents, pIsAdvanced);
    }
}