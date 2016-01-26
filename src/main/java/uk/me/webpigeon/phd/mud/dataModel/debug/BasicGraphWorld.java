package uk.me.webpigeon.phd.mud.dataModel.debug;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import uk.me.webpigeon.phd.mud.modules.world.Direction;
import uk.me.webpigeon.phd.mud.modules.world.Room;
import uk.me.webpigeon.phd.mud.modules.world.RoomLink;
import uk.me.webpigeon.phd.mud.modules.world.WorldModel;

/**
 * An exersize in why you shouldn't emulate databases using maps.
 */
public class BasicGraphWorld implements WorldModel {

	private final Map<String, Room> playerLocations;
	private final Map<Room, Collection<String>> peopleInRoom;
	private final Map<String, Room> rooms;
	private final Map<Room, Map<Direction, RoomLink>> links;

	public BasicGraphWorld() {
		this.playerLocations = new HashMap<String, Room>();
		this.peopleInRoom = new HashMap<Room, Collection<String>>();
		this.rooms = new HashMap<String, Room>();
		this.links = new HashMap<Room, Map<Direction, RoomLink>>();
	}

	@Override
	public Room getPlayerRoom(String account) {
		return playerLocations.get(account);
	}

	@Override
	public Room getRoomAt(Room room, Direction direction) {
		RoomLink link = getLink(room, direction);
		if (link == null) {
			return null;
		}
		return link.room;
	}

	@Override
	public void addPlayerRoom(String account, Room room) {
		playerLocations.put(account, room);

		Collection<String> people = peopleInRoom.get(account);
		if (people == null) {
			people = new HashSet<String>();
			peopleInRoom.put(room, people);
		}
		people.add(account);
	}

	@Override
	public void delPlayerRoom(String account, Room room) {
		if (playerLocations.get(account) == room) {
			playerLocations.put(account, null);
		}

		Collection<String> people = peopleInRoom.get(account);
		if (people != null) {
			people.remove(account);
		}
	}

	@Override
	public void setPlayerRoom(String account, Room newRoom) {
		// remove the player from the old room
		Room currentRoom = playerLocations.get(account);
		if (currentRoom != null) {
			Collection<String> peopleInCurrentRoom = peopleInRoom.get(currentRoom);
			peopleInCurrentRoom.remove(account);
		}

		// add the player to the new room
		playerLocations.put(account, newRoom);
		Collection<String> people = peopleInRoom.get(newRoom);
		if (people == null) {
			people = new HashSet<String>();
			peopleInRoom.put(newRoom, people);
		}
		people.add(account);
	}

	@Override
	public void createRoom(Room room) {
		rooms.put(room.getName(), room);
		links.put(room, new EnumMap<Direction, RoomLink>(Direction.class));
	}

	@Override
	public void link(Room from, Room to, Direction direction) {
		RoomLink link = new RoomLink(to, direction);

		Map<Direction, RoomLink> roomLinks = links.get(from);
		roomLinks.put(direction, link);
	}

	@Override
	public Room getRoomAt(String roomID) {
		return rooms.get(roomID);
	}

	@Override
	public Collection<Direction> getExits(Room room) {
		Map<Direction, RoomLink> roomLinks = links.get(room);
		if (roomLinks == null) {
			return Collections.emptyList();
		}

		return roomLinks.keySet();
	}

	@Override
	public RoomLink getLink(Room room, Direction d) {
		Map<Direction, RoomLink> rooms = links.get(room);
		if (rooms == null) {
			return null;
		}

		return rooms.get(d);
	}

}
