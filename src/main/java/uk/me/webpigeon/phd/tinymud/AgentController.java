package uk.me.webpigeon.phd.tinymud;

import java.util.List;

import uk.me.webpigeon.phd.tinymud.data.GameState;
import uk.me.webpigeon.phd.tinymud.world.World;

public interface AgentController {

	public void setup(String worldType, String agentID);
	public Move getMove(GameState state, List<Precept> bundle);
	
}
