package com.jg.oldguns.client.handlers;

import com.jg.oldguns.client.ClientHandler;
import com.jg.oldguns.guns.ItemGun;
import com.jg.oldguns.guns.ItemGun.ShootMode;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class ShootHandler extends Handler{
	
	private ShootMode shootmode;
	private float burstTimer;
	private float burstTargetTime;
	private int times;
	private int bursts;
	private Minecraft mc;
	
	public ShootHandler(ClientHandler handler) {
		super(handler);
		shootmode = ShootMode.NONE;
		burstTimer = 0;
		burstTargetTime = 0;
		times = 0;
		bursts = 0;
		mc = Minecraft.getInstance();
	}
	
	public void tick() {
		if(shootmode == ShootMode.BURST) {
			if(times <= bursts) {
				if(burstTimer < burstTargetTime) {
					System.out.println("DeltaTime: " + mc.getDeltaFrameTime());
					burstTimer += mc.getDeltaFrameTime();
				}else {
					System.out.println("Shooting times: " + times + " burstTimer: " + burstTimer);
					ItemStack stack = mc.player.getMainHandItem();
					burstTimer = 0;
					times++;
					System.out.println("asds");
					((ItemGun)stack.getItem()).tryShoot(stack, handler);
				}
			}
		}
		/*if(shootmode != ShootMode.BURST) {
			if(times > 0) {
				if(times < targetTimes) {
					burstTimer += mc.getDeltaFrameTime();
				}else {
					times++;
				}
			}
		}else {
			if(times < bursts) {
				if(burstTimer < burstTargetTime) {
					burstTimer += mc.getDeltaFrameTime();
				}else {
					System.out.println("Shooting times: " + times + " burstTimer: " + burstTimer);
					ItemStack stack = mc.player.getMainHandItem();
					burstTimer = 0;
					times++;
					((ItemGun)stack.getItem()).tryShoot(stack, handler);
				}
			}
		}*/
	}
	
	public float getBurstTimer() {
		return burstTimer;
	}
	
	public int getTimes() {
		return times;
	}
	
	public void setBurstsAndTarget(int bursts, float burstTargetTime) {
		resetForShoot();
		this.bursts = bursts;
		this.burstTargetTime = burstTargetTime;
	}
	
	public void reset() {
		burstTimer = 0;
		burstTargetTime = 0;
		times = 0;
		bursts = 0;
	}
	
	public void resetForShoot() {
		burstTimer = 0;
		times = 0;
	}
}
