package uk.me.webpigeon.phd.mud.modules.test;

import javax.jdo.PersistenceManager;

import com.google.inject.Inject;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.Context;
import uk.me.webpigeon.phd.mud.engine.Session;
import uk.me.webpigeon.phd.mud.modules.world.Room;
import uk.me.webpigeon.phd.mud.modules.world.WorldService;

public class DebugSpawn implements Command {
	private static final String SPAWN_ROOM = "main-street";
	
	private AvatarService avatars;
	private WorldService world;
	
	@Inject
	public DebugSpawn(AvatarService avatars, WorldService world) {
		this.avatars = avatars;
		this.world = world;
	}

	@Override
	public void execute(Context context, PersistenceManager pm) {
		
		Session session = context.getSession();
		if (session.getAvatar() != null) {
			//Player already has an avatar
			return;
		}
		
		//create a new dummy avatar
		Avatar dummyAvatar = avatars.createNew("DummyAvatar-"+System.currentTimeMillis());
		System.out.println("dummy avatar created");
		AvatarPercept percept = new AvatarPercept(dummyAvatar);
		session.setAvatar(dummyAvatar);
		//session.addPercept(percept);
		
		//put the dummy avatar in the spawn location
		Room spawn = world.getRoom(SPAWN_ROOM);
		spawn.addAvatar(dummyAvatar);
		//session.addPercept(new RoomPercept(spawn));
	}
}
