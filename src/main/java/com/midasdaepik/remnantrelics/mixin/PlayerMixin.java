package com.midasdaepik.remnantrelics.mixin;

import com.midasdaepik.remnantrelics.registries.RRTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "getItemBySlot", at = @At("HEAD"), cancellable = true)
    private void getItemBySlot(EquipmentSlot pEquipmentSlot, CallbackInfoReturnable<ItemStack> pReturn) {
        Player pThis = (Player) (Object) this;
        ItemStack pMainhand = pThis.getInventory().getSelected();
        ItemStack pOffhand = pThis.getInventory().offhand.get(0);
        if (pEquipmentSlot == EquipmentSlot.OFFHAND && (pMainhand.is(RRTags.DUAL_WIELDED_WEAPONS) || pOffhand.is(RRTags.DUAL_WIELDED_WEAPONS))) {
            pReturn.setReturnValue(ItemStack.EMPTY);
            pReturn.cancel();
        }
    }
}