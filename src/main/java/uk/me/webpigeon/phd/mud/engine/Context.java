package uk.me.webpigeon.phd.mud.engine;

import java.util.List;
import java.util.UUID;

public class Context {
	private final Session session;
	private final List<String> args;
	
	public Context(Session session, List<String> args) {
		assert session != null;
		assert args != null;
		
		this.session = session;
		this.args = args;
	}

	public Session getSession() {
		return session;
	}

	public String getArg(int pos, String defaultValue) {
		assert pos >=0;
		
		if (args.size() > pos) {
			return args.get(pos);
		}
		
		return defaultValue;
	}
	
}
