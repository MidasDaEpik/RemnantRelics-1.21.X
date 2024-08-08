package com.midasdaepik.remnantrelics.entity.custom;

import com.midasdaepik.remnantrelics.registries.Entities;
import com.midasdaepik.remnantrelics.registries.ItemUtil;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Comparator;
import java.util.List;

public class Firestorm extends Projectile {
    public int Duration = 200;
    public int EffectDuration = 40;
    public boolean WitherSpore = false;

    public Firestorm(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Firestorm(Level pLevel, LivingEntity pShooter, int pDuration, int pEffectDuration, boolean pWitherSpore) {
        super(Entities.FIRESTORM.get(), pLevel);
        this.setOwner(pShooter);
        this.Duration = pDuration;
        this.EffectDuration = pEffectDuration;
        this.WitherSpore = pWitherSpore;
    }

    @Override
    public void tick() {
        super.tick();

        this.AttackTick();

        if (this.level() instanceof ServerLevel pServerLevel) {
            this.Duration = this.Duration - 1;
            if (this.Duration <= 0) {
                this.discard();
            }

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity, ClipContext.Block.COLLIDER);
            if (hitresult.getType() != HitResult.Type.MISS && !net.neoforged.neoforge.event.EventHooks.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x * 0.1;
            double d1 = this.getY() + vec3.y * 0.05;
            double d2 = this.getZ() + vec3.z * 0.1;
            this.setPos(d0, d1, d2);

            ProjectileUtil.rotateTowardsMovement(this, 0.4F);
        }
    }

    protected void AttackTick() {
        if (this.level() instanceof ServerLevel pServerLevel) {
            if (this.WitherSpore) {
                pServerLevel.sendParticles(ParticleTypes.CRIMSON_SPORE, this.getX(), this.getY() + 0.25, this.getZ(), 2, 1.25, 1.25, 1.25, 0.02);
                pServerLevel.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER, this.getX(), this.getY() + 0.25, this.getZ(), 1, 1.25, 1.25, 1.25, 0.02);
            } else {
                pServerLevel.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY() + 0.25, this.getZ(), 1, 1.25, 1.25, 1.25, 0.02);
                pServerLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 0.25, this.getZ(), 1, 1.25, 1.25, 1.25, 0.02);
            }

            if (this.Duration % 20 == 0) {
                pServerLevel.playSeededSound(null, this.getX(), this.getY() + 0.25, this.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.NEUTRAL, 1f, 0.9f,0);

                final Vec3 _center = new Vec3(this.getX(), this.getY() + 0.25, this.getZ());
                List<LivingEntity> pFoundTarget = pServerLevel.getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center).inflate(5d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (LivingEntity pEntityIterator : pFoundTarget) {
                    if (pEntityIterator != this.getOwner()) {
                        if (this.WitherSpore) {
                            pEntityIterator.addEffect(new MobEffectInstance(MobEffects.WITHER, this.EffectDuration, 2));
                            pEntityIterator.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, this.EffectDuration, 0));
                        } else {
                            if (pEntityIterator.getRemainingFireTicks() < this.EffectDuration) {
                                pEntityIterator.setRemainingFireTicks(this.EffectDuration);
                            }
                            pEntityIterator.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, this.EffectDuration, 0));
                        }
                    }
                }

                if (this.WitherSpore) {
                    ItemUtil.ParticleSphere(pServerLevel, new DustColorTransitionOptions(new Vector3f(0.772f,0.203f,0.223f), new Vector3f(0.482f,0f,0f), 0.9f), this.getX(), this.getY(), this.getZ(), 1);
                } else {
                    ItemUtil.ParticleSphere(pServerLevel, ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 1);
                }
            }
        }
    }

    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.Duration = pCompound.getInt("Duration");
        this.EffectDuration = pCompound.getInt("EffectDuration");
        this.WitherSpore = pCompound.getBoolean("WitherSpore");
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("Duration", this.Duration);
        pCompound.putInt("EffectDuration", this.EffectDuration);
        pCompound.putBoolean("WitherSpore", this.WitherSpore);
    }
}
