package uk.me.webpigeon.phd.mud.modules.world.impl;

import javax.jdo.PersistenceManager;

import com.google.inject.Inject;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.Context;
import uk.me.webpigeon.phd.mud.engine.Session;
import uk.me.webpigeon.phd.mud.modules.test.Avatar;
import uk.me.webpigeon.phd.mud.modules.world.Room;
import uk.me.webpigeon.phd.mud.modules.world.WorldService;
import uk.me.webpigeon.phd.mud.protocol.MudCore;
import uk.me.webpigeon.phd.mud.protocol.MudWorld;

public class LookCommand implements Command {
	private WorldService world;
	
	@Inject
	public LookCommand(WorldService world){
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
			
			MudWorld.RoomPercept rp = MudWorld.RoomPercept.newBuilder().setId(room.getID()).setName(room.getName()).setDescription(room.getDescription()).build();
			MudCore.ServerResponse.Builder resp = MudCore.ServerResponse.newBuilder();
			resp.setType(MudWorld.ROOM_EXT_FIELD_NUMBER);
			resp.setExtension(MudWorld.roomExt, rp);
			
			currentUser.addPercept(resp.build());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	

}
