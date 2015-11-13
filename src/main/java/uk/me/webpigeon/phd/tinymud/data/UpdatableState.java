package uk.me.webpigeon.phd.tinymud.data;

import uk.me.webpigeon.phd.tinymud.Precept;

public interface UpdatableState extends GameState {

	// used by moves
	public void setLocation(String who, String newRoomID);
	public void addPrecept(String who, Precept event);
	public void addAgent(String agentID);
	public void addLink(String src, String dest);
	
}
