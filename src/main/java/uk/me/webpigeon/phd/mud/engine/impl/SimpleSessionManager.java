package uk.me.webpigeon.phd.mud.engine.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.google.inject.Singleton;

import uk.me.webpigeon.phd.mud.engine.Session;
import uk.me.webpigeon.phd.mud.engine.SessionManager;

@Singleton
public class SimpleSessionManager implements SessionManager {
	private Map<UUID, Session> sessions;
	
	public SimpleSessionManager() {
		this.sessions = new HashMap<UUID, Session>();
	}

	public UUID register(Session session) {
		UUID id = UUID.randomUUID();
		sessions.put(id, session);
		session.setID(id);
		session.onRegister(id);
		return id;
	}
	
	@Override
	public Session get(Object sessionID) {
		return sessions.get(sessionID);
	}

	@Override
	public void terminate(UUID id) {
		Session session = sessions.remove(id);
		if (session != null) {
			session.onTerminate();
		}
	}

	@Override
	public void reap() {
		List<UUID> reapList = new ArrayList<UUID>();
		
		for (Map.Entry<UUID,Session> entry : sessions.entrySet()){
			UUID id = entry.getKey();
			Session session  = entry.getValue();
			
			if (session.isDead()) {
				reapList.add(id);
			}
		}
		
		for (UUID id : reapList) {
			terminate(id);
		}
		
	}

}
