package com.redwyvern.util;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.redwyvern.proxy.ClientProxy;
import com.redwyvern.proxy.RenderItemException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.BipedModel.ArmPose;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
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

	public static final ResourceLocation hitmarker = new ResourceLocation("oldguns:textures/blocks/hitmarker.png");

	public static void drawHitmarker(MatrixStack stack) {

		RenderSystem.enableBlend();
		RenderSystem.enableAlphaTest();

		BufferBuilder buffer = Tessellator.getInstance().getBuilder();
		int window_width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		int window_height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

		int size = 8;

		stack.pushPose();
		{
			Matrix4f matrix = stack.last().pose();
			stack.translate((window_width - size) * 0.5f, (window_height - size) * 0.5f, 0);
			Minecraft.getInstance().getTextureManager().bind(hitmarker);
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

	public static void renderStack(int x, int y, ItemStack stack) {
		enable3DRender();
		try {
			Minecraft.getInstance().getItemRenderer().renderGuiItem(stack, x, y);
		} catch (Exception e) {
			String stackStr = (stack != null) ? stack.toString() : "NullStack";
			RenderItemException.handleErr(e, "renderStack | " + stackStr, null);
		}
		enable2DRender();
	}

	public static void drawTooltipBox(int x, int y, int w, int h, int bg) {
		drawGradientRect(x + 1, y, w - 1, 1, bg, bg);
		drawGradientRect(x + 1, y + h, w - 1, 1, bg, bg);
		drawGradientRect(x + 1, y + 1, w - 1, h - 1, bg, bg);// center
		drawGradientRect(x, y + 1, 1, h - 1, bg, bg);
		drawGradientRect(x + w, y + 1, 1, h - 1, bg, bg);
		drawGradientRect(x + 1, y + 2, 1, h - 3, bg, bg);
		drawGradientRect(x + w - 1, y + 2, 1, h - 3, bg, bg);

		drawGradientRect(x + 1, y + 1, w - 1, 1, bg, bg);
		drawGradientRect(x + 1, y + h - 1, w - 1, 1, bg, bg);
	}

	public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
		float zLevel = 306F;

		float f = (float) (startColor >> 24 & 255) / 255.0F;
		float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		float f3 = (float) (startColor & 255) / 255.0F;
		float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		float f7 = (float) (endColor & 255) / 255.0F;
		RenderSystem.disableTexture();
		RenderSystem.enableBlend();
		RenderSystem.disableAlphaTest();
		RenderSystem.blendFuncSeparate(770, 771, 1, 0);
		RenderSystem.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuilder();
		buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		buffer.vertex(left + right, top, zLevel).color(f1, f2, f3, f).endVertex();
		buffer.vertex(left, top, zLevel).color(f1, f2, f3, f).endVertex();
		buffer.vertex(left, top + bottom, zLevel).color(f5, f6, f7, f4).endVertex();
		buffer.vertex(left + right, top + bottom, zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.end();
		RenderSystem.shadeModel(7424);
		RenderSystem.disableBlend();
		RenderSystem.enableAlphaTest();
		RenderSystem.enableTexture();
	}

	public static void setupModel(MatrixStack matrix, IBakedModel basemodel) {
		matrix.translate(1.719999f, -1.7999989f, -1.7599989f);
		matrix.mulPose(Vector3f.XP.rotationDegrees(96)/* new Quaternion(96, 0, 0, true) */);
		matrix.translate(basemodel.getTransforms().firstPersonRightHand.translation.x(),
				basemodel.getTransforms().firstPersonRightHand.translation.y(),
				basemodel.getTransforms().firstPersonRightHand.translation.z());
		matrix.mulPose(Vector3f.XP.rotationDegrees(basemodel.getTransforms().firstPersonRightHand.rotation.x()));
		matrix.mulPose(Vector3f.YP.rotationDegrees(basemodel.getTransforms().firstPersonRightHand.rotation.y()));
		matrix.mulPose(Vector3f.ZP.rotationDegrees(basemodel.getTransforms().firstPersonRightHand.rotation.z()));
		matrix.scale(basemodel.getTransforms().firstPersonRightHand.scale.x(),
				basemodel.getTransforms().firstPersonRightHand.scale.y(),
				basemodel.getTransforms().firstPersonRightHand.scale.z());
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderItemStack(PlayerEntity player, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
		Minecraft.getInstance().getItemRenderer().render(player.getMainHandItem(),
				TransformType.FIRST_PERSON_RIGHT_HAND, false, matrix, buffer, light, OverlayTexture.NO_OVERLAY,
				Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(player.getMainHandItem()));
	}

	public static void renderModel(IBakedModel model, MatrixStack matrix, ItemStack stack, IRenderTypeBuffer buffer,
			int light, int overlay) {
		IVertexBuilder builder = ItemRenderer.getFoilBuffer(buffer, Atlases.translucentItemSheet(), true,
				stack.hasFoil());
		Minecraft.getInstance().getItemRenderer().renderModelLists(model, stack, light, overlay, matrix, builder);
	}

	public static int getItemStackColor(ItemStack stack, int tintIndex) {
		int color = Minecraft.getInstance().getItemColors().getColor(stack, tintIndex);
		if (color == -1) {
			if (!stack.isEmpty()) {
				return getItemStackColor(stack, tintIndex);
			}
		}
		return color;
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderRightArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
			AbstractClientPlayerEntity playerIn, PlayerModel<AbstractClientPlayerEntity> model, float rx, float ry,
			float rz) {
		renderRightItem(matrixStackIn, bufferIn, combinedLightIn, playerIn, model.rightArm, model.rightSleeve, rx, ry,
				rz);
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderLeftArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
			AbstractClientPlayerEntity playerIn, PlayerModel<AbstractClientPlayerEntity> model, float rx, float ry,
			float rz) {
		renderItem(matrixStackIn, bufferIn, combinedLightIn, playerIn, model.leftArm, model.leftSleeve, rx, ry, rz);
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderLeftPlayerArm(MatrixStack matrixStackIn, ItemStack stack, IRenderTypeBuffer bufferIn,
			int combinedLightIn, float pitch, float tx, float ty, float tz, int sz, float rx, float ry, float rz,
			IVertexBuilder builder) {
		if (!Minecraft.getInstance().player.isInvisible()) {
			matrixStackIn.pushPose();
			matrixStackIn.scale(1.5F, 1.5F, 1.5F);
			matrixStackIn.popPose();
			matrixStackIn.pushPose();
			matrixStackIn.scale(3, 3, 4);
			renderLeftArmFirstPersonCorrect(matrixStackIn, bufferIn, combinedLightIn, MathHelper.wrapDegrees(rx),
					MathHelper.wrapDegrees(ry), MathHelper.wrapDegrees(rz), tx, ty, tz);
			matrixStackIn.popPose();
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderRightPlayerArm(MatrixStack matrixStackIn, ItemStack stack, IRenderTypeBuffer bufferIn,
			int combinedLightIn, float pitch, float tx, float ty, float tz, float rx, float ry, float rz,
			IVertexBuilder builder) {
		if (!Minecraft.getInstance().player.isInvisible()) {
			matrixStackIn.pushPose();
			matrixStackIn.scale(2F, 2F, 2F);
			renderRightArmFirstPersonCorrect(matrixStackIn, bufferIn, combinedLightIn, MathHelper.wrapDegrees(rx),
					MathHelper.wrapDegrees(ry), MathHelper.wrapDegrees(rz), tx, ty, tz);
			matrixStackIn.popPose();
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderRightArmFirstPersonCorrect(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			int combinedLightIn, float rx, float ry, float rz, float tx, float ty, float tz) {
		Minecraft mc = Minecraft.getInstance();
		mc.getTextureManager().bind(mc.player.getSkinTextureLocation());
		PlayerModel<AbstractClientPlayerEntity> model = ClientProxy.getPlayerModel(mc.player);
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
		matrixStackIn.translate((double) (f * 5.6F) + tx, ty, tz);
		renderRightArm(matrixStackIn, bufferIn, combinedLightIn, mc.player, model, rx, ry, rz);

	}

	@OnlyIn(Dist.CLIENT)
	public static void renderLeftArmFirstPersonCorrect(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			int combinedLightIn, float rx, float ry, float rz, float tx, float ty, float tz) {
		Minecraft mc = Minecraft.getInstance();
		EntityRendererManager manager = mc.getEntityRenderDispatcher();
		mc.getTextureManager().bind(mc.player.getSkinTextureLocation());
		PlayerRenderer playerRenderer = (PlayerRenderer) manager.<AbstractClientPlayerEntity>getRenderer(mc.player);
		PlayerModel<AbstractClientPlayerEntity> model = ClientProxy.getPlayerModel(mc.player);
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
		AbstractClientPlayerEntity abstractclientplayerentity = mc.player;
		mc.getTextureManager().bind(abstractclientplayerentity.getSkinTextureLocation());
		matrixStackIn.translate((double) (f * -1.0F), (double) 3.6F, 3.5D);
		matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(f * 120.0F));
		matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(200.0F));
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f * -135.0F));
		matrixStackIn.translate((double) (f * 5.6F) + tx, ty, tz);
		renderLeftArm(matrixStackIn, bufferIn, combinedLightIn, mc.player, model, rx, ry, rz);
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderItem(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
			AbstractClientPlayerEntity playerIn, ModelRenderer rendererArmIn, ModelRenderer rendererArmwearIn, float rx,
			float ry, float rz) {
		PlayerModel<AbstractClientPlayerEntity> playermodel = ClientProxy
				.getPlayerModel((AbstractClientPlayerEntity) Minecraft.getInstance().player);
		setModelVisibilities((AbstractClientPlayerEntity) Minecraft.getInstance().player);
		playermodel.attackTime = 0.0F;
		playermodel.crouching = false;
		playermodel.swimAmount = 0.0F;
		playermodel.setupAnim(playerIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		playermodel.leftArmPose = ArmPose.BOW_AND_ARROW;
		playermodel.rightArmPose = ArmPose.BOW_AND_ARROW;
		rendererArmIn.xRot = (float) Math.toRadians(rx);// (float)Math.toRadians(348) + rx;
		rendererArmIn.yRot = (float) Math.toRadians(ry);
		rendererArmIn.zRot = (float) Math.toRadians(rz);
		rendererArmIn.render(matrixStackIn,
				bufferIn.getBuffer(RenderType.entitySolid(playerIn.getSkinTextureLocation())), combinedLightIn,
				OverlayTexture.NO_OVERLAY);
	}

	@OnlyIn(Dist.CLIENT)
	public static void renderRightItem(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
			AbstractClientPlayerEntity playerIn, ModelRenderer rendererArmIn, ModelRenderer rendererArmwearIn, float rx,
			float ry, float rz) {
		PlayerModel<AbstractClientPlayerEntity> playermodel = ClientProxy
				.getPlayerModel((AbstractClientPlayerEntity) Minecraft.getInstance().player);
		setModelVisibilities((AbstractClientPlayerEntity) Minecraft.getInstance().player);
		playermodel.attackTime = 0.0F;
		playermodel.crouching = false;
		playermodel.swimAmount = 0.0F;
		playermodel.setupAnim(playerIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		playermodel.leftArmPose = ArmPose.BOW_AND_ARROW;
		playermodel.rightArmPose = ArmPose.BOW_AND_ARROW;
		rendererArmIn.xRot = (float) Math.toRadians(MathHelper.wrapDegrees(rx));// (float)Math.toRadians(258);
		rendererArmIn.yRot = (float) Math.toRadians(MathHelper.wrapDegrees(ry));// (float)Math.toRadians(350);
		rendererArmIn.xRot = (float) Math.toRadians(MathHelper.wrapDegrees(rz));// (float)Math.toRadians(0);
		rendererArmIn.render(matrixStackIn,
				bufferIn.getBuffer(RenderType.entitySolid(playerIn.getSkinTextureLocation())), combinedLightIn,
				OverlayTexture.NO_OVERLAY);
	}

	@OnlyIn(Dist.CLIENT)
	private static void setModelVisibilities(AbstractClientPlayerEntity clientPlayer) {
		PlayerModel<AbstractClientPlayerEntity> playermodel = ClientProxy
				.getPlayerModel((AbstractClientPlayerEntity) Minecraft.getInstance().player);
		if (clientPlayer.isSpectator()) {
			playermodel.setAllVisible(false);
			playermodel.head.visible = true;
			playermodel.hat.visible = true;
		} else {
			playermodel.setAllVisible(true);
			playermodel.hat.visible = clientPlayer.isModelPartShown(PlayerModelPart.HAT);
			playermodel.jacket.visible = clientPlayer.isModelPartShown(PlayerModelPart.JACKET);
			playermodel.leftPants.visible = clientPlayer.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
			playermodel.rightPants.visible = clientPlayer.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
			playermodel.leftSleeve.visible = clientPlayer.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
			playermodel.rightSleeve.visible = clientPlayer.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
			playermodel.crouching = clientPlayer.isCrouching();
			playermodel.leftArm.visible = true;
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
