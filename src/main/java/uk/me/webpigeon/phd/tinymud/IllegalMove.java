package uk.me.webpigeon.phd.tinymud;

public class IllegalMove extends RuntimeException {
	private String agentID;
	
	public IllegalMove(String agentID, String description) {
		super(description);
		this.agentID = agentID;
	}

}
