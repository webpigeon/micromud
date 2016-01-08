package uk.me.webpigeon.phd.mud.engine;

public interface MudService {
	
	/**
	 * Execute a user initiated task
	 * 
	 * @param user the user to execute the task for
	 * @param task the task to execute
	 */
	public void process(Context context, Task task);
	
	/**
	 * Execute a system initiated task
	 * 
	 * @param task the task to execute
	 */
	public void process(Task task);
	
}
