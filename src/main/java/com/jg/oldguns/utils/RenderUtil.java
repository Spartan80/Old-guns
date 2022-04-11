package com.jg.oldguns.utils;

import org.lwjgl.opengl.GL11;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.ClientEventHandler;
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
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;

public class RenderUtil {

	// Resources

	public static final ResourceLocation HITMARKER = new ResourceLocation(OldGuns.MODID,
			"textures/effects/hitmarker.png");
	
	public static final ResourceLocation MUZZLE = new ResourceLocation(OldGuns.MODID,
			"textures/effects/muzzle_flash.png");
	
	public static final ResourceLocation SCOPE = new ResourceLocation(OldGuns.MODID,
			"textures/effects/optic_rifle.png");
	
	public static final ResourceLocation SCOPEEMPTYPART = new ResourceLocation(OldGuns.MODID,
			"textures/effects/scope_sides.png");

	// Render images

	public static void renderMuzzleFlash(PoseStack stack, BufferSource impl, float partialTicks, float xo, float yo, float zo) {
		stack.pushPose();
		stack.translate(
				xo, 
				yo,
				zo);

        stack.scale(0.03F, 0.03F, 0.0F);

        //float scale = 0.5F + 0.5F * (1.0F - partialTicks);
        //stack.scale(scale, scale, 1.0F);
        float r = (float)(360f * Math.random());
        //System.out.println(r);
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
		RenderSystem.enableDepthTest();

		BufferBuilder buffer = Tesselator.getInstance().getBuilder();
		int window_width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		int window_height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

		// int size = 8;

		/*stack.pushPose();
		{
			Matrix4f matrix = stack.last().pose();
			stack.translate((window_width - size) * 0.5f, (window_height - size) * 0.5f, 2);
			Minecraft.getInstance().getTextureManager().bindForSetup(image);
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			buffer.vertex(matrix, 0, size, 0).uv(0, 1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, size, size, 0).uv(1, 1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, size, 0, 0).uv(1, 0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.end();
			BufferUploader.end(buffer);
		}
		stack.popPose();*/
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
        buffer.end();
        BufferUploader.end(buffer);
		stack.popPose();
		//RenderSystem.disableBlend();
		//RenderSystem.disableDepthTest();
	}

	public static void enable3DRender() {
		RenderSystem.enableDepthTest();
	}

