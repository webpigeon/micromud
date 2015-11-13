package uk.me.webpigeon.phd.tinymud.world;

public class NodeRoom extends Room {
	private final String STR_FORMAT = "ROOM(%s)";
	public final String name;
	
	public NodeRoom(String name) {
		this.name = name;
	}
	
	@Override
	public String toID() {
		return name;
	}
	
	@Override
	public String toString() {
		return String.format(STR_FORMAT, name);
	}

}
