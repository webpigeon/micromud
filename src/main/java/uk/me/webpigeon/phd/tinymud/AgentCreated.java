package uk.me.webpigeon.phd.tinymud;

import uk.me.webpigeon.phd.tinymud.data.UpdatableState;

public class AgentCreated implements Precept {
	private static final String INFOFMT = "AGENT(%s)";
	public String agentID;
	
	public AgentCreated(String agentID) {
		this.agentID = agentID;
	}

	@Override
	public void update(UpdatableState model) {
		model.addAgent(agentID);
		
	}
	
	public String toString() {
		return String.format(INFOFMT, agentID);
	}

}
