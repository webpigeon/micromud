package uk.me.webpigeon.phd.mud.modules.world.impl;

import javax.jdo.PersistenceManager;

import com.google.inject.Inject;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.Context;
import uk.me.webpigeon.phd.mud.engine.Session;
import uk.me.webpigeon.phd.mud.modules.test.Avatar;
import uk.me.webpigeon.phd.mud.modules.world.Direction;
import uk.me.webpigeon.phd.mud.modules.world.Room;
import uk.me.webpigeon.phd.mud.modules.world.WorldService;

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
			
			//let the player know they moved
			currentUser.addPercept(new PlayerMovementPercept(avatar.getName(), currentRoom.getID(), newRoom.getID()));
			currentUser.addPercept(new RoomPercept(newRoom));
			currentUser.addPercept(new ExitsPercept(newRoom.getID(), world.getLinks(newRoom.getID())));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
