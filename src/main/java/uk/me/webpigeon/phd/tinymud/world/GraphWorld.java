package uk.me.webpigeon.phd.tinymud.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GraphWorld {
	private final Map<String, Room> rooms;
	private final Map<Room, List<Room>> links;
	
	public GraphWorld() {
		this.rooms = new TreeMap<String, Room>();
		this.links = new HashMap<Room, List<Room>>();
	}
	
	public void addLink(Room from, Room to) {
		assert from != null;
		assert to != null;
		assert rooms.containsValue(from);
		assert rooms.containsValue(to);
		
		List<Room> linkList = links.get(from);
		
		if (linkList == null) {
			linkList = new ArrayList<>();
			links.put(from, linkList);
		}
		
		linkList.add(to);
	}
	
	public void addDuelLink(Room from, Room to) {
		addLink(from, to);
		addLink(to, from);
	}
	
	public void addRoom(Room room) {
		rooms.put(room.toID(), room);
	}

	public Room getRoom(String key) {
		assert key != null;
		return rooms.get(key);
	}

	public List<String> getLinks(Room room) {
		System.out.println(links);
		
		List<Room> roomLinks = links.get(room);
		if (roomLinks == null) {
			return Collections.emptyList();
		}
		
		List<String> linkStr = new ArrayList<String>();
		for (Room linkedRoom : roomLinks) {
			linkStr.add(linkedRoom.toID());
		}
		
		return linkStr;
	}

	public Collection<String> getRoomIDs() {
		return Collections.unmodifiableCollection(rooms.keySet());
	}

}
