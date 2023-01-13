package com.jg.oldguns.client.handlers;

import com.jg.oldguns.client.animations.parts.GunModel;
import com.jg.oldguns.client.animations.parts.GunModelPart;
import com.jg.oldguns.utils.NBTUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ClientHandler {
	public static float partialTicks = 0.0f;
	
	private GunModel current;
	
	public int part;
	public int mode; // 0 : translate | 1 : rotate | 2 : scale
	public boolean init;
	public boolean display;
	public boolean renderHitmarker;
	public boolean debugAim;
	public boolean loop;
	
	private SprintHandler sprint;
	private AimHandler aim;
	private RecoilHandler recoil;
	private HitmarkerHandler hitmarker;
	private CooldownHandler cooldown;
	
	public ClientHandler() {
		sprint = new SprintHandler();
		aim = new AimHandler();
		recoil = new RecoilHandler();
		hitmarker = new HitmarkerHandler();
		cooldown = new CooldownHandler();
	}
	
	// Methods
	
	public void tick() {
		recoil.tick();
		hitmarker.tick();
		cooldown.tick();
	}
	
	public void shoot(Player player) {
		if(current.canShoot(player, player.getMainHandItem())) {
			current.shoot(player, player.getMainHandItem());
			cooldown.addCooldown(NBTUtils.getId(player.getMainHandItem()), current.gun
					.getShootTime());
			LogUtils.getLogger().info("shoot2");
			recoil.setShoot();
			player.setXRot(player.getXRot()-(float)(Math.random() * current.getKnockback()));
			player.setYRot(player.getYRot()+(float)(Math.random() * current.getKnockback()));
		}
	}
	
	// Rendering
	
	public void render(PoseStack matrix, MultiBufferSource buffer, int light) {
		LocalPlayer player = Minecraft.getInstance().player;
		ItemStack stack = player.getMainHandItem();
		if(current != null) {
			current.tick(player, stack);
			current.render(player, stack, buffer, matrix, light);
		}
	}
	
	public void left() {
		if(current != null) {
			part = (part - 1 + current.getParts().length) % current.getParts().length;
			GunModelPart gunPart = current.getParts()[part];
			if(gunPart != null) {
				LogUtils.getLogger().info("Left: " + part + " name: " + gunPart.getName());
			} else {
				LogUtils.getLogger().info("Left: " + part);
			}
		}
	}
	
	public void right() {
		if(current != null) {
			part = (part + 1) % current.getParts().length;
			GunModelPart gunPart = current.getParts()[part];
			if(gunPart != null) {
				LogUtils.getLogger().info("Right: " + part + " name: " + gunPart.getName());
			} else {
				LogUtils.getLogger().info("Right: " + part);
			}
		}
	}
	
	public void inc(int type) {
		float v = 0.01f;
		float vr = 1f;
		if(current != null) {
			if(mode == 0) {
				GunModelPart gunPart = current.getParts()[part];
				if(gunPart != null) {
					if(!display) {
						gunPart.getTransform().pos[type] += v;
						LogUtils.getLogger().info("Transform pos: x: " + current.getParts()[part].getTransform().pos[0] + " y: " + current.getParts()[part].getTransform().pos[1] + " z: " + current.getParts()[part].getTransform().pos[2]);
					} else {
						gunPart.getDTransform().pos[type] += v;
						LogUtils.getLogger().info("Transform pos: x: " + current.getParts()[part].getDTransform().pos[0] + " y: " + current.getParts()[part].getDTransform().pos[1] + " z: " + current.getParts()[part].getDTransform().pos[2]);
					}
				}
			} else if(mode == 1) {
				GunModelPart gunPart = current.getParts()[part];
				if(gunPart != null) {
					if(!display) {
						current.getParts()[part].getTransform().rot[type] += (float)Math.toRadians(vr);
						LogUtils.getLogger().info("Transform Rad x: " + current.getParts()[part].getTransform().rot[0]
								+ " y: " + 
								current.getParts()[part].getTransform().rot[1]
								+ " z: " 
								+ current.getParts()[part].getTransform().rot[2] + 
								" Deg x: " + 
								(float)Math.toDegrees(current.getParts()[part].getTransform().rot[0])
								+ " y: " + 
								(float)Math.toDegrees(current.getParts()[part].getTransform().rot[1]) 
								+ " z: " 
								+ (float)Math.toDegrees(current.getParts()[part].getTransform().rot[2]));
					} else {
						current.getParts()[part].getDTransform().rot[type] += (float)Math.toRadians(vr);
						LogUtils.getLogger().info("Transform Rad x: " + current.getParts()[part].getDTransform().rot[0]
								+ " y: " + 
								current.getParts()[part].getDTransform().rot[1]
								+ " z: " 
								+ current.getParts()[part].getDTransform().rot[2] + 
								" Deg x: " + 
								(float)Math.toDegrees(current.getParts()[part].getDTransform().rot[0])
								+ " y: " + 
								(float)Math.toDegrees(current.getParts()[part].getDTransform().rot[1]) 
								+ " z: " 
								+ (float)Math.toDegrees(current.getParts()[part].getDTransform().rot[2]));
					}
				}
			} else if(mode == 2) {
				GunModelPart gunPart = current.getParts()[part];
				if(gunPart != null) {
					if(!display) {
						gunPart.getTransform().sca[type] += v;
						LogUtils.getLogger().info("Transform sca: x: " + current.getParts()[part].getTransform().sca[0] + " y: " + current.getParts()[part].getTransform().sca[1] + " z: " + current.getParts()[part].getTransform().sca[2]);
					} else {
						gunPart.getDTransform().sca[type] += v;
						LogUtils.getLogger().info("Transform sca: x: " + current.getParts()[part].getDTransform().sca[0] + " y: " + current.getParts()[part].getDTransform().sca[1] + " z: " + current.getParts()[part].getDTransform().sca[2]);
					}
				}
			}
		}
	}
	
	public void dec(int type) {
		float v = 0.01f;
		float vr = 1f;
		if(current != null) {
			if(mode == 0) {
				GunModelPart gunPart = current.getParts()[part];
				if(gunPart != null) {
					if(!display) {
						gunPart.getTransform().pos[type] -= v;
						LogUtils.getLogger().info("Transform pos: x: " + current.getParts()[part].getTransform().pos[0] + " y: " + current.getParts()[part].getTransform().pos[1] + " z: " + current.getParts()[part].getTransform().pos[2]);
					} else {
						gunPart.getDTransform().pos[type] -= v;
						LogUtils.getLogger().info("Transform pos: x: " + current.getParts()[part].getDTransform().pos[0] + " y: " + current.getParts()[part].getDTransform().pos[1] + " z: " + current.getParts()[part].getDTransform().pos[2]);
					}
				}
			} else if(mode == 1) {
				GunModelPart gunPart = current.getParts()[part];
				if(gunPart != null) {
					if(!display) {
						current.getParts()[part].getTransform().rot[type] -= (float)Math.toRadians(vr);
						LogUtils.getLogger().info("Transform Rad x: " + current.getParts()[part].getTransform().rot[0]
								+ " y: " + 
								current.getParts()[part].getTransform().rot[1]
								+ " z: " 
								+ current.getParts()[part].getTransform().rot[2] + 
								" Deg x: " + 
								(float)Math.toDegrees(current.getParts()[part].getTransform().rot[0])
								+ " y: " + 
								(float)Math.toDegrees(current.getParts()[part].getTransform().rot[1]) 
								+ " z: " 
								+ (float)Math.toDegrees(current.getParts()[part].getTransform().rot[2]));
					} else {
						current.getParts()[part].getDTransform().rot[type] -= (float)Math.toRadians(vr);
						LogUtils.getLogger().info("Transform Rad x: " + current.getParts()[part].getDTransform().rot[0]
								+ " y: " + 
								current.getParts()[part].getDTransform().rot[1]
								+ " z: " 
								+ current.getParts()[part].getDTransform().rot[2] + 
								" Deg x: " + 
								(float)Math.toDegrees(current.getParts()[part].getDTransform().rot[0])
								+ " y: " + 
								(float)Math.toDegrees(current.getParts()[part].getDTransform().rot[1]) 
								+ " z: " 
								+ (float)Math.toDegrees(current.getParts()[part].getDTransform().rot[2]));
					}
				}
			} else if(mode == 2) {
				GunModelPart gunPart = current.getParts()[part];
				if(gunPart != null) {
					if(!display) {
						gunPart.getTransform().sca[type] -= v;
						LogUtils.getLogger().info("Transform sca: x: " + current.getParts()[part].getTransform().sca[0] + " y: " + current.getParts()[part].getTransform().sca[1] + " z: " + current.getParts()[part].getTransform().sca[2]);
					} else {
						gunPart.getDTransform().sca[type] -= v;
						LogUtils.getLogger().info("Transform sca: x: " + current.getParts()[part].getDTransform().sca[0] + " y: " + current.getParts()[part].getDTransform().sca[1] + " z: " + current.getParts()[part].getDTransform().sca[2]);
					}
				}
			}
		}
	}
	
	public void selectGunModel() {
		Item gun = Minecraft.getInstance().player.getMainHandItem().getItem();
		this.current = GunModelsHandler.get(gun.getDescriptionId());
		//LogUtils.getLogger().info("selecting gunModel");
	}
	
	public void switchRotationMode() {
		mode = (mode + 1) % 3;
		String modeS = "";
		switch(mode) {
		case 0:
			modeS = "Translate";
			break;
		case 1:
			modeS = "Rotate";
			break;
		case 2:
			modeS = "Scale";
			break;
		}
		LogUtils.getLogger().info("Mode: " + modeS);
	}
	
	public void setGunModel(GunModel model) {
		this.current = model;
	}
	
	public GunModel getGunModel() {
		return current;
	}

	public SprintHandler getSprintHandler() {
		return sprint;
	}

	public AimHandler getAimHandler() {
		return aim;
	}
	
	public RecoilHandler getRecoilHandler(){
		return recoil;
	}
	
	public HitmarkerHandler getHitmarker() {
		return hitmarker;
	}
	
	public CooldownHandler getCooldown() {
		return cooldown;
	}
	
}
