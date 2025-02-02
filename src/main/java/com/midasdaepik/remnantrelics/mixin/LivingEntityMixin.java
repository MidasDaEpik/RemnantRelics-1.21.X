package com.midasdaepik.remnantrelics.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.midasdaepik.remnantrelics.registries.RRAttachmentTypes.PYROSWEEP_DASH;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "shouldDiscardFriction", at = @At("HEAD"), cancellable = true)
    private void shouldDiscardFriction(CallbackInfoReturnable<Boolean> pCallbackInfo) {
        LivingEntity pThis = (LivingEntity) (Object) this;
        if (pThis.getData(PYROSWEEP_DASH) > 0) {
            pCallbackInfo.setReturnValue(true);
        }
    }
}