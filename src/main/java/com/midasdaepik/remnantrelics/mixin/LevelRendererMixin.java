package com.midasdaepik.remnantrelics.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.midasdaepik.remnantrelics.registries.RRItems;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getTeamColor()I"))
    private int modifyTeamColor(Entity pEntity, Operation<Integer> pOriginal) {
        int pOriginalValue = pOriginal.call(pEntity);
        if (pEntity instanceof ItemEntity pItemEntity && pItemEntity.getItem().getItem() == RRItems.MOD_ICON.get()) {
            if (pOriginalValue == 16777215) {
                return 13719531;
            } else {
                return pOriginalValue;
            }
        } else {
            return pOriginalValue;
        }
    }
}