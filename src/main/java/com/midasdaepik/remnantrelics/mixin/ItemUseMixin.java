package com.midasdaepik.remnantrelics.mixin;

import com.midasdaepik.remnantrelics.registries.Tags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemUseMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void stopItemUse(Level pLevel, Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> pReturn) {
        if (pPlayer != null && pPlayer.getMainHandItem().is(Tags.DUAL_WIELDED_WEAPONS) && pHand == InteractionHand.OFF_HAND) {
            pReturn.setReturnValue(InteractionResultHolder.fail(pPlayer.getItemInHand(pHand)));
        }
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void stopItemUseOn(UseOnContext pContext, CallbackInfoReturnable<InteractionResult> pReturn) {
        InteractionHand pHand = pContext.getHand();
        Player pPlayer = pContext.getPlayer();
        if (pPlayer != null && pPlayer.getMainHandItem().is(Tags.DUAL_WIELDED_WEAPONS) && pHand == InteractionHand.OFF_HAND) {
            pReturn.setReturnValue(InteractionResult.FAIL);
        }
    }
}
