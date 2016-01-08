package uk.me.webpigeon.phd.mud.engine.impl;

import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.CommandRegistry;

@Singleton
public class SimpleCommandRegistry implements CommandRegistry {
	private Map<String, Command> commands;
	
	@Inject
	public SimpleCommandRegistry(Map<String,Command> commands){
		this.commands = commands;
	}
	
	public void register(String verb, Command command){
		commands.put(verb, command);		
	}
	
	public void alias(String alias, String verb){
		throw new RuntimeException("Pigeon hasn't added this yet.");
	}

	@Override
	public Command get(String verb) {
		return commands.get(verb);
	}

}
