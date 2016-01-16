package uk.me.webpigeon.phd.mud.modules.world;

import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.security.Secured;
import uk.co.unitycoders.pircbotx.security.Session;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;

import java.util.Collection;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;

/**
 * Player movement, mud style.
 * 
 * You're probably thinking, what? why have you created seperate commands
 * for every movement direction, they're all basiclly the same command!
 * 
 * We'll you're right but MUDs let you use N and E and commands. The bot was
 * designed to support [plugin] [command] notation or [command] notation so
 * in order for this to work correctly N and E must be commands (this means
 * you can also use go N or go E).
 */
public class WorldCommands extends AnnotationModule {
	
	private WorldModel world;

	public WorldCommands(WorldModel world) {
		super("world");
		this.world = world;
	}
	
	@Command({"look", "l"})
	@Secured
	public void doLook(Message message) {
		
		Session session = message.getSession();
		if (session == null || !session.isLoggedIn()) {
			message.respond("You don't look logged in to me...");
			return;
		}
		
		String account = session.getProp(Account.NAME_PROP, null);
		if (account == null) {
			message.respond("You don't appear to be playing...");
			return;
		}
		
		Room room = world.getPlayerRoom(account);
		if (room != null) {
			message.respond(":: "+room.getName()+" ::");
			message.respond(room.getDescription());
		}
	}
	
	@Command({"exits", "ex"})
	@Secured
	public void doExits(Message message) {
		
		Session session = message.getSession();
		if (session == null || !session.isLoggedIn()) {
			message.respond("You don't look logged in to me...");
			return;
		}
		
		String account = session.getProp(Account.NAME_PROP, null);
		if (account == null) {
			message.respond("You don't appear to be playing...");
			return;
		}
		
		Room room = world.getPlayerRoom(account);
		Collection<Direction> exits = world.getExits(room);	
		message.respond("exits: "+exits);
	}

}
