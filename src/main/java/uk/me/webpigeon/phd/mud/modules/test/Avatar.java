package uk.me.webpigeon.phd.mud.modules.test;

import javax.jdo.annotations.PersistenceCapable;

import uk.me.webpigeon.phd.mud.persist.GameData;

@PersistenceCapable
public class Avatar extends GameData {
	private final String name;
	private String room;
	
	public Avatar(String name) {
		super(name);
		this.name = name;
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setCurrentRoom(String roomID) {
		this.room = roomID;
	}
	
	public String getCurrentRoom() {
		return room;
	}

}
