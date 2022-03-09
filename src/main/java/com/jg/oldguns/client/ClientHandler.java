package com.jg.oldguns.client;

import com.jg.oldguns.OldGuns;
import com.jg.oldguns.client.handlers.AimHandler;
import com.jg.oldguns.client.handlers.HitmarkerHandler;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.client.handlers.RecoilHandler;
import com.jg.oldguns.client.handlers.ShootHandler;
import com.jg.oldguns.client.handlers.SprintHandler;
import com.jg.oldguns.client.models.GunModel;
import com.jg.oldguns.utils.Constants;
import com.jg.oldguns.utils.RenderUtil;
import com.jg.oldguns.utils.Util;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.world.item.ItemStack;

public class ClientHandler {
	
	public static PlayerModel<AbstractClientPlayer> model;
	
	private GunModel gunmodel;
	
	private boolean rot = false;

	private AimHandler aimHandler;
	private SprintHandler sprintHandler;
	private RecoilHandler recoilHandler;
	private HitmarkerHandler hitmarkerHandler;
	private ShootHandler shootHandler;
	
	public static boolean debug = false;
	private boolean debugAim = false;
	
	private ItemStack oldstack = ItemStack.EMPTY;

	public ClientHandler() {
		model = ClientEventHandler.getPlayerModel(Minecraft.getInstance().player);
		aimHandler = new AimHandler();
		sprintHandler = new SprintHandler();
		recoilHandler = new RecoilHandler();
		hitmarkerHandler = new HitmarkerHandler();
		shootHandler = new ShootHandler(this);
	}
	
	public void render(PoseStack matrix, ItemStack stack, MultiBufferSource itembuffer, BufferSource armbuffer, float partialTicks, int light,
			int overlay) {
		if (gunmodel != null) {
			gunmodel.render(matrix, stack, itembuffer, armbuffer, light, overlay, partialTicks, aimHandler.getProgress(),
					sprintHandler.getProgress(), recoilHandler);
		}
	}

	public void tick() {
		if (gunmodel != null) {
			gunmodel.tick();
			recoilHandler.tick();
			hitmarkerHandler.tick();
			shootHandler.tick();
			
			ItemStack actual = Util.getStack();

			if (!(oldstack.getOrCreateTag().getString(Constants.ID)
					.equals(actual.getOrCreateTag().getString(Constants.ID)))
					&& gunmodel.getAnimation() == GunModel.EMPTY) {
				// System.out.println("OldStack Id: " +
				// oldstack.getOrCreateTag().getString(Constants.ID) + " current stack id: " +
				// actual.getOrCreateTag().getString(Constants.ID));
				selectGunModel(actual);
				reset();
				oldstack = actual;
			}

		} else {
			selectGunModel(ClientEventHandler.getPlayer().getMainHandItem());
		}
	}

	public void reset() {
		recoilHandler.reset();
		aimHandler.reset();
		sprintHandler.reset();
		shootHandler.reset();
		recoilHandler.removeCooldown(ClientEventHandler.getPlayer().getMainHandItem().getItem());
		gunmodel.setAnimation(GunModel.EMPTY);
		shootHandler.reset();
	}

	public void onShoot(ItemStack stack) {
		gunmodel.onShoot(stack);
	}

	public boolean isAnimationEmpty() {
		return gunmodel.getAnimation() == GunModel.EMPTY;
	}

	public GunModel getGunModel() {
		if (gunmodel == null) {
			OldGuns.LOGGER.warn("GunModel is null");
		}
		return gunmodel;
	}

	public void pickRandomRecoil() {
		recoilHandler.setRandom();
	}

	public void toogleRot() {
		this.rot = !rot;
		System.out.println("Is on rotation mode: " + rot);
	}

	public void selectGunModel(ItemStack stack) {
		if (Util.canWork(stack)) {
			this.gunmodel = ModelHandler.INSTANCE.gunmodels.get(stack.getItem());
		}
	}
	
	public void tryToReload(int gunbullets, int magbullets, int ammoindex) {
		gunmodel.reset(gunbullets, magbullets, ammoindex);
		gunmodel.initReloadAnimation(gunbullets, magbullets);
	}

	public boolean canRenderHitmarker() {
		return hitmarkerHandler.hitmarkerTime > 0;
	}

	public boolean debugAim() {
		return debugAim;
	}
	
	public boolean isOnRotation() {
		return rot;
	}
	
	public void invertDebugAim() {
		this.debugAim = !this.debugAim;
	}
	
	public RecoilHandler getRecoilHandler() {
		return recoilHandler;
	}

	public AimHandler getAimingHandler() {
		return aimHandler;
	}

	public SprintHandler getSprintHandler() {
		return sprintHandler;
	}

	public HitmarkerHandler getHitmarkerHandler() {
		return hitmarkerHandler;
	}
	
	public ShootHandler getShootHandler() {
		return shootHandler;
	}
	
}
