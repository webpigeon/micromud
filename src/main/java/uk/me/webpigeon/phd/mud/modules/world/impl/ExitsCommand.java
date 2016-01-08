package uk.me.webpigeon.phd.mud.modules.world.impl;

import java.util.Collections;
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

public class ExitsCommand implements Command {
	private WorldService world;
	
	@Inject
	public ExitsCommand(WorldService world){
		this.world = world;
	}

	@Override
	public void execute(Context context, PersistenceManager pm) {
		
		try {
			Session currentUser = context.getSession();
			Avatar avatar = currentUser.getAvatar();
			
			String roomFor = context.getArg(1, avatar.getCurrentRoom());
			if (roomFor == null) {
				System.out.println("Room not provided");
				return;
			}
			
			Room room = world.getRoom(roomFor);
			if (room == null){
				System.out.println("Room "+roomFor+" is not valid");
				return;
			}
			
			Map<Direction, String> links = world.getLinks(roomFor);
			if (links == null) {
				links = Collections.emptyMap();
			}
			
			currentUser.addPercept(new ExitsPercept(room.getID(), links));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	

}
