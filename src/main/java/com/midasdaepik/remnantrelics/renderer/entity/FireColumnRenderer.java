package com.midasdaepik.remnantrelics.renderer.entity;

import com.midasdaepik.remnantrelics.entity.FireColumn;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireColumnRenderer extends EntityRenderer<FireColumn> {
	public FireColumnRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(FireColumn entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(FireColumn pEntity) {
		return null;
	}
}
