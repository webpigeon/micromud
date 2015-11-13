package uk.me.webpigeon.phd.tinymud;

import java.util.List;

import uk.me.webpigeon.phd.tinymud.data.GameState;
import uk.me.webpigeon.phd.tinymud.data.UpdatableState;

public class ChangeRoom implements Move {
	public final String newRoomID;
	
	public ChangeRoom(String newRoomID) {
		this.newRoomID = newRoomID;
	}

	@Override
	public void execute(String who, UpdatableState state) {
		String whoLocation = state.getLocation(who);
		
		List<String> moves = state.getLinks(whoLocation);
		if (!moves.contains(newRoomID)) {
			throw new IllegalMove(who, "Tried to move between "+whoLocation+" and "+newRoomID+" but no path exists");
		}
		
		state.setLocation(who, newRoomID);
		
		//it's the action's job to update the precepts (to allow for hidden infomation)
		Precept event = new LocationChanged(who, newRoomID);
		state.addPrecept(who, event);
		
		for (String agent : state.getAgentIDs()) {
			System.out.println("updating agent: "+agent);
			
			String agentLocation = state.getLocation(agent);
			if (whoLocation.equals(agentLocation) || newRoomID.equals(agentLocation)) {
				state.addPrecept(agent, event);
				
				if (!agent.equals(who)) {
					state.addPrecept(who, new LocationChanged(agent, newRoomID));
				}
			}
		}
	}

	@Override
	public String getReadableDescription() {
		return "move to <"+newRoomID+">";
	}
	
	public String toString() {
		return "MOVE("+newRoomID+")";
	}
	
}
