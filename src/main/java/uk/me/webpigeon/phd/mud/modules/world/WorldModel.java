package uk.me.webpigeon.phd.mud.modules.world;

import java.util.Collection;

public interface WorldModel {

	Room getPlayerRoom(String account);

	void setPlayerRoom(String account, Room newRoom);

	void createRoom(Room room);

	void link(Room from, Room to, Direction direction);

	Room getRoomAt(String roomID);

	Room getRoomAt(Room room, Direction direction);

	Collection<RoomLink> getExits(Room room);

	RoomLink getLink(Room room, Direction d);

}
