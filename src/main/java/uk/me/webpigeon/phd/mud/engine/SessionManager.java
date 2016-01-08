package uk.me.webpigeon.phd.mud.engine;

import java.util.UUID;

public interface SessionManager {

	UUID register(Session session);
	Session get(Object sessionID);
	void terminate(UUID session);
	
	void reap();

}
