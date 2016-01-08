package uk.me.webpigeon.phd.mud.engine.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.CommandRegistry;
import uk.me.webpigeon.phd.mud.engine.Context;
import uk.me.webpigeon.phd.mud.engine.MudService;
import uk.me.webpigeon.phd.mud.engine.Session;

public class CommandParser {
	private final Logger LOG = Logger.getLogger(CommandParser.class.getCanonicalName());
	private final Pattern tokeniser;
	private final CommandRegistry registry;
	private final MudService processor;
	
	public CommandParser(CommandRegistry registry, MudService processor){
		this.tokeniser = Pattern.compile("([^\\s\"']+)|\"([^\"]*)\"|'([^']*)'");
		this.registry = registry;
		this.processor = processor;
	}
	
    public List<String> tokenise(String message) {
    	List<String> arguments = new ArrayList<String>();
    	
    	Matcher matcher = tokeniser.matcher(message);
    	while(matcher.find()) {
    		if (matcher.group(1) != null) {
    			arguments.add(matcher.group(1));
    		}else if (matcher.group(2) != null) {
    			arguments.add(matcher.group(2));
    		}else if (matcher.group(3) != null) {
    			arguments.add(matcher.group(3));
    		}
    	}
    	
    	return arguments;
    }
    
    public Command getCommand(String verb){
    	Command command = registry.get(verb);
    	return command;
    }
    
    public void process(Session session, String message) throws UnknownCommandException {
    	List<String> tokens = tokenise(message);
    	Context context = new Context(session, tokens);
    	
    	Command command = getCommand(tokens.get(0));
    	LOG.info(String.format("%s -> %s (%s)", message, tokens, command));
    	
    	if (command == null) {
    		throw new UnknownCommandException(message, tokens);
    	}
    	
    	processor.process(context, command);
    }

}
