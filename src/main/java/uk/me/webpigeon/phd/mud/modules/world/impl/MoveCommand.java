package uk.me.webpigeon.phd.mud.modules.world.impl;

import java.util.Collection;
import java.util.Map;

import javax.jdo.PersistenceManager;

import com.google.inject.Inject;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.Context;
import uk.me.webpigeon.phd.mud.engine.Session;
import uk.me.webpigeon.phd.mud.modules.test.Avatar;
import uk.me.webpigeon.phd.mud.modules.world.Direction;
import uk.me.webpigeon.phd.mud.modules.world.Room;
import uk.me.webpigeon.phd.mud.modules.world.WorldService;
import uk.me.webpigeon.phd.mud.protocol.MudCore;
import uk.me.webpigeon.phd.mud.protocol.MudWorld;

public class MoveCommand implements Command {
	private WorldService world;

	@Inject
	public MoveCommand(WorldService world){
		this.world = world;
	}

	@Override
	public void execute(Context context, PersistenceManager pm) {
		
		try {
			Session currentUser = context.getSession();
			Avatar avatar = currentUser.getAvatar();
			
			String directionString = context.getArg(1, null);
			if (directionString == null) {
				//They didn't provide a direction
				System.out.println("no direction provided");
				return;
			}
			
			Direction direction = Direction.valueOf(directionString);
			if (direction == null) {
				System.out.println("provided invalid direction");
				return;
			}
			
			String currentRoomID = avatar.getCurrentRoom();
			Room currentRoom = world.getRoom(currentRoomID);
			if (currentRoom == null) {
				//ok, that's a problem.
				return;
			}
			
			Room newRoom = world.getRoomAt(currentRoom, direction);
			if (newRoom == null) {
				//no new room
				return;
			}
			
			//swap the avatar's rooms
			currentRoom.removeAvatar(avatar);
			newRoom.addAvatar(avatar);
			
			
			//TODO notify other players
			MudWorld.MovementPercept.Builder moved = MudWorld.MovementPercept.newBuilder().setWho(avatar.getName()).setOldRoom(currentRoom.getID()).setNewRoom(newRoom.getID());
			MudWorld.RoomPercept.Builder rp = MudWorld.RoomPercept.newBuilder().setId(newRoom.getID()).setName(newRoom.getName()).setDescription(newRoom.getDescription());
			
			Collection<Avatar> players = newRoom.getAvatars();
			for (Avatar rAvatar : players) {
				rp.addPlayer(rAvatar.getName());
			}
			
			Map<Direction,String> links = world.getLinks(newRoom.getID());
			for (Map.Entry<Direction, String> entry : links.entrySet()) {
				MudWorld.RoomExit.Builder reb = MudWorld.RoomExit.newBuilder();
				reb.setRoomId(entry.getValue());
				reb.setDirection(entry.getKey().toString());
				rp.addExit(reb.build());
			}
			
			MudCore.ServerResponse.Builder resp = MudCore.ServerResponse.newBuilder();
			resp.setType(MudWorld.ROOM_EXT_FIELD_NUMBER);
			resp.setExtension(MudWorld.roomExt, rp.build());
			currentUser.addPercept(resp.build());
			
			MudCore.ServerResponse.Builder resp2 = MudCore.ServerResponse.newBuilder();
			resp2.setType(MudWorld.MOVE_EXT_FIELD_NUMBER);
			resp2.setExtension(MudWorld.moveExt, moved.build());
			currentUser.addPercept(resp2.build());
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
