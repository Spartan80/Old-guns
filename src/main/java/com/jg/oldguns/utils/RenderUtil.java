package com.jg.oldguns.utils;

import org.lwjgl.opengl.GL11;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.ClientEventHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.BipedModel.ArmPose;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RenderUtil {

	// Resources

	public static final ResourceLocation HITMARKER = new ResourceLocation(OldGuns.MODID,
			"textures/effects/hitmarker.png");
	
	public static final ResourceLocation SCOPE = new ResourceLocation(OldGuns.MODID,
			"textures/effects/optic_rifle.png");
	
	public static final ResourceLocation SCOPEEMPTYPART = new ResourceLocation(OldGuns.MODID,
			"textures/effects/scope_sides.png");

	// Render images

	public static void drawHitmarker(MatrixStack stack, ResourceLocation image, int size) {

		RenderSystem.enableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableDepthTest();

		BufferBuilder buffer = Tessellator.getInstance().getBuilder();
		int window_width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		int window_height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

		// int size = 8;

		stack.pushPose();
		{
			Matrix4f matrix = stack.last().pose();
			stack.translate((window_width - size) * 0.5f, (window_height - size) * 0.5f, 2);
			Minecraft.getInstance().getTextureManager().bind(image);
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buffer.vertex(matrix, 0, size, 0).uv(0, 1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, size, size, 0).uv(1, 1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, size, 0, 0).uv(1, 0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.end();

			RenderSystem.enableAlphaTest();
			WorldVertexBufferUploader.end(buffer);
		}
		stack.popPose();
	}

	public static void enable3DRender() {
		RenderSystem.enableLighting();
		RenderSystem.enableDepthTest();
	}

	public static void enable2DRender() {
		RenderSystem.disableLighting();
		RenderSystem.disableDepthTest();
	}

	public static int getRatio(int width, int height) {
		return width / height;
	}

	public static int[] getScaledSize(int width, int height) {
		int finalWidth = 0;
		int finalHeight = 0;
		int wwidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		int wheight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
		int windowRatio = getRatio(wwidth, wheight);
		int imgRatio = getRatio(width, height);
		if (windowRatio > imgRatio) {
			finalHeight = wheight;
			finalWidth = (wheight * imgRatio);
		} else {
			finalWidth = wwidth;
			finalHeight = (wwidth * imgRatio);
		}
		return new int[] { finalWidth, finalHeight };
	}

	public static void renderScopeImage(MatrixStack stack, ResourceLocation image, ResourceLocation nullpart, int width,
			int height) {
		RenderSystem.enableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableDepthTest();

		BufferBuilder buffer = Tessellator.getInstance().getBuilder();
		int window_width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		int window_height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

		stack.pushPose();
		{
			Matrix4f matrix = stack.last().pose();
			stack.translate(0, window_height * 0.25f, 1000);
			Minecraft.getInstance().getTextureManager().bind(image);
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buffer.vertex(matrix, 0, window_height * 0.5f, 0).uv(0, 1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, window_width, window_height * 0.5f, 0).uv(1, 1).color(1.0f, 1.0f, 1.0f, 1.0f)
					.endVertex();
			buffer.vertex(matrix, window_width, 0, 0).uv(1, 0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.end();

			RenderSystem.enableAlphaTest();
			WorldVertexBufferUploader.end(buffer);
		}
		stack.popPose();

		stack.pushPose();
		{
			Matrix4f matrix = stack.last().pose();
			stack.translate(0, 0, 1000);
			Minecraft.getInstance().getTextureManager().bind(nullpart);
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buffer.vertex(matrix, 0, window_height * 0.25f, 0).uv(0, 1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, window_width, window_height * 0.25f, 0).uv(1, 1).color(1.0f, 1.0f, 1.0f, 1.0f)
					.endVertex();
			buffer.vertex(matrix, window_width, 0, 0).uv(1, 0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.end();
		}

		RenderSystem.enableAlphaTest();
		WorldVertexBufferUploader.end(buffer);

		stack.popPose();

		stack.pushPose();
		{
			Matrix4f matrix = stack.last().pose();
			stack.translate(0, window_height * 0.75f, 1000);
			Minecraft.getInstance().getTextureManager().bind(nullpart);
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			buffer.vertex(matrix, 0, window_height * 0.25f, 0).uv(0, 1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, window_width, window_height * 0.25f, 0).uv(1, 1).color(1.0f, 1.0f, 1.0f, 1.0f)
					.endVertex();
			buffer.vertex(matrix, window_width, 0, 0).uv(1, 0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.end();
		}

		RenderSystem.enableAlphaTest();
		WorldVertexBufferUploader.end(buffer);

		stack.popPose();
	}

	// Render Gui

	public static void renderGuiItem(ItemStack p_191962_1_, int p_191962_2_, int p_191962_3_, IBakedModel p_191962_4_) {
		RenderSystem.pushMatrix();
		Minecraft.getInstance().textureManager.bind(AtlasTexture.LOCATION_BLOCKS);
		Minecraft.getInstance().textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
		RenderSystem.enableRescaleNormal();
		RenderSystem.enableAlphaTest();
		RenderSystem.defaultAlphaFunc();
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.translatef((float) p_191962_2_, (float) p_191962_3_,
				100.0F + Minecraft.getInstance().getItemRenderer().blitOffset);
		RenderSystem.translatef(8.0F, 8.0F, 0.0F);
		RenderSystem.scalef(1.0F, -1.0F, 1.0F);
		RenderSystem.scalef(16.0F, 16.0F, 16.0F);
		MatrixStack matrixstack = new MatrixStack();
		IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
		boolean flag = !p_191962_4_.usesBlockLight();
		if (flag) {
			RenderHelper.setupForFlatItems();
		}

		Minecraft.getInstance().getItemRenderer().render(p_191962_1_, ItemCameraTransforms.TransformType.GUI, false,
				matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, p_191962_4_);
		irendertypebuffer$impl.endBatch();
		RenderSystem.enableDepthTest();
		if (flag) {
			RenderHelper.setupFor3DItems();
		}

		RenderSystem.disableAlphaTest();
		RenderSystem.disableRescaleNormal();
		RenderSystem.popMatrix();
	}

	// Render Right arm

	@OnlyIn(Dist.CLIENT)
	public static void renderRightArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
			AbstractClientPlayerEntity playerIn, PlayerModel<AbstractClientPlayerEntity> model, float rx, float ry,
			float rz) {
		renderArm(matrixStackIn, bufferIn, combinedLightIn, playerIn, model.rightArm, model.rightSleeve, rx, ry, rz);
	}

	// Render Left arm

	@OnlyIn(Dist.CLIENT)
	public static void renderLeftArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
			AbstractClientPlayerEntity playerIn, PlayerModel<AbstractClientPlayerEntity> model, float rx, float ry,
			float rz) {
		renderArm(matrixStackIn, bufferIn, combinedLightIn, playerIn, model.leftArm, model.leftSleeve, rx, ry, rz);
	}

	// Render arm

	@OnlyIn(Dist.CLIENT)
	public static void renderArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
			AbstractClientPlayerEntity playerIn, ModelRenderer rendererArmIn, ModelRenderer rendererArmwearIn, float rx,
			float ry, float rz) {
		Minecraft mc = Minecraft.getInstance();
		mc.getTextureManager().bind(mc.player.getSkinTextureLocation());
		float f = 1.0F;
		float f1 = MathHelper.sqrt(0);
		float f2 = -0.3F * MathHelper.sin(f1 * (float) Math.PI);
		float f3 = 0.4F * MathHelper.sin(f1 * ((float) Math.PI * 2F));
		float f4 = -0.4F * MathHelper.sin(0 * (float) Math.PI);
		matrixStackIn.translate((double) (f * (f2 + 0.64000005F)), (double) (f3 + -0.6F + 0 * -0.6F),
				(double) (f4 + -0.71999997F));
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f * 45.0F));
		float f5 = MathHelper.sin(0 * 0 * (float) Math.PI);
		float f6 = MathHelper.sin(f1 * (float) Math.PI);
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f * f6 * 70.0F));
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f * f5 * -20.0F));
		matrixStackIn.translate((double) (f * -1.0F), (double) 3.6F, 3.5D);
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f * 120.0F));
		matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(200.0F));
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f * -135.0F));
		matrixStackIn.translate((double) (f * 5.6F), 0, 0);
		setModelVisibilities((AbstractClientPlayerEntity) Minecraft.getInstance().player);
		rendererArmIn.xRot = rx;
		rendererArmIn.yRot = ry;
		rendererArmIn.zRot = rz;
		rendererArmIn.render(matrixStackIn,
				bufferIn.getBuffer(RenderType.entitySolid(playerIn.getSkinTextureLocation())), combinedLightIn,
				OverlayTexture.NO_OVERLAY);
	}

	// Render Item Model

	public static void renderModel(IBakedModel model, MatrixStack matrix, ItemStack stack, IRenderTypeBuffer buffer,
			int light, int overlay) {
		IVertexBuilder builder = ItemRenderer.getFoilBuffer(buffer, Atlases.translucentItemSheet(), true,
				stack.hasFoil());
		Minecraft.getInstance().getItemRenderer().renderModelLists(model, stack, light, overlay, matrix, builder);
	}

	// Model

	public static IBakedModel getModel(String loc) {
		return Minecraft.getInstance().getModelManager()
				.getModel(new ModelResourceLocation(Util.loc(loc), "inventory"));
	}

	// Misc

	@OnlyIn(Dist.CLIENT)
	private static void setModelVisibilities(AbstractClientPlayerEntity clientPlayer) {
		PlayerModel<AbstractClientPlayerEntity> playermodel = ClientEventHandler
				.getPlayerModel((AbstractClientPlayerEntity) Minecraft.getInstance().player);
		if (clientPlayer.isSpectator()) {
			playermodel.setAllVisible(false);
			playermodel.head.visible = true;
			playermodel.hat.visible = true;
		} else {
			playermodel.attackTime = 0.0F;
			playermodel.crouching = false;
			playermodel.swimAmount = 0.0F;
			playermodel.setupAnim(clientPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
			playermodel.leftArmPose = ArmPose.BOW_AND_ARROW;
			playermodel.rightArmPose = ArmPose.BOW_AND_ARROW;
			playermodel.setAllVisible(true);
			playermodel.hat.visible = clientPlayer.isModelPartShown(PlayerModelPart.HAT);
			playermodel.jacket.visible = clientPlayer.isModelPartShown(PlayerModelPart.JACKET);
			playermodel.leftPants.visible = clientPlayer.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
			playermodel.rightPants.visible = clientPlayer.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
			playermodel.leftSleeve.visible = clientPlayer.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
			playermodel.rightSleeve.visible = clientPlayer.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
			playermodel.crouching = clientPlayer.isCrouching();
			playermodel.leftArm.visible = true;
			playermodel.rightArm.visible = true;
			BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.BOW_AND_ARROW;
			BipedModel.ArmPose bipedmodel$armpose1 = BipedModel.ArmPose.BOW_AND_ARROW;

			if (clientPlayer.getMainArm() == HandSide.RIGHT) {
				playermodel.rightArmPose = bipedmodel$armpose;
				playermodel.leftArmPose = bipedmodel$armpose1;
			} else {
				playermodel.rightArmPose = bipedmodel$armpose1;
				playermodel.leftArmPose = bipedmodel$armpose;
			}
		}

	}
}
