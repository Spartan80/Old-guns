package com.jg.oldguns.client.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mojang.logging.LogUtils;

public class CooldownHandler {

	private Map<String, Stuff> cooldowns;
	
	public CooldownHandler() {
		cooldowns = new HashMap<>();
	}
	
	public void tick() {
		for(Entry<String, Stuff> e : cooldowns.entrySet()) {
			if(cooldowns.get(e.getKey()).prog == 0) {
				cooldowns.remove(e.getKey());
				continue;
			}
			e.getValue().prev = e.getValue().prog;
			float p = Math.max(0, e.getValue().prog - 0.5f);
			e.getValue().prog = p;
			LogUtils.getLogger().info("Id: " + e.getKey() + " time: " + e.getValue().prog + 
					" progress: " + getProgress(e.getKey()));
		}
	}
	
	public void addCooldown(String id, float time) {
		cooldowns.put(id, new Stuff(time, time));
	}
	
	public float getProgress(String id) {
		if(cooldowns.containsKey(id)) {
			Stuff stuff = cooldowns.get(id);
			float v = (stuff.prev + (stuff.prog - stuff.prev)
					* (stuff.prev == 0 || 
							stuff.prev == stuff.max ? 0 : 
					ClientHandler.partialTicks)) / stuff.max;
			//v = stuff.prog / stuff.max;
			return v;
		}
		return 0;
	}
	
	public boolean hasCooldown(String id) {
		return cooldowns.containsKey(id);
	}
	
	public class Stuff {
		
		public float max;
		public float prog;
		public float prev;
		
		public Stuff(float max, float prog) {
			this.max = max;
			this.prog = prog;
			this.prev = prog;
		}
		
	}
	
}
