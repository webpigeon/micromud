package uk.me.webpigeon.phd.mud.engine;

import java.util.UUID;

import com.google.protobuf.Message;

import uk.me.webpigeon.phd.mud.modules.test.Avatar;
import uk.me.webpigeon.phd.mud.protocol.MudCore.ServerResponse;

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

	@Deprecated
	public abstract void addPercept(Percept percept);
	
	public void addPercept(ServerResponse message){
		System.out.println("something went bananas");
	}
	
	public abstract boolean isDead();

	public void onRegister(UUID id) {}

	public void onTerminate() {}
	
}
