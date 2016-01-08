package uk.me.webpigeon.phd.mud.modules.world.impl;

import uk.me.webpigeon.phd.mud.engine.MessageType;
import uk.me.webpigeon.phd.mud.engine.Percept;

public class PlayerMovementPercept extends Percept {
	public final String who;
	public final String oldRoom;
	public final String newRoom;
	
	public PlayerMovementPercept(String who, String oldRoom, String newRoom) {
		super(MessageType.SYSTEM);
		this.who = who;
		this.oldRoom = oldRoom;
		this.newRoom = newRoom;
	}
	
	public String toString() {
		return String.format("%s moved from %s to %s", who, oldRoom, newRoom);
	}
	
}
