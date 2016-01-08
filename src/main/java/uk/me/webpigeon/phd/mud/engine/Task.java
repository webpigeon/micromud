package uk.me.webpigeon.phd.mud.engine;

import javax.jdo.PersistenceManager;

public interface Task {
	
	public void execute(Context context, PersistenceManager pm);

}
