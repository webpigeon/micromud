package uk.me.webpigeon.phd.mud.modules.test;

import uk.me.webpigeon.phd.mud.engine.MessageType;
import uk.me.webpigeon.phd.mud.engine.Percept;

public class AvatarPercept extends Percept {
	private final String name;
	
	public AvatarPercept(Avatar avatar) {
		this(avatar.getName());
	}
	
	public AvatarPercept(final String name) {
		super(MessageType.NARRATIVE);
		this.name = name;
	}
	
	public String toString() {
		return "You see "+name;
	}

}
