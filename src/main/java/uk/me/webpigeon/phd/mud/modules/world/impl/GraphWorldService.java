package uk.me.webpigeon.phd.mud.modules.world.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Singleton;

import uk.me.webpigeon.phd.mud.MudServer;
import uk.me.webpigeon.phd.mud.modules.world.Direction;
import uk.me.webpigeon.phd.mud.modules.world.Room;
import uk.me.webpigeon.phd.mud.modules.world.WorldService;
import uk.me.webpigeon.phd.mud.modules.world.impl.WorldConfig.RoomData;

@Singleton
public class GraphWorldService implements WorldService {
	private Map<String, Room> rooms;
	private Map<String, Map<Direction, String>> links;
	private Map<String, List<String>> playerLocations;
	
	public GraphWorldService() {
		this.rooms = new HashMap<String, Room>();
		this.links = new HashMap<String, Map<Direction, String>>();
		this.playerLocations = new HashMap<String, List<String>>();
	}
	
	public void buildGraph() {
		WorldConfig config = MudServer.loadInternalFile("spec/world.json", WorldConfig.class);
		
		for (RoomData data : config.rooms) {
			createRoom(data.id, data);
		}
		
		for (Map.Entry<String, Map<Direction, String>> linkEntry : config.links.entrySet()) {
			String roomID = linkEntry.getKey();
			Map<Direction,String> links = linkEntry.getValue();
			this.links.put(roomID, links);
		}
	}
	
	public void createRoom(String id, RoomData data) {
		Room room = new Room(id);
		room.setName(data.name);
		room.setDescription(data.description);
		rooms.put(id, room);
	}
	
	public void createLink(String from, String to, Direction direction) {
		Map<Direction,String> linkMap = links.get(from);
		if (linkMap == null) {
			linkMap = new EnumMap<Direction, String>(Direction.class);
			links.put(from, linkMap);
		}
		linkMap.put(direction, to);
	}

	@Override
	public void init() {
		buildGraph();
	}

	@Override
	public Room getRoom(String roomID) {
		return rooms.get(roomID);
	}

	@Override
	public Map<Direction, String> getLinks(String roomFor) {
		return links.get(roomFor);
	}

	@Override
	public Room getRoomAt(Room currentRoom, Direction direction) {
		String currentRoomID = currentRoom.getID();
		Map<Direction, String> roomLinks = links.get(currentRoomID);
		
		if (roomLinks == null || !roomLinks.containsKey(direction)){
			return null;
		}

		String roomID = roomLinks.get(direction);
		return rooms.get(roomID);
	}
	
	public void setPlayerAt(String room, String player) {
		List<String> players = playerLocations.get(room);
		if (players == null) {
			players = new ArrayList<String>();
			playerLocations.put(room, players);
		}
		players.add(player);
	}
	
	public void rmPlayerAt(String room, String player) {
		List<String> players = playerLocations.get(room);
		if (players != null) {
			players.remove(player);
		}
	}

	@Override
	public Collection<String> getPlayers(String id) {
		List<String> players = playerLocations.get(id);
		if (players == null) {
			return Collections.emptyList();
		}
		
		return players;
	}

}
