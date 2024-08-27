package com.midasdaepik.remnantrelics.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.midasdaepik.remnantrelics.registries.RRItems;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @WrapOperation(method = "tickFov", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier()F"))
    private float modifyFov(AbstractClientPlayer pInstance, Operation<Float> pOriginal) {
        float f = pOriginal.call(pInstance);
        ItemStack itemstack = pInstance.getUseItem();
        if (pInstance.isUsingItem()) {
            if (itemstack.is(RRItems.WHISPERWIND)) {
                int i = pInstance.getTicksUsingItem();
                float f1 = (float)i / 15.0F;
                if (f1 > 1.0F) {
                    f1 = 1.0F;
                } else {
                    f1 *= f1;
                }

                f *= 1.0F - f1 * 0.15F;
            }
        }
        return f;
    }
}