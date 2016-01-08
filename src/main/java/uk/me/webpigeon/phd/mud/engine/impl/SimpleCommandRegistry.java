package uk.me.webpigeon.phd.mud.engine.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Singleton;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.CommandRegistry;

@Singleton
public class SimpleCommandRegistry implements CommandRegistry {
	private Map<String, Command> commands;
	
	public SimpleCommandRegistry(){
		this.commands = new HashMap<>();
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
