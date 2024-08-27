package com.midasdaepik.remnantrelics.entity.custom;

import com.midasdaepik.remnantrelics.registries.RREntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class NoDamageFireball extends Fireball {
    public int DespawnDuration = 400;
    public int FlyDuration = 400;
    public float explosionPower = 1;

    public NoDamageFireball(EntityType<? extends NoDamageFireball> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public NoDamageFireball(Level pLevel, LivingEntity pShooter, Vec3 pVec3) {
        super(RREntities.NO_DAMAGE_FIREBALL.get(), pShooter, pVec3, pLevel);
    }

    public NoDamageFireball(Level pLevel, LivingEntity pShooter, Vec3 pVec3, int pDespawnDuration, int pFlyDuration, int pExplosionPower) {
        super(RREntities.NO_DAMAGE_FIREBALL.get(), pShooter, pVec3, pLevel);
        this.DespawnDuration = pDespawnDuration;
        this.FlyDuration = pFlyDuration;
        this.explosionPower = pExplosionPower;
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, false, Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (this.level() instanceof ServerLevel serverlevel) {
            Entity entity1 = pResult.getEntity();
            Entity $$4 = this.getOwner();
            DamageSource $$5 = this.damageSources().fireball(this, $$4);
            entity1.hurt($$5, 6.0F);
            EnchantmentHelper.doPostAttackEffects(serverlevel, entity1, $$5);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.DespawnDuration <= 0) {
                this.setDeltaMovement(0, -0.1, 0);
            }

            if (this.FlyDuration <= 0) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.explosionPower, false, Level.ExplosionInteraction.NONE);
                this.discard();
            }

            if (this.getDeltaMovement().x <= 0.01 && this.getDeltaMovement().y <= 0.01 && this.getDeltaMovement().z <= 0.01) {
                this.DespawnDuration = this.DespawnDuration - 1;
            } else {
                this.FlyDuration = this.FlyDuration - 1;
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.DespawnDuration = pCompound.getInt("DespawnDuration");
        this.FlyDuration = pCompound.getInt("FlyDuration");
        this.explosionPower = pCompound.getFloat("ExplosionPower");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("DespawnDuration", this.DespawnDuration);
        pCompound.putInt("FlyDuration", this.FlyDuration);
        pCompound.putFloat("ExplosionPower", this.explosionPower);
    }
}
