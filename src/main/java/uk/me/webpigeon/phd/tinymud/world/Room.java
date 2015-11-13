package uk.me.webpigeon.phd.tinymud.world;

import java.util.UUID;

public class Room {
	private UUID uuid;
	
	public Room() {
		this.uuid = UUID.randomUUID();
	}
	
	public String getUUID() {
		return uuid.toString();
	}
	
	public String toID(){
		return getUUID();
	}

}
