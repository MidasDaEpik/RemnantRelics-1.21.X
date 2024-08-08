package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.EnumExtensions;
import com.midasdaepik.remnantrelics.registries.ItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class Whisperwind extends ProjectileWeaponItem {
    public Whisperwind(Properties pProperties) {
        super(pProperties.durability(500).attributes(Whisperwind.createAttributes()).rarity(EnumExtensions.RARITY_WIND.getValue()));
    }

    public static @NotNull ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.MOVEMENT_SPEED,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "movement_speed"),  0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                        EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pItemstack, ItemStack pRepairCandidate) {
        return pRepairCandidate.is(Items.BREEZE_ROD);
    }

    @Override
    public int getUseDuration(ItemStack pItemstack, LivingEntity pEntity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pItemstack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack pItemstack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player pPlayer) {
            ItemStack ProjectileItemStack = pPlayer.getProjectile(pItemstack);
            if (!ProjectileItemStack.isEmpty()) {
                int i = this.getUseDuration(pItemstack, pEntityLiving) - pTimeLeft;
                i = EventHooks.onArrowLoose(ProjectileItemStack, pLevel, pPlayer, i, !ProjectileItemStack.isEmpty());
                if (i < 0) return;
                float f = getPowerForTime(i);
                if (!((double)f < 0.1)) {
                    List<ItemStack> list = draw(pItemstack, ProjectileItemStack, pPlayer);
                    if (pLevel instanceof ServerLevel serverlevel && !list.isEmpty()) {
                        this.shoot(serverlevel, pPlayer, pPlayer.getUsedItemHand(), pItemstack, list, f * 3.0F, 1.0F, f == 1.0F, null);
                    }
                    pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BREEZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    protected void shoot(ServerLevel pLevel, LivingEntity pLivingEntity, InteractionHand pHand, ItemStack pWeapon, List<ItemStack> pProjectileItems, float pVelocity, float pInaccuracy, boolean pIsCrit, @Nullable LivingEntity pTarget) {
        float f = EnchantmentHelper.processProjectileSpread(pLevel, pWeapon, pLivingEntity, 0.0F);
        float f1 = pProjectileItems.size() == 1 ? 0.0F : 2.0F * f / (float)(pProjectileItems.size() - 1);
        float f2 = (float)((pProjectileItems.size() - 1) % 2) * f1 / 2.0F;
        float f3 = 1.0F;

        for (int i = 0; i < pProjectileItems.size(); i++) {
            ItemStack ProjectileItemStack = pProjectileItems.get(i);
            if (!ProjectileItemStack.isEmpty()) {
                float f4 = f2 + f3 * (float)((i + 1) / 2) * f1;
                AbstractArrow pProjectile = this.createProjectile(pLevel, pLivingEntity, pWeapon, ProjectileItemStack, pIsCrit);
                this.shootProjectile(pLivingEntity, pProjectile, pVelocity, pInaccuracy, f4, pTarget);
                pLevel.addFreshEntity(pProjectile);
                pWeapon.hurtAndBreak(this.getDurabilityUse(ProjectileItemStack), pLivingEntity, LivingEntity.getSlotForHand(pHand));
                if (pWeapon.isEmpty()) {
                    break;
                }
            }
        }
    }

    @Override
    protected AbstractArrow createProjectile(Level pLevel, LivingEntity pLivingEntity, ItemStack pWeapon, ItemStack pAmmo, boolean pIsCrit) {
        ArrowItem arrowitem = pAmmo.getItem() instanceof ArrowItem arrowitem1 ? arrowitem1 : (ArrowItem)Items.ARROW;
        AbstractArrow abstractarrow = arrowitem.createArrow(pLevel, pAmmo, pLivingEntity, pWeapon);
        if (pIsCrit) {
            abstractarrow.setCritArrow(true);
        }

        return customArrow(abstractarrow, pAmmo, pWeapon);
    }

    protected void shootProjectile(LivingEntity pLivingEntity, AbstractArrow pProjectile, float pVelocity, float pInaccuracy, float pAngle, @Nullable LivingEntity pTarget) {
        pProjectile.setBaseDamage(pProjectile.getBaseDamage() * 0.66);
        pProjectile.shootFromRotation(pLivingEntity, pLivingEntity.getXRot(), pLivingEntity.getYRot() + pAngle, 0.0F, pVelocity * 0.5f, pInaccuracy * 0.5f);
    }

    @Override
    protected void shootProjectile(LivingEntity pLivingEntity, Projectile pProjectile, int pIndex, float pVelocity, float pInaccuracy, float pAngle, @Nullable LivingEntity pTarget) {
        pProjectile.shootFromRotation(pLivingEntity, pLivingEntity.getXRot(), pLivingEntity.getYRot() + pAngle, 0.0F, pVelocity, pInaccuracy);
    }

    public static float getPowerForTime(int pCharge) {
        float f = (float)pCharge / 15.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack ProjectileItemStack = pPlayer.getItemInHand(pHand);
        boolean flag = !pPlayer.getProjectile(ProjectileItemStack).isEmpty();

        InteractionResultHolder<ItemStack> ret = EventHooks.onArrowNock(ProjectileItemStack, pLevel, pPlayer, pHand, flag);
        if (ret != null) return ret;

        if (!pPlayer.hasInfiniteMaterials() && !flag) {
            return InteractionResultHolder.fail(ProjectileItemStack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(ProjectileItemStack);
        }
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public void appendHoverText(ItemStack pItemstack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (ItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.whisperwind.shift_desc_1"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        if (pItemstack.isEnchanted()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.empty"));
        }
        super.appendHoverText(pItemstack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
