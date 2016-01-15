package uk.me.webpigeon.phd.mud.modules.world;

import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.security.Secured;
import uk.co.unitycoders.pircbotx.security.Session;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;
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
public class PlayerMovement extends AnnotationModule {
	
	private WorldModel world;

	public PlayerMovement(WorldModel world) {
		super("go");
		this.world = world;
	}
	
	@Command({"N", "n", "north"})
	@Secured
	public void goNorth(Message message) {
		doMovement(message, Direction.NORTH);
	}
	
	@Command({"E", "e", "east"})
	@Secured
	public void goEast(Message message) {
		doMovement(message, Direction.EAST);
	}
	
	@Command({"S", "s", "south"})
	@Secured
	public void goSouth(Message message) {
		doMovement(message, Direction.SOUTH);
	}
	
	@Command({"W", "w", "west"})
	@Secured
	public void goWest(Message message) {
		doMovement(message, Direction.WEST);
	}
	
	@Command({"U", "u", "up"})
	@Secured
	public void goUp(Message message) {
		doMovement(message, Direction.UP);
	}
	
	@Command({"D", "d", "down"})
	@Secured
	public void goDown(Message message) {
		doMovement(message, Direction.DOWN);
	}
	
	@Command({"stuck", "limbo"})
	@Secured
	public void stuck(Message message) {
		
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
		
		Room spawnRoom = world.getRoomAt("limbo");
		world.setPlayerRoom(account, spawnRoom);
		message.respond("A bright flash of light blinds you.");
	}
	
	protected void doMovement(Message message, Direction direction) {
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
		if (room == null) {
			message.respond("You appear to be in the middle of nowhere...");
			return;
		}
		
		Room nextRoom = world.getRoomAt(room, direction);
		if (nextRoom == null) {
			message.respond("You can't go that way");
			return;
		}
		
		world.setPlayerRoom(account, nextRoom);
		message.respond("You move into "+nextRoom.getName());
	}

}
