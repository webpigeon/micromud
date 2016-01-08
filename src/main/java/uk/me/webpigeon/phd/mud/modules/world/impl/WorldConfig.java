package uk.me.webpigeon.phd.mud.modules.world.impl;

import java.util.List;
import java.util.Map;

import uk.me.webpigeon.phd.mud.modules.world.Direction;

public class WorldConfig {
	public List<RoomData> rooms;
	public Map<String, Map<Direction, String>> links;
	
	class RoomData{
		String id;
		String name;
		String description;
	}
	
}
