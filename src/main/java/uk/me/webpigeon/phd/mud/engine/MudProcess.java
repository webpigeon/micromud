package uk.me.webpigeon.phd.mud.engine;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

public class MudProcess implements Runnable {
	private Context context;
	private Task task;
	
	public MudProcess(Context context, Task task) {
		this.context = context;
		this.task = task;
	}
	
	@Override
	public void run() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Tutorial");
		PersistenceManager pm = pmf.getPersistenceManager();
		
		Transaction tx = pm.currentTransaction();
		
		try {
			tx.begin();
			task.execute(context, pm);
			tx.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

}
