package com.jg.oldguns.client.handlers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.User;

public class ClientsHandler {

	private static Map<String, ClientHandler> clients = new HashMap<>();
	
	public static void register(User user, ClientHandler client) {
		clients.put(user.getUuid().toString(), client);
	}
	
	public static ClientHandler getClient(User user) {
		return clients.get(user.getUuid().toString());
	}
	
	public static ClientHandler getClient(String user) {
		return clients.get(user);
	}
	
}
