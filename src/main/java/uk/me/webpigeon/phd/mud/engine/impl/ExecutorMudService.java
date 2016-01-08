package uk.me.webpigeon.phd.mud.engine.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

import com.google.inject.Singleton;

import uk.me.webpigeon.phd.mud.engine.Context;
import uk.me.webpigeon.phd.mud.engine.MudProcess;
import uk.me.webpigeon.phd.mud.engine.MudService;
import uk.me.webpigeon.phd.mud.engine.Task;

/**
 * Mud service using executors as a backend.
 * 
 * This is a fairly unrefined and unoptimised stratergy but it'll do for testing.
 */
@Singleton
public class ExecutorMudService implements MudService {
	private static final Integer NUM_WORKERS = 1;
	private static final Integer NUM_TIMERS = 1;
	private static final Logger LOG = Logger.getLogger(ExecutorMudService.class.getCanonicalName());
	
	private final ExecutorService pool;
	private final ScheduledExecutorService timers;
	
	public ExecutorMudService() {
		this.pool = Executors.newFixedThreadPool(NUM_WORKERS);
		this.timers = Executors.newScheduledThreadPool(NUM_TIMERS);
	}
	
	@Override
	public void process(Context context, Task task) {
		LOG.fine("Got task "+task+" with context "+context);
		pool.submit(new MudProcess(context, task));
	}

	@Override
	public void process(Task task) {
		LOG.fine("Got task "+task+" from system");
		pool.submit(new MudProcess(null, task));
	}

}
