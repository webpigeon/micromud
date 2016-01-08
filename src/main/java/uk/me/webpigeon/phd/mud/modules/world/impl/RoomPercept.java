package uk.me.webpigeon.phd.mud.modules.world.impl;

import java.util.Map;

import uk.me.webpigeon.phd.mud.engine.MessageType;
import uk.me.webpigeon.phd.mud.engine.Percept;
import uk.me.webpigeon.phd.mud.modules.world.Room;

public class RoomPercept extends Percept {
	public final String roomID;
	public final String name;
	public final String description;
	
	public RoomPercept(String roomID, String name, String description) {
		super(MessageType.SYSTEM);
		this.roomID = roomID;
		this.name = name;
		this.description = description;
	}
	
	public RoomPercept(Room room) {
		this(room.getID(), room.getName(), room.getDescription());
	}

	public String toString() {
		return String.format("%s: %s", name, description);
	}

}
