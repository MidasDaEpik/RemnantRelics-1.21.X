package com.midasdaepik.remnantrelics.mixin;

import com.midasdaepik.remnantrelics.entity.DragonsBreath;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
    @Inject(method = "tick", at = @At("HEAD"))
    private void SpecialArrowTick(CallbackInfo ci) {
        AbstractArrow pThis = (AbstractArrow) (Object) this;
        if (pThis.getData(SPECIAL_ARROW_TYPE) == 0) {
            if (pThis.level() instanceof ServerLevel pServerLevel && !pThis.onGround()) {
                pServerLevel.sendParticles(ParticleTypes.DRAGON_BREATH, pThis.getX(), pThis.getY(), pThis.getZ(), 1, 0, 0, 0, 0);
            }
        }
    }

    @Inject(method = "onHitEntity", at = @At("HEAD"))
    private void spawnSpecialArrowOnHitEntity(EntityHitResult pResult, CallbackInfo pCallbackInfo) {
        AbstractArrow pThis = (AbstractArrow) (Object) this;
        if (pThis.getData(SPECIAL_ARROW_TYPE) == 0) {
            Entity pOwner = pThis.getOwner();
            if (pThis.level() instanceof ServerLevel pServerLevel && pOwner instanceof LivingEntity pLivingEntityOwner) {
                Entity Target = pResult.getEntity();
                Vec3 SpawnLocation = new Vec3(Target.getX(), Target.getY(), Target.getZ());

                ItemStack pWeaponItem = pThis.getWeaponItem();
                int pProjectileCount = 0;
                if (pWeaponItem != null) {
                    pProjectileCount = EnchantmentHelper.processProjectileCount(pServerLevel, pWeaponItem, pLivingEntityOwner, 1) - 1;
                }

                DragonsBreath dragonsBreath = new DragonsBreath(pThis.level(), pLivingEntityOwner, 160, -20, 6 + pThis.getPierceLevel() - pProjectileCount, SpawnLocation);
                pThis.level().addFreshEntity(dragonsBreath);

                pServerLevel.playSeededSound(null, SpawnLocation.x, SpawnLocation.y, SpawnLocation.z, SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.NEUTRAL, 0.8f, 1.2f,0);
                pServerLevel.sendParticles(ParticleTypes.DRAGON_BREATH, SpawnLocation.x, SpawnLocation.y, SpawnLocation.z, 16, 0.1, 0.1, 0.1, 0.05);
            }
        }
    }

    @Inject(method = "onHitBlock", at = @At("HEAD"))
    private void spawnSpecialArrowOnHitBlock(BlockHitResult pResult, CallbackInfo pCallbackInfo) {
        AbstractArrow pThis = (AbstractArrow) (Object) this;
        if (pThis.getData(SPECIAL_ARROW_TYPE) == 0) {
            Entity pOwner = pThis.getOwner();
            if (pThis.level() instanceof ServerLevel pServerLevel && pOwner instanceof LivingEntity pLivingEntityOwner) {
                Vec3 SpawnLocation = pResult.getLocation();

                ItemStack pWeaponItem = pThis.getWeaponItem();
                int pProjectileCount = 0;
                if (pWeaponItem != null) {
                    pProjectileCount = EnchantmentHelper.processProjectileCount(pServerLevel, pWeaponItem, pLivingEntityOwner, 1) - 1;
                }

                DragonsBreath dragonsBreath = new DragonsBreath(pThis.level(), pLivingEntityOwner, 160, -20, 6 + pThis.getPierceLevel() - pProjectileCount, SpawnLocation);
                pThis.level().addFreshEntity(dragonsBreath);

                pServerLevel.playSeededSound(null, SpawnLocation.x, SpawnLocation.y, SpawnLocation.z, SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.NEUTRAL, 0.8f, 1.2f,0);
                pServerLevel.sendParticles(ParticleTypes.DRAGON_BREATH, SpawnLocation.x, SpawnLocation.y, SpawnLocation.z, 16, 0.1, 0.1, 0.1, 0.05);
            }
        }
    }
}