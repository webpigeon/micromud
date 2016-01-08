package uk.me.webpigeon.phd.mud.modules.test;

import javax.jdo.PersistenceManager;

import com.google.inject.Inject;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.Context;
import uk.me.webpigeon.phd.mud.engine.Session;
import uk.me.webpigeon.phd.mud.engine.SessionManager;

public class TestCommand implements Command {

	@Override
	public void execute(Context context, PersistenceManager pm) {
		Session session = context.getSession();
		session.addPercept(new DebugPercept("testing"));
	}

}
