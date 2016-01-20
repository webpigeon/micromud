package uk.me.webpigeon.phd.mud.modules.world;

import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.security.Secured;
import uk.co.unitycoders.pircbotx.security.Session;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
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
	private AccountModel accounts;

	public PlayerMovement(WorldModel world, AccountModel accounts) {
		super("go");
		this.world = world;
		this.accounts = accounts;
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
		String currentRoomID = session.getProp(Account.ROOM_PROP, null);
		if (account == null) {
			message.respond("You don't appear to be playing...");
			return;
		}
		
		Room currentRoom = world.getRoomAt(currentRoomID);
		Room spawnRoom = world.getRoomAt("limbo");
		Account currPlayer = accounts.getAccount(account);
		
		move(message, currPlayer, currentRoom, spawnRoom);
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

		Account currPlayer = accounts.getAccount(account);
		move(message, currPlayer, room, nextRoom);
	}
	
	private void move(Message message, Account account, Room oldRoom, Room newRoom) {
		world.setPlayerRoom(account.getUsername(), newRoom);
		account.setProperty(Account.ROOM_PROP, newRoom.getName());
		accounts.save(account);
		
		String format = "%s moves from %s to %s";
		message.respond("You move into "+newRoom.getName());
		message.broadcast(String.format(format, account, oldRoom.getName(), newRoom.getName()));
	}

}
