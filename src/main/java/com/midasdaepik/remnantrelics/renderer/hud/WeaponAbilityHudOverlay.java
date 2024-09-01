package com.midasdaepik.remnantrelics.renderer.hud;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.registries.RRItems;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import static com.midasdaepik.remnantrelics.registries.RRAttachmentTypes.CHARYBDIS_CHARGE;
import static com.midasdaepik.remnantrelics.registries.RRAttachmentTypes.DRAGONS_RAGE_CHARGE;

public class WeaponAbilityHudOverlay implements LayeredDraw.Layer {
	private static final ResourceLocation CHARYBDIS_BACKGROUND_SPRITE = ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "hud/charybdis_bar_background");
	private static final ResourceLocation CHARYBDIS_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "hud/charybdis_bar_progress");

	private static final ResourceLocation DRAGONS_RAGE_BACKGROUND_SPRITE = ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "hud/dragons_rage_bar_background");
	private static final ResourceLocation DRAGONS_RAGE_PROGRESS_SPRITE = ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "hud/dragons_rage_bar_progress");
	private static final ResourceLocation DRAGONS_RAGE_FULL_0_SPRITE = ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "hud/dragons_rage_bar_full_0");
	private static final ResourceLocation DRAGONS_RAGE_FULL_1_SPRITE = ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "hud/dragons_rage_bar_full_1");
	private static final ResourceLocation DRAGONS_RAGE_FULL_2_SPRITE = ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "hud/dragons_rage_bar_full_2");
	private static final ResourceLocation DRAGONS_RAGE_FULL_3_SPRITE = ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "hud/dragons_rage_bar_full_3");
	private static final ResourceLocation DRAGONS_RAGE_FULL_4_SPRITE = ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "hud/dragons_rage_bar_full_4");

	private final Minecraft minecraft;

	public WeaponAbilityHudOverlay(Minecraft pMinecraft) {
		this.minecraft = pMinecraft;
	}

	@Override
	public void render(GuiGraphics pGuiGraphics, DeltaTracker pDeltaTracker) {
		Player pPlayer = this.minecraft.player;
		ClientLevel pLevel = this.minecraft.level;
		if (pPlayer != null && pLevel != null) {
			if (pPlayer.getMainHandItem().getItem() == RRItems.DRAGONS_RAGE.get()) {
				int x = pGuiGraphics.guiWidth() / 2;
				int y = pGuiGraphics.guiHeight() - 39 - 32;
				int DragonsRageCharge = pPlayer.getData(DRAGONS_RAGE_CHARGE);
				int height = 30 - Mth.clamp(Mth.floor(DragonsRageCharge / 100f), 0, 18);
				if (DragonsRageCharge > 0) {
					height -= 1;
				}
				this.minecraft.getProfiler().push("weapon_ability_hud_overlay");

				RenderSystem.enableBlend();
				if (DragonsRageCharge == 1800) {
					double Timer = pLevel.getGameTime();
					if (Timer % 29 == 0 || Timer % 29 == 1) {
						pGuiGraphics.blitSprite(DRAGONS_RAGE_FULL_0_SPRITE, x - 10, y, 20, 32);
					} else if (Timer % 29 == 2 || Timer % 29 == 3) {
						pGuiGraphics.blitSprite(DRAGONS_RAGE_FULL_1_SPRITE, x - 10, y, 20, 32);
					} else if (Timer % 29 == 4 || Timer % 29 == 5) {
						pGuiGraphics.blitSprite(DRAGONS_RAGE_FULL_2_SPRITE, x - 10, y, 20, 32);
					} else if (Timer % 29 == 6 || Timer % 29 == 7) {
						pGuiGraphics.blitSprite(DRAGONS_RAGE_FULL_3_SPRITE, x - 10, y, 20, 32);
					} else if (Timer % 29 == 8 || Timer % 29 == 9) {
						pGuiGraphics.blitSprite(DRAGONS_RAGE_FULL_4_SPRITE, x - 10, y, 20, 32);
					} else {
						pGuiGraphics.blitSprite(DRAGONS_RAGE_PROGRESS_SPRITE, x - 8, y, 16, 32);
					}

				} else {
					pGuiGraphics.blitSprite(DRAGONS_RAGE_PROGRESS_SPRITE, x - 8, y, 16, 32);
					pGuiGraphics.blitSprite(DRAGONS_RAGE_BACKGROUND_SPRITE, 16, 32, 0, 0, x - 8, y, 18, height);
				}
				RenderSystem.disableBlend();

				this.minecraft.getProfiler().pop();

			} else if (pPlayer.getMainHandItem().getItem() == RRItems.CHARYBDIS.get()) {
				int x = pGuiGraphics.guiWidth() / 2;
				int y = pGuiGraphics.guiHeight() - 39 - 16 - 8;
				int CharybdisCharge = pPlayer.getData(CHARYBDIS_CHARGE);
				int height = 15 - Mth.clamp(Mth.floor(CharybdisCharge / 100f), 0, 14);

				this.minecraft.getProfiler().push("weapon_ability_hud_overlay");

				RenderSystem.enableBlend();
				pGuiGraphics.blitSprite(CHARYBDIS_PROGRESS_SPRITE, x - 8, y, 18, 18);
				pGuiGraphics.blitSprite(CHARYBDIS_BACKGROUND_SPRITE, 18, 18, 0, 0, x - 8, y, 18, height);
				RenderSystem.disableBlend();

				this.minecraft.getProfiler().pop();
			}
		}
	}
}