	public static void enable2DRender() {
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

	public static void renderScopeImage(PoseStack stack, ResourceLocation image, ResourceLocation nullpart, int width,
			int height) {
		RenderSystem.enableBlend();
		RenderSystem.enableDepthTest();

		BufferBuilder buffer = Tesselator.getInstance().getBuilder();
		int window_width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
		int window_height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

		stack.pushPose();
		{
			Matrix4f matrix = stack.last().pose();
			stack.translate(0, window_height * 0.25f, 1000);
			Minecraft.getInstance().getTextureManager().bindForSetup(image);
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			buffer.vertex(matrix, 0, window_height * 0.5f, 0).uv(0, 1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, window_width, window_height * 0.5f, 0).uv(1, 1).color(1.0f, 1.0f, 1.0f, 1.0f)
					.endVertex();
			buffer.vertex(matrix, window_width, 0, 0).uv(1, 0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.end();

			BufferUploader.end(buffer);
		}
		stack.popPose();

		stack.pushPose();
		{
			Matrix4f matrix = stack.last().pose();
			stack.translate(0, 0, 1000);
			Minecraft.getInstance().getTextureManager().bindForSetup(nullpart);
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			buffer.vertex(matrix, 0, window_height * 0.25f, 0).uv(0, 1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, window_width, window_height * 0.25f, 0).uv(1, 1).color(1.0f, 1.0f, 1.0f, 1.0f)
					.endVertex();
			buffer.vertex(matrix, window_width, 0, 0).uv(1, 0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.end();
		}

		BufferUploader.end(buffer);

		stack.popPose();

		stack.pushPose();
		{
			Matrix4f matrix = stack.last().pose();
			stack.translate(0, window_height * 0.75f, 1000);
			Minecraft.getInstance().getTextureManager().bindForSetup(nullpart);
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			buffer.vertex(matrix, 0, window_height * 0.25f, 0).uv(0, 1).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, window_width, window_height * 0.25f, 0).uv(1, 1).color(1.0f, 1.0f, 1.0f, 1.0f)
					.endVertex();
			buffer.vertex(matrix, window_width, 0, 0).uv(1, 0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
			buffer.vertex(matrix, 0, 0, 0).uv(0, 0).color(1.0F, 1.0F, 1.0F, 1.0f).endVertex();
			buffer.end();
		}

		BufferUploader.end(buffer);

		stack.popPose();
	}

	// Render Gui

	public static void renderGuiItem(ItemStack p_191962_1_, int p_191962_2_, int p_191962_3_, BakedModel p_191962_4_) {
		RenderSystem.getModelViewStack().pushPose();
		Minecraft.getInstance().textureManager.bindForSetup(TextureAtlas.LOCATION_BLOCKS);
		Minecraft.getInstance().textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.getModelViewStack().translate((float) p_191962_2_, (float) p_191962_3_,
				100.0F + Minecraft.getInstance().getItemRenderer().blitOffset);
		RenderSystem.getModelViewStack().translate(8.0F, 8.0F, 0.0F);
		RenderSystem.getModelViewStack().scale(1.0F, -1.0F, 1.0F);
		RenderSystem.getModelViewStack().scale(16.0F, 16.0F, 16.0F);
		PoseStack PoseStack = new PoseStack();
		BufferSource irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
		boolean flag = !p_191962_4_.usesBlockLight();
		if (flag) {
			Lighting.setupForFlatItems();
		}
		
		Minecraft.getInstance().getItemRenderer().render(p_191962_1_, ItemTransforms.TransformType.GUI, false,
				PoseStack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, p_191962_4_);
		irendertypebuffer$impl.endBatch();
		RenderSystem.enableDepthTest();
		if (flag) {
			Lighting.setupFor3DItems();
		}

		RenderSystem.getModelViewStack().popPose();
	}

	// Render Right arm

	@OnlyIn(Dist.CLIENT)
	public static void renderRightArm(PoseStack PoseStackIn, MultiBufferSource bufferIn, int combinedLightIn,
			AbstractClientPlayer playerIn, PlayerModel<AbstractClientPlayer> model, float rx, float ry,
			float rz) {
		renderArm(PoseStackIn, bufferIn, combinedLightIn, playerIn, model.rightArm, model.rightSleeve, rx, ry, rz);
	}

	// Render Left arm

	@OnlyIn(Dist.CLIENT)
	public static void renderLeftArm(PoseStack PoseStackIn, MultiBufferSource bufferIn, int combinedLightIn,
			AbstractClientPlayer playerIn, PlayerModel<AbstractClientPlayer> model, float rx, float ry,
			float rz) {
		renderArm(PoseStackIn, bufferIn, combinedLightIn, playerIn, model.leftArm, model.leftSleeve, rx, ry, rz);
	}

	// Render arm

	@OnlyIn(Dist.CLIENT)
	public static void renderArm(PoseStack PoseStackIn, MultiBufferSource bufferIn, int combinedLightIn,
			AbstractClientPlayer playerIn, ModelPart rendererArmIn, ModelPart rendererArmwearIn, float rx,
			float ry, float rz) {
		Minecraft mc = Minecraft.getInstance();
		mc.getTextureManager().bindForSetup(mc.player.getSkinTextureLocation());
		float f = 1.0F;
		float f1 = Mth.sqrt(0);
		float f2 = -0.3F * Mth.sin(f1 * (float) Math.PI);
		float f3 = 0.4F * Mth.sin(f1 * ((float) Math.PI * 2F));
		float f4 = -0.4F * Mth.sin(0 * (float) Math.PI);
		PoseStackIn.translate((double) (f * (f2 + 0.64000005F)), (double) (f3 + -0.6F + 0 * -0.6F),
				(double) (f4 + -0.71999997F));
		PoseStackIn.mulPose(Vector3f.YP.rotationDegrees(f * 45.0F));
		float f5 = Mth.sin(0 * 0 * (float) Math.PI);
		float f6 = Mth.sin(f1 * (float) Math.PI);
		PoseStackIn.mulPose(Vector3f.YP.rotationDegrees(f * f6 * 70.0F));
		PoseStackIn.mulPose(Vector3f.ZP.rotationDegrees(f * f5 * -20.0F));
		PoseStackIn.translate((double) (f * -1.0F), (double) 3.6F, 3.5D);
		PoseStackIn.mulPose(Vector3f.ZP.rotationDegrees(f * 120.0F));
		PoseStackIn.mulPose(Vector3f.XP.rotationDegrees(200.0F));
		PoseStackIn.mulPose(Vector3f.YP.rotationDegrees(f * -135.0F));
		PoseStackIn.translate((double) (f * 5.6F), 0, 0);
		setModelVisibilities((AbstractClientPlayer) Minecraft.getInstance().player);
		rendererArmIn.xRot = rx;
		rendererArmIn.yRot = ry;
		rendererArmIn.zRot = rz;
		//System.out.println("BufferIn null? " + (bufferIn == null) + " entity solid null? " + (RenderType.entitySolid(playerIn.getSkinTextureLocation()) == null));
		//VertexConsumer type = bufferIn.getBuffer(RenderType.entitySolid(playerIn.getSkinTextureLocation()));
		rendererArmIn.render(PoseStackIn,
				bufferIn.getBuffer(RenderType.entitySolid(playerIn.getSkinTextureLocation())), combinedLightIn,
				OverlayTexture.NO_OVERLAY);
		/*rendererArmIn.render(PoseStackIn,
				type, combinedLightIn,
				OverlayTexture.NO_OVERLAY);*/
	}

	// Render Item Model

	public static void renderModel(BakedModel model, PoseStack matrix, ItemStack stack, MultiBufferSource buffer,
			int light, int overlay) {
		VertexConsumer builder = ItemRenderer.getFoilBuffer(buffer, Sheets.translucentItemSheet(), true,
				stack.hasFoil());
		Minecraft.getInstance().getItemRenderer().renderModelLists(model, stack, light, overlay, matrix, builder);
	}

	// Model

	public static BakedModel getModel(String loc) {
		return Minecraft.getInstance().getModelManager()
				.getModel(new ModelResourceLocation(Util.loc(loc), "inventory"));
	}

	// Misc

	@OnlyIn(Dist.CLIENT)
	private static void setModelVisibilities(AbstractClientPlayer clientPlayer) {
		PlayerModel<AbstractClientPlayer> playermodel = ClientEventHandler
				.getPlayerModel((AbstractClientPlayer) Minecraft.getInstance().player);
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
			HumanoidModel.ArmPose bipedmodel$armpose = HumanoidModel.ArmPose.BOW_AND_ARROW;
			HumanoidModel.ArmPose bipedmodel$armpose1 = HumanoidModel.ArmPose.BOW_AND_ARROW;
			
			if (clientPlayer.getMainArm() == HumanoidArm.RIGHT) {
				playermodel.rightArmPose = bipedmodel$armpose;
				playermodel.leftArmPose = bipedmodel$armpose1;
			} else {
				playermodel.rightArmPose = bipedmodel$armpose1;
				playermodel.leftArmPose = bipedmodel$armpose;
			}
		}

	}
}
