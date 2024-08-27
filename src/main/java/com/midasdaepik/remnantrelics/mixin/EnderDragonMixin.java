package com.midasdaepik.remnantrelics.mixin;

import com.midasdaepik.remnantrelics.registries.Items;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndDragonFight.class)
public class EnderDragonMixin {
    @Inject(method = "setDragonKilled", at = @At("HEAD"))
    private void spawnBossLoot(EnderDragon pDragon, CallbackInfo pCallbackInfo) {
        Level pLevel = pDragon.level();
        Vec3 pPodium = pLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.getLocation(pDragon.getFightOrigin())).getCenter();

        ItemEntity pDragonLoot = new ItemEntity(pLevel, pPodium.x, pPodium.y + 4, pPodium.z, Items.DRAGONBONE.toStack());
        pDragonLoot.setDeltaMovement(0.0, 0.0, 0.0);
        pDragonLoot.setGlowingTag(true);
        pDragonLoot.setNoGravity(true);
        pDragonLoot.setUnlimitedLifetime();
        pLevel.addFreshEntity(pDragonLoot);
    }
}