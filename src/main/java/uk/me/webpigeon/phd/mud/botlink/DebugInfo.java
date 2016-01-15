package uk.me.webpigeon.phd.mud.botlink;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;

/**
 * This is mostly useful for my debugging.
 */
public class DebugInfo extends AnnotationModule {

	public DebugInfo() {
		super("debug");
	}

	@Command("sessionKey")
	public void printSessionKey(Message message) {
		message.respond("Your session key is "+message.getSessionKey());
	}
	
	@Command("session")
	public void printSession(Message message) {
		message.respond("Your session "+message.getSession());
	}
	
	@Command("ping")
	public void doPing(Message message) {
		message.respond("pong");
	}
	
	@Command("blow-up")
	public void testException(Message message) {
		throw new RuntimeException("exception text");
	}
	
}
