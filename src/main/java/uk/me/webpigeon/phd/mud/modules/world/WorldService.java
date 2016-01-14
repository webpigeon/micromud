package uk.me.webpigeon.phd.mud.modules.world;

import java.util.Collection;
import java.util.Map;


public interface WorldService {
	
	public void init();

	public Room getRoom(String roomFor);

	public Map<Direction, String> getLinks(String roomFor);

	public Room getRoomAt(Room currentRoom, Direction direction);

	public Collection<String> getPlayers(String id);
	
}
