package com.jg.oldguns.client.helpers;

import com.jg.oldguns.client.ClientEventHandler;
import com.jg.oldguns.client.ClientHandler;
import com.jg.oldguns.client.handlers.ModelHandler;
import com.jg.oldguns.utils.Constants;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.DistExecutor;

public class InitHelper {
	public static DistExecutor.SafeRunnable start() {

		return new DistExecutor.SafeRunnable() {
			@Override
			public void run() {
				if (!ClientEventHandler.init) {
					ClientEventHandler.handlers.put(Minecraft.getInstance().player.getUUID(), new ClientHandler());
					ClientEventHandler.init = true;
				}
			}
		};
	}

	public static DistExecutor.SafeRunnable startRec(ItemStack stack) {

		return new DistExecutor.SafeRunnable() {
			@Override
			public void run() {
				if (ModelHandler.INSTANCE.cache.containsKey(stack.getOrCreateTag().getString(Constants.ID))) {
					ModelHandler.INSTANCE.cache.get(stack.getOrCreateTag().getString(Constants.ID)).reconstruct();
					System.out.println("reconstructing");
				}
			}
		};
	}
}
