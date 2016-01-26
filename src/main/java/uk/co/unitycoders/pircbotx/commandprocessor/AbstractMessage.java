package uk.co.unitycoders.pircbotx.commandprocessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.co.unitycoders.pircbotx.security.Session;

public abstract class AbstractMessage implements Message {
	private final List<String> args;
	private final String sessionKey;

	private Session session;

	public AbstractMessage(List<String> args, String sessionKey) {
		this.args = new ArrayList<String>(args);
		this.sessionKey = sessionKey;
		this.session = null;
	}

	@Override
	public Session getSession() {
		return session;
	}

	@Override
	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public String getSessionKey() {
		return sessionKey;
	}

	@Override
	public String getMessage() {
		if (args.size() <= 2) {
			return "";
		}

		// get the argument list
		List<String> cmdArgs = args.subList(2, args.size());

		// emulate String.join in java 1.7
		StringBuilder argStr = new StringBuilder();
		Iterator<String> argItr = cmdArgs.iterator();
		while (argItr.hasNext()) {
			argStr.append(argItr.next());
			if (argItr.hasNext()) {
				argStr.append(" ");
			}
		}

		return argStr.toString();
	}

	@Override
	public String getArgument(int id, String defaultValue) {
		if (args == null || args.size() <= id) {
			return defaultValue;
		}

		return args.get(id);
	}

	@Override
	public void insertArgument(int pos, String arg) {
		args.add(pos, arg);
	}

}
