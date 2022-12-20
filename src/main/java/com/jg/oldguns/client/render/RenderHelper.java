package com.jg.oldguns.client.render;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.render.types.RenderTypes;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

public class RenderHelper {
	
	public static final ResourceLocation HITMARKER = new ResourceLocation(OldGuns.MODID,
			"textures/effects/hitmarker.png");

	public static final ResourceLocation MUZZLE = new ResourceLocation(OldGuns.MODID,
			"textures/effects/muzzle_flash.png");
	
	// Render images

		public static void renderMuzzleFlash(PoseStack stack, BufferSource impl, float partialTicks, float xo, float yo,
				float zo) {
			stack.pushPose();
			stack.translate(xo, yo, zo);

			stack.scale(0.03F, 0.03F, 0.0F);

			// float scale = 0.5F + 0.5F * (1.0F - partialTicks);
			// stack.scale(scale, scale, 1.0F);
			float r = (float) (360f * Math.random());
			// System.out.println(r);
			stack.mulPose(Vector3f.ZP.rotationDegrees(r));
			stack.translate(-8 / 2, -8 / 2, 0);

			float minU = 0.0f;
			float maxU = 0.5f;
			Matrix4f matrix = stack.last().pose();
			VertexConsumer builder = impl.getBuffer(RenderTypes.MUZZLE_FLASH);
			builder.vertex(matrix, 0, 0, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(maxU, 1.0F).uv2(15728880).endVertex();
			builder.vertex(matrix, 8, 0, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(minU, 1.0F).uv2(15728880).endVertex();
			builder.vertex(matrix, 8, 8, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(minU, 0).uv2(15728880).endVertex();
			builder.vertex(matrix, 0, 8, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(maxU, 0).uv2(15728880).endVertex();

			stack.popPose();
		}

		public static void drawHitmarker(PoseStack stack, ResourceLocation image, int size) {

			RenderSystem.enableBlend();

			BufferBuilder buffer = Tesselator.getInstance().getBuilder();
			int window_width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
			int window_height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

			stack.pushPose();
			RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShaderTexture(0, HITMARKER);
			Matrix4f matrix = stack.last().pose();
			stack.translate((window_width - size) / 2F, (window_height - size) / 2F, 0);
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
			buffer.vertex(matrix, 0, size, 0).uv(0, 1).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.vertex(matrix, size, size, 0).uv(1, 1).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.vertex(matrix, size, 0, 0).uv(1, 0).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			BufferUploader.drawWithShader(buffer.end());
			stack.popPose();
		}
	
	public static void renderPlayerArm(PoseStack matrix, MultiBufferSource buffer, int light, float p_109350_,
			float p_109351_, HumanoidArm p_109352_) {
		boolean flag = p_109352_ != HumanoidArm.LEFT;
		float f = flag ? 1.0F : -1.0F;
		float f1 = Mth.sqrt(p_109351_);
		float f2 = -0.3F * Mth.sin(f1 * (float) Math.PI);
		float f3 = 0.4F * Mth.sin(f1 * ((float) Math.PI * 2F));
		float f4 = -0.4F * Mth.sin(p_109351_ * (float) Math.PI);
		matrix.translate((double) (f * (f2 + 0.64000005F)), (double) (f3 + -0.6F + p_109350_ * -0.6F),
				(double) (f4 + -0.71999997F));
		matrix.mulPose(Vector3f.YP.rotationDegrees(f * 45.0F));
		float f5 = Mth.sin(p_109351_ * p_109351_ * (float) Math.PI);
		float f6 = Mth.sin(f1 * (float) Math.PI);
		matrix.mulPose(Vector3f.YP.rotationDegrees(f * f6 * 70.0F));
		matrix.mulPose(Vector3f.ZP.rotationDegrees(f * f5 * -20.0F));
		AbstractClientPlayer abstractclientplayer = Minecraft.getInstance().player;
		RenderSystem.setShaderTexture(0, abstractclientplayer.getSkinTextureLocation());
		matrix.translate((double) (f * -1.0F), (double) 3.6F, 3.5D);
		matrix.mulPose(Vector3f.ZP.rotationDegrees(f * 120.0F));
		matrix.mulPose(Vector3f.XP.rotationDegrees(200.0F));
		matrix.mulPose(Vector3f.YP.rotationDegrees(f * -135.0F));
		matrix.translate((double) (f * 5.6F), 0.0D, 0.0D);
		PlayerRenderer playerrenderer = (PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher()
				.<AbstractClientPlayer>getRenderer(abstractclientplayer);
		if (flag) {
			playerrenderer.renderRightHand(matrix, buffer, light, abstractclientplayer);
		} else {
			playerrenderer.renderLeftHand(matrix, buffer, light, abstractclientplayer);
		}
	}

	// GUI

	public static void blit(PoseStack p_93201_, int p_93202_, int p_93203_, int p_93204_, int p_93205_, int p_93206_,
			TextureAtlasSprite p_93207_) {
		innerBlit(p_93201_.last().pose(), p_93202_, p_93202_ + p_93205_, p_93203_, p_93203_ + p_93206_, p_93204_,
				p_93207_.getU0(), p_93207_.getU1(), p_93207_.getV0(), p_93207_.getV1());
	}

	public void blit(PoseStack p_93229_, int p_93230_, int p_93231_, int p_93232_, int p_93233_, int p_93234_,
			int p_93235_) {
		blit(p_93229_, p_93230_, p_93231_, -90, (float) p_93232_, (float) p_93233_, p_93234_, p_93235_, 256, 256);
	}

	public static void blit(PoseStack p_93144_, int p_93145_, int p_93146_, int p_93147_, float p_93148_,
			float p_93149_, int p_93150_, int p_93151_, int p_93152_, int p_93153_) {
		innerBlit(p_93144_, p_93145_, p_93145_ + p_93150_, p_93146_, p_93146_ + p_93151_, p_93147_, p_93150_, p_93151_,
				p_93148_, p_93149_, p_93152_, p_93153_);
	}

	public static void blit(PoseStack p_93161_, int p_93162_, int p_93163_, int p_93164_, int p_93165_, float p_93166_,
			float p_93167_, int p_93168_, int p_93169_, int p_93170_, int p_93171_) {
		innerBlit(p_93161_, p_93162_, p_93162_ + p_93164_, p_93163_, p_93163_ + p_93165_, 0, p_93168_, p_93169_,
				p_93166_, p_93167_, p_93170_, p_93171_);
	}

	public static void blit(PoseStack p_93134_, int p_93135_, int p_93136_, float p_93137_, float p_93138_,
			int p_93139_, int p_93140_, int p_93141_, int p_93142_) {
		blit(p_93134_, p_93135_, p_93136_, p_93139_, p_93140_, p_93137_, p_93138_, p_93139_, p_93140_, p_93141_,
				p_93142_);
	}

	private static void innerBlit(PoseStack p_93188_, int p_93189_, int p_93190_, int p_93191_, int p_93192_,
			int p_93193_, int p_93194_, int p_93195_, float p_93196_, float p_93197_, int p_93198_, int p_93199_) {
		innerBlit(p_93188_.last().pose(), p_93189_, p_93190_, p_93191_, p_93192_, p_93193_,
				(p_93196_ + 0.0F) / (float) p_93198_, (p_93196_ + (float) p_93194_) / (float) p_93198_,
				(p_93197_ + 0.0F) / (float) p_93199_, (p_93197_ + (float) p_93195_) / (float) p_93199_);
	}

	private static void innerBlit(Matrix4f p_93113_, int p_93114_, int p_93115_, int p_93116_, int p_93117_,
			int p_93118_, float p_93119_, float p_93120_, float p_93121_, float p_93122_) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferbuilder.vertex(p_93113_, (float) p_93114_, (float) p_93117_, (float) p_93118_).uv(p_93119_, p_93122_)
				.endVertex();
		bufferbuilder.vertex(p_93113_, (float) p_93115_, (float) p_93117_, (float) p_93118_).uv(p_93120_, p_93122_)
				.endVertex();
		bufferbuilder.vertex(p_93113_, (float) p_93115_, (float) p_93116_, (float) p_93118_).uv(p_93120_, p_93121_)
				.endVertex();
		bufferbuilder.vertex(p_93113_, (float) p_93114_, (float) p_93116_, (float) p_93118_).uv(p_93119_, p_93121_)
				.endVertex();
		bufferbuilder.end();
		BufferUploader.draw(bufferbuilder.end());
	}
	
	public static void renderScopeOverlay(float progress) {
		
		float screenWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		float screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
		
		RenderSystem.enableBlend();
		RenderSystem.disableDepthTest();
	      RenderSystem.depthMask(false);
	      RenderSystem.defaultBlendFunc();
	      RenderSystem.setShader(GameRenderer::getPositionTexShader);
	      RenderSystem.setShaderTexture(0, new ResourceLocation("textures/misc/spyglass_scope.png"));
	      Tesselator tesselator = Tesselator.getInstance();
	      BufferBuilder bufferbuilder = tesselator.getBuilder();
	      float f = (float)Math.min(screenWidth, screenHeight);
	      float f1 = Math.min((float) screenWidth / f, (float) screenHeight 
					/ f) * Mth.lerp((progress-0.5f)/0.5f, 
							0.01f, 1.125f);
	      float f2 = f * f1;
	      float f3 = f * f1;
	      float f4 = ((float)screenWidth - f2) / 2.0F;
	      float f5 = ((float)screenHeight - f3) / 2.0F;
	      float f6 = f4 + f2;
	      float f7 = f5 + f3;
	      bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
	      bufferbuilder.vertex((double)f4, (double)f7, -90.0D).uv(0.0F, 1.0F).endVertex();
	      bufferbuilder.vertex((double)f6, (double)f7, -90.0D).uv(1.0F, 1.0F).endVertex();
	      bufferbuilder.vertex((double)f6, (double)f5, -90.0D).uv(1.0F, 0.0F).endVertex();
	      bufferbuilder.vertex((double)f4, (double)f5, -90.0D).uv(0.0F, 0.0F).endVertex();
	      tesselator.end();
	      
	      RenderSystem.setShader(GameRenderer::getPositionColorShader);
	      RenderSystem.disableTexture();
	      bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
	      bufferbuilder.vertex(0.0D, (double)screenHeight, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex((double)screenWidth, (double)screenHeight, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex((double)screenWidth, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex(0.0D, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex(0.0D, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex((double)screenWidth, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex((double)screenWidth, 0.0D, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex(0.0D, 0.0D, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex(0.0D, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex((double)f4, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex((double)f4, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex(0.0D, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex((double)f6, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex((double)screenWidth, (double)f7, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex((double)screenWidth, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
	      bufferbuilder.vertex((double)f6, (double)f5, -90.0D).color(0, 0, 0, 255).endVertex();
	      tesselator.end();
	      RenderSystem.enableTexture();
	      RenderSystem.depthMask(true);
	      RenderSystem.enableDepthTest();
	      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public static void renderGuiItem(ItemStack p_191962_1_, int p_191962_2_, int p_191962_3_, BakedModel p_191962_4_) {
		Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
		RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		PoseStack posestack = RenderSystem.getModelViewStack();
		posestack.pushPose();
		posestack.translate((double) p_191962_2_, (double) p_191962_3_,
				(double) (100.0F + Minecraft.getInstance().getItemRenderer().blitOffset));
		posestack.translate(8.0D, 8.0D, 0.0D);
		posestack.scale(1.0F, -1.0F, 1.0F);
		posestack.scale(16.0F, 16.0F, 16.0F);
		RenderSystem.applyModelViewMatrix();
		PoseStack posestack1 = new PoseStack();
		MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers()
				.bufferSource();
		boolean flag = !p_191962_4_.usesBlockLight();
		if (flag) {
			Lighting.setupForFlatItems();
		}

		Minecraft.getInstance().getItemRenderer().render(p_191962_1_, ItemTransforms.TransformType.GUI, false,
				posestack1, multibuffersource$buffersource, 15728880, OverlayTexture.NO_OVERLAY, p_191962_4_);
		multibuffersource$buffersource.endBatch();
		RenderSystem.enableDepthTest();
		if (flag) {
			Lighting.setupFor3DItems();
		}

		posestack.popPose();
		RenderSystem.applyModelViewMatrix();
	}
}
