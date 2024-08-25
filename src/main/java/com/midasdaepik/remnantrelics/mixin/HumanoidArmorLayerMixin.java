package com.midasdaepik.remnantrelics.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.midasdaepik.remnantrelics.registries.Items;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {
    @WrapOperation(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/DyedItemColor;getOrDefault(Lnet/minecraft/world/item/ItemStack;I)I"))
    private int getDefaultDyeColor(ItemStack pItemStack, int pDefaultValue, Operation<Integer> pOriginal) {
        if (pItemStack.getItem() == Items.ELDER_CHESTPLATE.get()) {
            if (pOriginal.call(pItemStack, pDefaultValue) == -6265536) {
                return 6448520;
            } else {
                return pOriginal.call(pItemStack, pDefaultValue);
            }
        } else {
            return pOriginal.call(pItemStack, pDefaultValue);
        }
    }
}