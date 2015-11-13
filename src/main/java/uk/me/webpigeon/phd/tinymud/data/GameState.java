package uk.me.webpigeon.phd.tinymud.data;

import java.util.Collection;
import java.util.List;

import uk.me.webpigeon.phd.tinymud.Move;
import uk.me.webpigeon.phd.tinymud.Precept;
import uk.me.webpigeon.phd.tinymud.world.Item;

public interface GameState {

	//Get the agent IDs
	public int getAgentCount();
	public Collection<String> getAgentIDs();
	
	//dealing with rooms
	public String getLocation(String agentID);
	public Collection<String> getRoomIDs();
	public List<String> getLinks(String roomID);
	
	//dealing with items
	public List<Item> getItemsInRoom(String roomID);
	public List<Item> getInventory(String agentID);
	
	public List<Move> getLegalMoves(String agentID);
	
}
