package com.jg.oldguns.client.render.types;

import org.lwjgl.opengl.GL11;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.utils.RenderUtil;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class RenderTypes extends RenderType{
	
	public RenderTypes(String p_173178_, VertexFormat p_173179_, Mode p_173180_,
			int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
		super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
		// TODO Auto-generated constructor stub
	}

	public static final RenderType MUZZLE_FLASH = RenderType
			.create(OldGuns.MODID + ":muzzle_flash", 
			DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, 
			VertexFormat.Mode.QUADS, 256, true, false, 
			RenderType.CompositeState.builder()
			.setTextureState(new RenderStateShard.TextureStateShard(RenderUtil.MUZZLE, false, false))
			.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
			.setCullState(NO_CULL).createCompositeState(true));
}
