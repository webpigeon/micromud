package uk.me.webpigeon.phd.tinymud;

import java.util.List;

import uk.me.webpigeon.phd.tinymud.data.GameState;

public interface AgentController {

	public void setup(String worldType, String agentID);
	public Move getMove(GameState state, List<Precept> bundle);
	
}
