package uk.me.webpigeon.phd.tinymud;

import java.util.List;

import uk.me.webpigeon.phd.tinymud.data.UpdatableState;

public class LinkInfomation implements Precept {
	private static final String INFOFMT = "LINKS(%s,%s)";
	private String roomID;
	private List<String> links;
	
	public LinkInfomation(String roomID, List<String> links) {
		this.roomID = roomID;
		this.links = links;
	}

	@Override
	public void update(UpdatableState model) {
		for (String link : links) {
			model.addLink(roomID, link);
		}
	}
	
	@Override
	public String toString() {
		return String.format(INFOFMT, roomID, links);
	}

}
