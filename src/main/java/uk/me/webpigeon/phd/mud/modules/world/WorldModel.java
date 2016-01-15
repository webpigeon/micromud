package uk.me.webpigeon.phd.mud.modules.world;

import java.util.Collection;

public interface WorldModel {

	Room getPlayerRoom(String account);

	Room getRoomAt(Room room, Direction direction);

	void addPlayerRoom(String account, Room room);
	void delPlayerRoom(String account, Room room);
	
	void setPlayerRoom(String account, Room newRoom);

	void createRoom(Room room);

	void link(Room mainStreet, Room bakery, Direction west);

	Room getRoomAt(String roomID);

	Collection<Direction> getExits(Room room);

}
