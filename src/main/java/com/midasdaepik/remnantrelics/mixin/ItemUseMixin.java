package com.midasdaepik.remnantrelics.mixin;

import com.midasdaepik.remnantrelics.registries.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemUseMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void stopItemUseOn(UseOnContext pContext, CallbackInfoReturnable<InteractionResult> pReturn) {
        InteractionHand pHand = pContext.getHand();
        Player pPlayer = pContext.getPlayer();
        if (pPlayer != null && pPlayer.getMainHandItem().getItem() == Items.SOULEATING_SLASHER.get() && pHand == InteractionHand.OFF_HAND) {
            pReturn.setReturnValue(InteractionResult.FAIL);
        }
    }
}
