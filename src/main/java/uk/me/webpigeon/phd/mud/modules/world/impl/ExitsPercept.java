package uk.me.webpigeon.phd.mud.modules.world.impl;

import java.util.Map;

import uk.me.webpigeon.phd.mud.engine.MessageType;
import uk.me.webpigeon.phd.mud.engine.Percept;
import uk.me.webpigeon.phd.mud.modules.world.Direction;

public class ExitsPercept extends Percept {
	public final String roomID;
	public final Map<Direction, String> links;
	
	public ExitsPercept(String roomID, Map<Direction,String> links) {
		super(MessageType.SYSTEM);
		this.roomID = roomID;
		this.links = links;
	}
	
	public String toString() {
		return String.format("Exits: %s", links.keySet());
	}

}
