package uk.me.webpigeon.phd.mud.engine;

import java.util.UUID;

import uk.me.webpigeon.phd.mud.modules.test.Avatar;

public abstract class Session {
	private UUID id;
	private Avatar avatar;
	
	public void setAvatar(Avatar avatar){
		this.avatar = avatar;
	}
	
	public Avatar getAvatar() {
		return avatar;
	}
	
	public final void setID(UUID id) {
		if (this.id != null) {
			throw new RuntimeException("Only the SessionManager should set the ID");
		}
		this.id = id;
	}
	
	public UUID getID() {
		return id;
	}

	public abstract void addPercept(Percept percept);
	public abstract boolean isDead();

	public void onRegister(UUID id) {}

	public void onTerminate() {}
	
}
