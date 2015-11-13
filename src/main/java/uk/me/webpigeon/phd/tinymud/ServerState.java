package uk.me.webpigeon.phd.tinymud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.me.webpigeon.phd.tinymud.data.UpdatableState;
import uk.me.webpigeon.phd.tinymud.world.GraphWorld;
import uk.me.webpigeon.phd.tinymud.world.Item;
import uk.me.webpigeon.phd.tinymud.world.Room;

public class ServerState implements UpdatableState {
	private List<String> agentIDs;
	private Map<String, String> agentLocations;
	private GameRuntime runtime;
	private GraphWorld world;

	public ServerState(GameRuntime gameRuntime, GraphWorld world) {
		assert gameRuntime != null;
		assert world != null;
		
		this.runtime = gameRuntime;
		this.world = world;
		this.agentIDs = new ArrayList<String>();
		this.agentLocations = new TreeMap<String, String>();
	}

	@Override
	public int getAgentCount() {
		return agentIDs.size();
	}

	@Override
	public Collection<String> getAgentIDs() {
		return Collections.unmodifiableList(agentIDs);
	}

	@Override
	public String getLocation(String agentID) {
		return agentLocations.get(agentID);
	}

	@Override
	public Collection<String> getRoomIDs() {
		return world.getRoomIDs();
	}

	@Override
	public List<String> getLinks(String roomID) {
		Room room = world.getRoom(roomID);
		if (room == null) {
			throw new RuntimeException("Invalid room provided!");
		}
		
		return world.getLinks(room);
	}

	@Override
	public List<Item> getItemsInRoom(String roomID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getInventory(String agentID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Move> getLegalMoves(String agentID) {
		throw new RuntimeException("Server side doens't care about this...");
	}

	@Override
	public void setLocation(String who, String newRoomID) {
		agentLocations.put(who, newRoomID);
	}

	@Override
	public void addPrecept(String agentID, Precept p) {
		runtime.addPrecept(agentID, p);
	}

	@Override
	public void addAgent(String agentID) {
		agentIDs.add(agentID);
	}

	@Override
	public void addLink(String src, String dest) {
		Room srcRoom = world.getRoom(src);
		Room destRoom = world.getRoom(dest);
		
		world.addLink(srcRoom, destRoom);
	}

}
