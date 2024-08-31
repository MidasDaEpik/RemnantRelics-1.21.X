package com.midasdaepik.remnantrelics.mixin;

import com.midasdaepik.remnantrelics.entity.DragonsBreath;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
                Entity Target = pResult.getEntity();
                Vec3 SpawnLocation = new Vec3(Target.getX(), Target.getY(), Target.getZ());

                if (pThis.level() instanceof ServerLevel pServerLevel) {
                    pServerLevel.playSeededSound(null, SpawnLocation.x, SpawnLocation.y, SpawnLocation.z, SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.PLAYERS, 2f, 1.2f,0);

                    pServerLevel.sendParticles(ParticleTypes.DRAGON_BREATH, SpawnLocation.x, SpawnLocation.y, SpawnLocation.z, 12, 0.1, 0.1, 0.1, 0.5);
                }

                DragonsBreath dragonsBreath = new DragonsBreath(pThis.level(), pLivingEntityOwner, 160, -20, 6 + pThis.getPierceLevel(), SpawnLocation);
                pThis.level().addFreshEntity(dragonsBreath);
            }
        }
    }

    @Inject(method = "onHitBlock", at = @At("HEAD"))
    private void spawnSpecialArrowHitEntity(BlockHitResult pResult, CallbackInfo pCallbackInfo) {
        AbstractArrow pThis = (AbstractArrow) (Object) this;
        if (pThis.getData(SPECIAL_ARROW_TYPE) == 0) {
            Entity pOwner = pThis.getOwner();
            if (pOwner instanceof LivingEntity pLivingEntityOwner) {
                Vec3 SpawnLocation = pResult.getLocation();

                if (pThis.level() instanceof ServerLevel pServerLevel) {
                    pServerLevel.playSeededSound(null, SpawnLocation.x, SpawnLocation.y, SpawnLocation.z, SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.PLAYERS, 2f, 1.2f,0);

                    pServerLevel.sendParticles(ParticleTypes.DRAGON_BREATH, SpawnLocation.x, SpawnLocation.y, SpawnLocation.z, 12, 0.1, 0.1, 0.1, 0.5);
                }

                DragonsBreath dragonsBreath = new DragonsBreath(pThis.level(), pLivingEntityOwner, 160, -20, 6 + pThis.getPierceLevel(), SpawnLocation);
                pThis.level().addFreshEntity(dragonsBreath);
            }
        }
    }
}