package com.midasdaepik.remnantrelics.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.RRTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @WrapOperation(method = "handlePlayerAction", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack handlePlayerAction(ServerPlayer pPlayer, InteractionHand pInteractionHand, Operation<ItemStack> pOriginal) {
        ItemStack pMainhand = pPlayer.getInventory().getSelected();
        ItemStack pOffhand = pPlayer.getInventory().offhand.get(0);
        if (pMainhand.is(RRTags.DUAL_WIELDED_WEAPONS) || pOffhand.is(RRTags.DUAL_WIELDED_WEAPONS)) {
            ItemStack pItem = null;
            switch (pInteractionHand) {
                case InteractionHand.MAIN_HAND -> {
                    pItem = pMainhand;
                }
                case InteractionHand.OFF_HAND -> {
                    pItem = pOffhand;
                }
            }
            RemnantRelics.LOGGER.debug(pItem.getDescriptionId());
            return pItem;
        } else {
            return pOriginal.call(pPlayer, pInteractionHand);
        }
    }
}