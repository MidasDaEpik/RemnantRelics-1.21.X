package com.midasdaepik.remnantrelics.registries;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import org.lwjgl.glfw.GLFW;

public class ItemUtil {
    public static class ItemKeys {
        private static final long WINDOW = Minecraft.getInstance().getWindow().getWindow();

        public static boolean isHoldingShift() {
            return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SHIFT);
        }

        public static boolean isHoldingControl() {
            return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_CONTROL) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_CONTROL);
        }

        public static boolean isHoldingAlt() {
            return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_ALT) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_ALT);
        }

        public static boolean isHoldingSpace() {
            return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_SPACE);
        }
    }

    public static BlockHitResult BlockHitRaycast(Level pLevel, LivingEntity pLivingEntity, ClipContext.Fluid pFluidMode, int pRange) {
        float XRot = pLivingEntity.getXRot();
        float YRot = pLivingEntity.getYRot();
        Vec3 vec3 = pLivingEntity.getEyePosition(1.0F);

        float XZMult = -Mth.cos(-XRot * ((float)Math.PI / 180F));
        float X = Mth.sin(-YRot * ((float)Math.PI / 180F) - (float)Math.PI) * XZMult;
        float Y = Mth.sin(-XRot * ((float)Math.PI / 180F));
        float Z = Mth.cos(-YRot * ((float)Math.PI / 180F) - (float)Math.PI) * XZMult;

        Vec3 vec31 = vec3.add((double)X * pRange, (double)Y * pRange, (double)Z * pRange);
        return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.OUTLINE, pFluidMode, pLivingEntity));
    }

    public static int getPlayerXP(Player pPlayer) {
        return (int) (getExperienceForLevel(pPlayer.experienceLevel) + pPlayer.experienceProgress * pPlayer.getXpNeededForNextLevel());
    }

    public static void modifyPlayerXP(Player player, int amount) {
        PlayerXpEvent.XpChange eventXP = new PlayerXpEvent.XpChange(player, amount);
        amount = eventXP.getAmount();

        int experience = getPlayerXP(player) + amount;

        player.totalExperience = experience;
        player.experienceLevel = getLevelForExperience(experience);
        int expForLevel = getExperienceForLevel(player.experienceLevel);
        player.experienceProgress = (float) (experience - expForLevel) / (float) player.getXpNeededForNextLevel();
    }

    public static void modifyPlayerXPalt(Player player, int amount) {
        PlayerXpEvent.XpChange eventXP = new PlayerXpEvent.XpChange(player, amount);
        amount = eventXP.getAmount();

        int oldLevel = player.experienceLevel;
        int newLevel = getLevelForExperience(getPlayerXP(player) + amount);
        int experience = getPlayerXP(player) + amount;

        if (oldLevel != newLevel) {
            PlayerXpEvent.LevelChange eventLvl = new PlayerXpEvent.LevelChange(player, newLevel - oldLevel);
            int remainder = experience - getExperienceForLevel(newLevel);
            newLevel = oldLevel + eventLvl.getLevels();
            amount = getExperienceForLevel(newLevel) - getExperienceForLevel(oldLevel) + remainder;
        }

        player.totalExperience = experience;
        player.experienceLevel = getLevelForExperience(experience);
        int expForLevel = getExperienceForLevel(player.experienceLevel);
        player.experienceProgress = (float) (experience - expForLevel) / (float) player.getXpNeededForNextLevel();
    }

    public static int getExperienceForLevel(int XPLevel) {
        if (XPLevel <= 0) {
            return 0;
        } else if (XPLevel < 17) {
            return (XPLevel * XPLevel + 6 * XPLevel);
        } else if (XPLevel < 32) {
            return (int) (2.5 * XPLevel * XPLevel - 40.5 * XPLevel + 360);
        } else {
            return (int) (4.5 * XPLevel * XPLevel - 162.5 * XPLevel + 2220);
        }
    }

    public static int getLevelForExperience(int pExperience) {
        if (pExperience <= 0)
            return 0;

        int i = 0;
        while (getExperienceForLevel(i) <= pExperience) {
            i++;
        }

        return i - 1;
    }

    public static void ParticleCircle(ServerLevel pServerLevel, ParticleOptions pParticle, double pX, double pY, double pZ, double pScale) {
        pServerLevel.sendParticles(pParticle, pX + 1 * pScale, pY, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1 * pScale, pY, pZ, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX, pY, pZ + 1 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY, pZ - 1 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 0.707 * pScale, pY, pZ + 0.707 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.707 * pScale, pY, pZ + 0.707 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 0.707 * pScale, pY, pZ - 0.707 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.707 * pScale, pY, pZ - 0.707 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 0.382 * pScale, pY, pZ + 0.923 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.382 * pScale, pY, pZ + 0.923 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 0.382 * pScale, pY, pZ - 0.923 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.382 * pScale, pY, pZ - 0.923 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 0.923 * pScale, pY, pZ + 0.382 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.923 * pScale, pY, pZ + 0.382 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 0.923 * pScale, pY, pZ - 0.382 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.923 * pScale, pY, pZ - 0.382 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 0.195 * pScale, pY, pZ + 0.980 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.195 * pScale, pY, pZ + 0.980 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 0.195 * pScale, pY, pZ - 0.980 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.195 * pScale, pY, pZ - 0.980 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 0.980 * pScale, pY, pZ + 0.195 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.980 * pScale, pY, pZ + 0.195 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 0.980 * pScale, pY, pZ - 0.195 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.980 * pScale, pY, pZ - 0.195 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 0.555 * pScale, pY, pZ + 0.831 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.555 * pScale, pY, pZ + 0.831 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 0.555 * pScale, pY, pZ - 0.831 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.555 * pScale, pY, pZ - 0.831 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 0.831 * pScale, pY, pZ + 0.555 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.831 * pScale, pY, pZ + 0.555 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 0.831 * pScale, pY, pZ - 0.555 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.831 * pScale, pY, pZ - 0.555 * pScale, 1, 0, 0, 0, 0);
    }

    public static void ParticleSphere(ServerLevel pServerLevel, ParticleOptions pParticle, double pX, double pY, double pZ, double pScale) {
        pServerLevel.sendParticles(pParticle, pX + 2.74 * pScale, pY + 4.43 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 2.74 * pScale, pY - 4.43 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 2.74 * pScale, pY + 4.43 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 2.74 * pScale, pY - 4.43 * pScale, pZ, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 4.43 * pScale, pY,  pZ + 2.74 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 4.43 * pScale, pY,  pZ - 2.74 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 4.43 * pScale, pY,  pZ + 2.74 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 4.43 * pScale, pY,  pZ - 2.74 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX, pY + 2.74 * pScale, pZ + 4.43 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY + 2.74 * pScale, pZ - 4.43 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY - 2.74 * pScale, pZ + 4.43 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY - 2.74 * pScale, pZ - 4.43 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 5 * pScale, pY + 1.9 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 5 * pScale, pY -1.9 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 5 * pScale, pY + 1.9 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 5 * pScale, pY -1.9 * pScale, pZ, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 1.9 * pScale, pY,  pZ + 5 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 1.9 * pScale, pY,  pZ - 5 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.9 * pScale, pY,  pZ + 5 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.9 * pScale, pY,  pZ - 5 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX, pY + 5 * pScale, pZ + 1.9 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY + 5 * pScale, pZ - 1.9 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY - 5 * pScale, pZ + 1.9 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY - 5 * pScale, pZ - 1.9 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 3.09 * pScale, pY + 3.09 * pScale, pZ + 3.09 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 3.09 * pScale, pY + 3.09 * pScale, pZ - 3.09 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 3.09 * pScale, pY - 3.09 * pScale, pZ + 3.09 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 3.09 * pScale, pY - 3.09 * pScale, pZ - 3.09 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX - 3.09 * pScale, pY + 3.09 * pScale, pZ + 3.09 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 3.09 * pScale, pY + 3.09 * pScale, pZ - 3.09 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 3.09 * pScale, pY - 3.09 * pScale, pZ + 3.09 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 3.09 * pScale, pY - 3.09 * pScale, pZ - 3.09 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 5 * pScale, pY, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 5 * pScale, pY, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY + 5 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY - 5 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY,  pZ + 5 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY,  pZ - 5 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 4.715 * pScale, pY + 0.95 * pScale, pZ + 1.37 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 4.715 * pScale, pY + 0.95 * pScale, pZ - 1.37 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 4.715 * pScale, pY - 0.95 * pScale, pZ + 1.37 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 4.715 * pScale, pY - 0.95 * pScale, pZ - 1.37 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX - 4.715 * pScale, pY + 0.95 * pScale, pZ + 1.37 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 4.715 * pScale, pY + 0.95 * pScale, pZ - 1.37 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 4.715 * pScale, pY - 0.95 * pScale, pZ + 1.37 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 4.715 * pScale, pY - 0.95 * pScale, pZ - 1.37 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 0.95 * pScale, pY + 1.37 * pScale, pZ + 4.715 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 0.95 * pScale, pY + 1.37 * pScale, pZ - 4.715 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 0.95 * pScale, pY - 1.37 * pScale, pZ + 4.715 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 0.95 * pScale, pY - 1.37 * pScale, pZ - 4.715 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX - 0.95 * pScale, pY + 1.37 * pScale, pZ + 4.715 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.95 * pScale, pY + 1.37 * pScale, pZ - 4.715 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.95 * pScale, pY - 1.37 * pScale, pZ + 4.715 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 0.95 * pScale, pY - 1.37 * pScale, pZ - 4.715 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 1.37 * pScale, pY + 4.715 * pScale, pZ + 0.95 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 1.37 * pScale, pY + 4.715 * pScale, pZ - 0.95 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 1.37 * pScale, pY - 4.715 * pScale, pZ + 0.95 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 1.37 * pScale, pY - 4.715 * pScale, pZ - 0.95 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX - 1.37 * pScale, pY + 4.715 * pScale, pZ + 0.95 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.37 * pScale, pY + 4.715 * pScale, pZ - 0.95 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.37 * pScale, pY - 4.715 * pScale, pZ + 0.95 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.37 * pScale, pY - 4.715 * pScale, pZ - 0.95 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX, pY + 3.87 * pScale, pZ + 3.165 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY + 3.87 * pScale, pZ - 3.165 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY - 3.87 * pScale, pZ + 3.165 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX, pY - 3.87 * pScale, pZ - 3.165 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 3.87 * pScale, pY + 3.165 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 3.87 * pScale, pY - 3.165 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 3.87 * pScale, pY + 3.165 * pScale, pZ, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 3.87 * pScale, pY - 3.165 * pScale, pZ, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 3.165 * pScale, pY,  pZ + 3.87 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 3.165 * pScale, pY,  pZ - 3.87 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 3.165 * pScale, pY,  pZ + 3.87 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 3.165 * pScale, pY,  pZ - 3.87 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 4.045 * pScale, pY + 2.495 * pScale, pZ + 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 4.045 * pScale, pY + 2.495 * pScale, pZ - 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 4.045 * pScale, pY - 2.495 * pScale, pZ + 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 4.045 * pScale, pY - 2.495 * pScale, pZ - 1.545 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX - 4.045 * pScale, pY + 2.495 * pScale, pZ + 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 4.045 * pScale, pY + 2.495 * pScale, pZ - 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 4.045 * pScale, pY - 2.495 * pScale, pZ + 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 4.045 * pScale, pY - 2.495 * pScale, pZ - 1.545 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 2.495 * pScale, pY + 1.545 * pScale, pZ + 4.045 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 2.495 * pScale, pY + 1.545 * pScale, pZ - 4.045 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 2.495 * pScale, pY - 1.545 * pScale, pZ + 4.045 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 2.495 * pScale, pY - 1.545 * pScale, pZ - 4.045 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX - 2.495 * pScale, pY + 1.545 * pScale, pZ + 4.045 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 2.495 * pScale, pY + 1.545 * pScale, pZ - 4.045 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 2.495 * pScale, pY - 1.545 * pScale, pZ + 4.045 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 2.495 * pScale, pY - 1.545 * pScale, pZ - 4.045 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 1.545 * pScale, pY + 4.045 * pScale, pZ + 2.495 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 1.545 * pScale, pY + 4.045 * pScale, pZ - 2.495 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 1.545 * pScale, pY - 4.045 * pScale, pZ + 2.495 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 1.545 * pScale, pY - 4.045 * pScale, pZ - 2.495 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX - 1.545 * pScale, pY + 4.045 * pScale, pZ + 2.495 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.545 * pScale, pY + 4.045 * pScale, pZ - 2.495 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.545 * pScale, pY - 4.045 * pScale, pZ + 2.495 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.545 * pScale, pY - 4.045 * pScale, pZ - 2.495 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 2.915 * pScale, pY + 3.76 * pScale, pZ + 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 2.915 * pScale, pY + 3.76 * pScale, pZ - 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 2.915 * pScale, pY - 3.76 * pScale, pZ + 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 2.915 * pScale, pY - 3.76 * pScale, pZ - 1.545 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX - 2.915 * pScale, pY + 3.76 * pScale, pZ + 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 2.915 * pScale, pY + 3.76 * pScale, pZ - 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 2.915 * pScale, pY - 3.76 * pScale, pZ + 1.545 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 2.915 * pScale, pY - 3.76 * pScale, pZ - 1.545 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 3.76 * pScale, pY + 1.545 * pScale, pZ + 2.915 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 3.76 * pScale, pY + 1.545 * pScale, pZ - 2.915 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 3.76 * pScale, pY - 1.545 * pScale, pZ + 2.915 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 3.76 * pScale, pY - 1.545 * pScale, pZ - 2.915 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX - 3.76 * pScale, pY + 1.545 * pScale, pZ + 2.915 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 3.76 * pScale, pY + 1.545 * pScale, pZ - 2.915 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 3.76 * pScale, pY - 1.545 * pScale, pZ + 2.915 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 3.76 * pScale, pY - 1.545 * pScale, pZ - 2.915 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX + 1.545 * pScale, pY + 2.915 * pScale, pZ + 3.76 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 1.545 * pScale, pY + 2.915 * pScale, pZ - 3.76 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 1.545 * pScale, pY - 2.915 * pScale, pZ + 3.76 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX + 1.545 * pScale, pY - 2.915 * pScale, pZ - 3.76 * pScale, 1, 0, 0, 0, 0);

        pServerLevel.sendParticles(pParticle, pX - 1.545 * pScale, pY + 2.915 * pScale, pZ + 3.76 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.545 * pScale, pY + 2.915 * pScale, pZ - 3.76 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.545 * pScale, pY - 2.915 * pScale, pZ + 3.76 * pScale, 1, 0, 0, 0, 0);
        pServerLevel.sendParticles(pParticle, pX - 1.545 * pScale, pY - 2.915 * pScale, pZ - 3.76 * pScale, 1, 0, 0, 0, 0);
    }
}
