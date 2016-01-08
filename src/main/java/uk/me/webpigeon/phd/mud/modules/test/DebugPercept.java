package uk.me.webpigeon.phd.mud.modules.test;

import uk.me.webpigeon.phd.mud.engine.MessageType;
import uk.me.webpigeon.phd.mud.engine.Percept;

public class DebugPercept extends Percept {
	private final String message;
	
	public DebugPercept(String message) {
		super(MessageType.DEBUG);
		this.message = message;
	}

	public String toString() {
		return message;
	}
	
}
