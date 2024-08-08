package com.midasdaepik.remnantrelics.item;

import com.midasdaepik.remnantrelics.entity.custom.NoDamageFireball;
import com.midasdaepik.remnantrelics.registries.ItemUtil;
import com.midasdaepik.remnantrelics.registries.EnumExtensions;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SearingStaff extends Item {
    public SearingStaff(Properties pProperties) {
        super(pProperties.durability(512).rarity(EnumExtensions.RARITY_BLAZE.getValue()));
    }

    @Override
    public int getEnchantmentValue() {
        return 12;
    }

    @Override
    public boolean isValidRepairItem(ItemStack pItemstack, ItemStack pRepairCandidate) {
        return pRepairCandidate.is(Items.BLAZE_ROD);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            NoDamageFireball fireballMid = new NoDamageFireball(pLevel, pPlayer, new Vec3(0, 0, 0));
            fireballMid.setPos(pPlayer.getX() + Mth.sin(pPlayer.getYRot() * ((float)Math.PI / 180F) - (float)Math.PI) * 2, pPlayer.getY() + 1.25, pPlayer.getZ() + Mth.cos(pPlayer.getYRot() * ((float)Math.PI / 180F) - (float)Math.PI) * -2);
            fireballMid.DespawnDuration = 400;
            fireballMid.FlyDuration = 400;
            fireballMid.explosionPower = 2f;
            pLevel.addFreshEntity(fireballMid);

            NoDamageFireball fireballPos45 = new NoDamageFireball(pLevel, pPlayer, new Vec3(0, 0, 0));
            fireballPos45.setPos(pPlayer.getX() + Mth.sin((pPlayer.getYRot() + 45) * ((float)Math.PI / 180F) - (float)Math.PI) * 2, pPlayer.getY() + 1.25, pPlayer.getZ() + Mth.cos((pPlayer.getYRot() + 45) * ((float)Math.PI / 180F) - (float)Math.PI) * -2);
            fireballPos45.DespawnDuration = 400;
            fireballPos45.FlyDuration = 400;
            fireballPos45.explosionPower = 2f;
            pLevel.addFreshEntity(fireballPos45);

            NoDamageFireball fireballNeg45 = new NoDamageFireball(pLevel, pPlayer, new Vec3(0, 0, 0));
            fireballNeg45.setPos(pPlayer.getX() + Mth.sin((pPlayer.getYRot() - 45) * ((float)Math.PI / 180F) - (float)Math.PI) * 2, pPlayer.getY() + 1.25, pPlayer.getZ() + Mth.cos((pPlayer.getYRot() - 45) * ((float)Math.PI / 180F) - (float)Math.PI) * -2);
            fireballNeg45.DespawnDuration = 400;
            fireballNeg45.FlyDuration = 400;
            fireballNeg45.explosionPower = 2f;
            pLevel.addFreshEntity(fireballNeg45);
        }

        pPlayer.getItemInHand(pUsedHand).hurtAndBreak(1, pPlayer, pUsedHand == net.minecraft.world.InteractionHand.MAIN_HAND ? net.minecraft.world.entity.EquipmentSlot.MAINHAND : net.minecraft.world.entity.EquipmentSlot.OFFHAND);

        pPlayer.getCooldowns().addCooldown(this, 160);
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pItemstack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (ItemUtil.ItemKeys.isHoldingShift()) {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.searing_staff.shift_desc_1"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.searing_staff.shift_desc_2"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.searing_staff.shift_desc_3"));
            pTooltipComponents.add(Component.translatable("item.remnantrelics.searing_staff.shift_desc_4"));
        } else {
            pTooltipComponents.add(Component.translatable("item.remnantrelics.shift_desc_info"));
        }
        super.appendHoverText(pItemstack, pContext, pTooltipComponents, pIsAdvanced);
    }
}
