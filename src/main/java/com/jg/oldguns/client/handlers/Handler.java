package com.jg.oldguns.client.handlers;

import com.jg.oldguns.client.ClientHandler;

public abstract class Handler {
	
	ClientHandler handler;
	
	public Handler(ClientHandler handler) {
		this.handler = handler;
	}
}
