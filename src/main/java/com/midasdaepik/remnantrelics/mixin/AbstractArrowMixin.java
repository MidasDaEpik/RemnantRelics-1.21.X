package com.midasdaepik.remnantrelics.mixin;

import com.midasdaepik.remnantrelics.entity.Firestorm;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.midasdaepik.remnantrelics.registries.RRAttachmentTypes.SPECIAL_ARROW_TYPE;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {
    @Inject(method = "onHitEntity", at = @At("HEAD"))
    private void spawnSpecialArrowHitEntity(EntityHitResult pResult, CallbackInfo pCallbackInfo) {
        AbstractArrow pThis = (AbstractArrow) (Object) this;
        if (pThis.getData(SPECIAL_ARROW_TYPE) == 0) {
            Entity pOwner = pThis.getOwner();
            if (pOwner instanceof LivingEntity pLivingEntityOwner) {
                Vec3 Motion = pThis.getDeltaMovement();
                Firestorm firestorm = new Firestorm(pThis.level(), pLivingEntityOwner, 200, 20, false);
                firestorm.setPos(pThis.getX() - Motion.x, pThis.getY() - Motion.y, pThis.getZ() - Motion.z);
                pThis.level().addFreshEntity(firestorm);
            }
        }
    }

    @Inject(method = "onHitBlock", at = @At("HEAD"))
    private void spawnSpecialArrowHitEntity(BlockHitResult pResult, CallbackInfo pCallbackInfo) {
        AbstractArrow pThis = (AbstractArrow) (Object) this;
        if (pThis.getData(SPECIAL_ARROW_TYPE) == 0) {
            Entity pOwner = pThis.getOwner();
            if (pOwner instanceof LivingEntity pLivingEntityOwner) {
                Vec3 Motion = pThis.getDeltaMovement();
                Firestorm firestorm = new Firestorm(pThis.level(), pLivingEntityOwner, 200, 20, false);
                firestorm.setPos(pThis.getX() - Motion.x, pThis.getY() - Motion.y, pThis.getZ() - Motion.z);
                pThis.level().addFreshEntity(firestorm);
            }
        }
    }
}