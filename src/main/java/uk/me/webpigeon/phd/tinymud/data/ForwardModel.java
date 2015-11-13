package uk.me.webpigeon.phd.tinymud.data;

import java.util.List;

import uk.me.webpigeon.phd.tinymud.AgentController;
import uk.me.webpigeon.phd.tinymud.Move;

public interface ForwardModel {
	
	public List<String> getAgentIDs();
	
	/**
	 * Get the score for a given agent
	 * 
	 * @param agent the agent you want the score for
	 * @return the score of the agent
	 */
	public double getScore(String agentID);
	
	/**
	 * Advance this model by a step
	 * 
	 * @param agent the agent to simulate
	 * @param move the move the agent made
	 */
	public void simulate(String agentID, Move move);
	
	/**
	 * Create an exact copy of this model
	 * 
	 * @return the copy of this model
	 */
	public ForwardModel copy();

}
