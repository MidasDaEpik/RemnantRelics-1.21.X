package com.midasdaepik.remnantrelics.entity.renderer;

import com.midasdaepik.remnantrelics.entity.custom.SonicBlast;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SonicBlastRenderer extends EntityRenderer<SonicBlast> {
	public SonicBlastRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public void render(SonicBlast entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(SonicBlast pEntity) {
		return null;
	}
}
