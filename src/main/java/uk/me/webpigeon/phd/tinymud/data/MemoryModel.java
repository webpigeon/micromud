package uk.me.webpigeon.phd.tinymud.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.me.webpigeon.phd.tinymud.ChangeRoom;
import uk.me.webpigeon.phd.tinymud.Move;
import uk.me.webpigeon.phd.tinymud.Precept;
import uk.me.webpigeon.phd.tinymud.world.Item;

/**
 * An informed model based on the precepts provided to the agent.
 * 
 * This allows agents which require a forward model to work in versions of the game even
 * if the game doesn't want to provide a forward model.
 */
public class MemoryModel implements InformedForwardModel, UpdatableState {
	private final List<String> rooms;
	private final List<String> agentIDs;
	private final Map<String, String> agentLocations;
	private final Map<String, List<String>> roomGraph;
	private final Map<String, List<Item>> roomItems;
	private final Map<String, List<Item>> inventories;
	
	public MemoryModel() {
		this.rooms = new ArrayList<String>();
		this.agentIDs = new ArrayList<String>();
		this.agentLocations = new TreeMap<String, String>();
		this.roomGraph = new TreeMap<String, List<String>>();
		this.roomItems = new TreeMap<String, List<Item>>();
		this.inventories = new TreeMap<String, List<Item>>();
	}

	public MemoryModel(MemoryModel mm) {
		this.rooms = new ArrayList<String>(mm.rooms);
		this.agentIDs = new ArrayList<String>(mm.agentIDs);
		this.agentLocations = new TreeMap<String, String>(mm.agentLocations);
		this.roomGraph = deepCopy(mm.roomGraph);
		this.roomItems = deepCopy(mm.roomItems);
		this.inventories = deepCopy(mm.inventories);
	}
	
	private <K,V> Map<K, List<V>> deepCopy(Map<K, List<V>> source) {
		Map<K, List<V>> copyMap = new TreeMap<>();
		for (Map.Entry<K, List<V>> entry : source.entrySet()) {
			List<V> copyList = new ArrayList<V>(entry.getValue());
			copyMap.put(entry.getKey(), copyList);
		}
		return copyMap;
	}

	@Override
	public List<String> getAgentIDs() {
		return Collections.unmodifiableList(agentIDs);
	}

	@Override
	public double getScore(String agentID) {
		return 0;
	}

	@Override
	public void simulate(String agentID, Move move) {
		move.execute(agentID, this);
	}

	@Override
	public String getLocation(String agentID) {
		return agentLocations.get(agentID);
	}

	@Override
	public List<String> getRoomIDs() {
		return Collections.unmodifiableList(rooms);
	}

	@Override
	public List<String> getLinks(String roomID) {
		return roomGraph.get(roomID);
	}

	@Override
	public List<Item> getItemsInRoom(String roomID) {
		return roomItems.get(roomID);
	}

	@Override
	public List<Item> getInventory(String agentID) {
		return inventories.get(agentID);
	}

	@Override
	public InformedForwardModel copy() {
		return new MemoryModel(this);
	}

	@Override
	public int getAgentCount() {
		return agentIDs.size();
	}

	@Override
	public List<Move> getLegalMoves(String agentID) {
		
		List<Move> moveList = new ArrayList<Move>();
		
		// get legal links for our current room
		String agentLocation = agentLocations.get(agentID);
		if (agentLocation != null) {
			List<String> legalLinks = roomGraph.get(agentLocation);
			
			if (legalLinks != null && !legalLinks.isEmpty()) {		
				for (String legalLink : legalLinks) {
					moveList.add(new ChangeRoom(legalLink));
				}
			}
		}
		
		return moveList;
	}

	@Override
	public void setLocation(String who, String newRoomID) {
		agentLocations.put(who, newRoomID);
	}

	@Override
	public void addPrecept(String who, Precept event) {
		//not overly important for us...
		
	}

	public void update(List<Precept> bundle) {
		System.out.println(bundle);
		for (Precept percept : bundle) {
			percept.update(this);
		}
	}

	@Override
	public void addAgent(String agentID) {
		agentIDs.add(agentID);
	}

	@Override
	public void addLink(String src, String dest) {
		List<String> roomLinks = roomGraph.get(src);
		if (roomLinks == null) {
			roomLinks = new ArrayList<String>();
			roomGraph.put(src, roomLinks);
		}
		
		roomLinks.add(dest);
	}

}
