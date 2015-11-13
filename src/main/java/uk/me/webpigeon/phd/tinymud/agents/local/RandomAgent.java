package uk.me.webpigeon.phd.tinymud.agents.local;

import java.util.List;
import java.util.Random;

import uk.me.webpigeon.phd.tinymud.AgentController;
import uk.me.webpigeon.phd.tinymud.Move;
import uk.me.webpigeon.phd.tinymud.Precept;
import uk.me.webpigeon.phd.tinymud.data.GameState;
import uk.me.webpigeon.phd.tinymud.data.MemoryModel;

public class RandomAgent implements AgentController {
	private String agentID;
	private String worldType;
	private final Random random; 
	private MemoryModel memory;
	
	public RandomAgent(Random random){
		this.random = random;
		this.memory = new MemoryModel();
	}
	
	public RandomAgent() {
		this(new Random());
	}

	@Override
	public void setup(String worldType, String agentID) {
		this.agentID = agentID;
		this.worldType = worldType;
	}

	@Override
	public Move getMove(GameState state, List<Precept> bundle) {
		if (state == null){
			state = memory;
			memory.update(bundle);
		}
		
		List<Move> moves = state.getLegalMoves(agentID);
		if (moves.isEmpty()) {
			return null;
		}
		
		int randomMove = random.nextInt(moves.size());
		return moves.get(randomMove);
	}
	
	public String toString() {
		return "Agent(RandomAgent)";
	}

}
