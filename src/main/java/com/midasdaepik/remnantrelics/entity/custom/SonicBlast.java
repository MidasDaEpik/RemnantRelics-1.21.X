package com.midasdaepik.remnantrelics.entity.custom;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.Entities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SonicBlast extends Projectile {
    public int Duration = 200;
    public int AttackDamage = 10;

    public SonicBlast(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noCulling = true;
    }

    public SonicBlast(Level pLevel, LivingEntity pShooter, int pDuration) {
        super(Entities.SONIC_BLAST.get(), pLevel);
        this.setOwner(pShooter);
        this.Duration = pDuration;
    }

    public SonicBlast(Level pLevel, LivingEntity pShooter, int pDuration, int pAttackDamage) {
        super(Entities.SONIC_BLAST.get(), pLevel);
        this.setOwner(pShooter);
        this.Duration = pDuration;
        this.AttackDamage = pAttackDamage;
    }

    @Override
    public void tick() {
        if (this.level() instanceof ServerLevel pServerLevel) {
            super.tick();

            if (this.Duration % 2 == 0) {
                pServerLevel.sendParticles(ParticleTypes.SONIC_BOOM, this.getX(), this.getY() + 0.5, this.getZ(), 1, 0, 0, 0, 0);
            }

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
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            this.setPos(d0, d1, d2);

            ProjectileUtil.rotateTowardsMovement(this, 0.4F);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (!this.level().isClientSide) {
            pResult.getEntity().hurt(new DamageSource(this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "sonic_boom"))), this.getOwner()), this.AttackDamage);
        }
    }

    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.Duration = pCompound.getInt("Duration");
        this.AttackDamage = pCompound.getInt("AttackDamage");
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("Duration", this.Duration);
        pCompound.putInt("AttackDamage", this.AttackDamage);
    }
}
