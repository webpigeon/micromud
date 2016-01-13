package uk.me.webpigeon.phd.mud.modules.world.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.google.inject.Inject;

import uk.me.webpigeon.phd.mud.modules.world.Direction;
import uk.me.webpigeon.phd.mud.modules.world.Room;
import uk.me.webpigeon.phd.mud.modules.world.WorldService;
import uk.me.webpigeon.phd.mud.persist.SQLModel;

public class DatabaseWorldService extends SQLModel<Room> implements WorldService {
	
	@Inject
	public DatabaseWorldService(Connection conn) {
		super(conn);
	}
	
	
	@Override
	public void init() {
	}

	@Override
	public Room getRoom(String roomFor) {
		return get(roomFor);
	}

	@Override
	public Map<Direction, String> getLinks(String roomFor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room getRoomAt(Room currentRoom, Direction direction) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected Room build(ResultSet rs) {
		try {
			String pk = rs.getString(0);
			
			Room room = new Room(pk);
			room.setName(rs.getString(1));
			room.setDescription(rs.getString(2));
			
			return room;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}


	@Override
	protected void buildStatements(Connection conn) {
		try {
			this.fetchAll = conn.prepareStatement("SELECT * FROM rooms");
			this.getOne = conn.prepareStatement("SELECT * FROM rooms WHERE pk=?");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
