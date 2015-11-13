package uk.me.webpigeon.phd.tinymud;

import uk.me.webpigeon.phd.tinymud.data.UpdatableState;

public class LocationChanged implements Precept {
	private static final String INFOFMT = "AT(%s,%s)";
	public final String agentID;
	public final String newLocation;
	
	public LocationChanged(String agentID, String newLocation) {
		this.agentID = agentID;
		this.newLocation = newLocation;
	}

	@Override
	public void update(UpdatableState model) {
		model.setLocation(agentID, newLocation);
	}
	
	public String toString() {
		return String.format(INFOFMT, agentID, newLocation);
	}
}
