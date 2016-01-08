package uk.me.webpigeon.phd.mud.engine;

public class Percept {
	private MessageType type;
	
	public Percept(MessageType type) {
		this.type = type;
	}

	public MessageType getType() {
		return type;
	}

}
