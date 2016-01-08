package uk.me.webpigeon.phd.mud.engine;

public interface CommandRegistry {

	public void register(String verb, Command command);
	
	public void alias(String alias, String verb);

	public Command get(String verb);
	
}
