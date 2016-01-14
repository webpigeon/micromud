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
import uk.me.webpigeon.phd.mud.protocol.MudCore;
import uk.me.webpigeon.phd.mud.protocol.MudWorld;

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
			
			MudWorld.ExitsPercept.Builder epb = MudWorld.ExitsPercept.newBuilder();
			epb.setId(room.getName());
			
			//build room link description
			for (Map.Entry<Direction, String> entry : links.entrySet()) {
				MudWorld.RoomExit.Builder reb = MudWorld.RoomExit.newBuilder();
				reb.setRoomId(entry.getValue());
				reb.setDirection(entry.getKey().toString());
				epb.addExit(reb.build());
			}
			
			//build the server response
			MudCore.ServerResponse.Builder resp = MudCore.ServerResponse.newBuilder();
			resp.setType(MudWorld.EXITS_EXT_FIELD_NUMBER);
			resp.setExtension(MudWorld.exitsExt, epb.build());
			
			currentUser.addPercept(resp.build());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	

}
