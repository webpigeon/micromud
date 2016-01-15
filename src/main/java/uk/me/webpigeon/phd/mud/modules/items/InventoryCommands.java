package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.security.Secured;
import uk.co.unitycoders.pircbotx.security.Session;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;

/**
 * Commands for inventory management and access.
 */
public class InventoryCommands extends AnnotationModule {
	private ItemModel items;

	public InventoryCommands(ItemModel items) {
		super("items");
		this.items = items;
	}
	
	@Command({"examine", "x"})
	@Secured
	public void onExamine(Message message) {
		Session session = message.getSession();
		String account = session.getProp(Account.NAME_PROP, null);
		String currentRoom = session.getProp(Account.ROOM_PROP, null);
		if (account == null || currentRoom == null) {
			message.respond("You don't appear to be playing");
			return;
		}
		
		//check if an item was provided
		String keyword = message.getArgument(2, null);
		if (keyword == null) {
			Collection<Item> roomItems = items.getInventory("room", currentRoom);
			message.respond("You see: "+printItems(roomItems));
			return;
		}
		
		//check if the item matches something in the room
		Collection<Item> roomItems = items.getInventory("room", currentRoom);
		Item selectedItem = getBestMatch(roomItems, keyword);
		if (selectedItem != null) {
			message.respond(selectedItem.getDescription());
			return;
		}
		
		//check if the item matches something in our inventory
		Collection<Item> accountItems = items.getInventory("account", account);
		selectedItem = getBestMatch(accountItems, keyword);
		if (selectedItem != null) {
			message.respond(selectedItem.getDescription());
			return;
		}
		
		message.respond("You can't see that item");
	}
	
	@Command({"inventory", "i"})
	@Secured
	public void onInventory(Message message) {
		Session session = message.getSession();
		String account = session.getProp(Account.NAME_PROP, null);
		if (account == null) {
			message.respond("You don't appear to be playing");
			return;
		}
		
		Collection<Item> playerItems = items.getInventory("account", account);
		message.respond("You are holding: "+playerItems);
	}
	
	@Command({"get", "pickup", "take"})
	@Secured
	public void onGet(Message message) {
		Session session = message.getSession();
		String account = session.getProp(Account.NAME_PROP, null);
		String currentRoom = session.getProp(Account.ROOM_PROP, null);
		
		if (account == null || currentRoom == null) {
			message.respond("You don't appear to be playing");
			return;
		}
		
		String itemToPickup = message.getArgument(2, null);
		if (itemToPickup == null) {
			message.respond("What do you want to pick up?");
			return;
		}
		
		Collection<Item> itemsInRoom = items.getInventory("room", currentRoom);
		
		Item selectedItem = getBestMatch(itemsInRoom, itemToPickup);
		if (selectedItem == null) {
			message.respond("I didn't understand that item");
			return;
		}
		
		if (selectedItem.hasTag(Tags.ANCHORED)) {
			message.respond("You cannot pick that up");
			return;
		}
		
		items.takeItem("room", currentRoom, selectedItem);
		items.putItem("account", account, selectedItem);
	}
	
	@Command({"drop"})
	@Secured
	public void onDrop(Message message) {
		Session session = message.getSession();
		String account = session.getProp(Account.NAME_PROP, null);
		String currentRoom = session.getProp(Account.ROOM_PROP, null);
		
		if (account == null || currentRoom == null) {
			message.respond("You don't appear to be playing");
			return;
		}
		
		String itemToDrop = message.getArgument(2, null);
		if (itemToDrop == null) {
			message.respond("What do you want to pick up?");
			return;
		}
			
		Collection<Item> playerItems = items.getInventory("account", account);
		
		Item selectedItem = getBestMatch(playerItems, itemToDrop);
		if (selectedItem == null) {
			message.respond("I didn't understand that item");
			return;
		}
		
		items.takeItem("account", account, selectedItem);
		items.putItem("room", currentRoom, selectedItem);
	}
	
	@Command({"equipment", "eq"})
	@Secured
	public void onEquipment(Message message) {
		Session session = message.getSession();
		String account = session.getProp(Account.NAME_PROP, null);
		if (account == null) {
			message.respond("You don't appear to be playing");
			return;
		}
		
		Collection<Item> playerItems = items.getInventory("account", account);
		message.respond("You are wearing: "+playerItems);
	}
	
	
	@Command({"wear"})
	@Secured
	public void onWear(Message message) {
		
	}
	
	@Command({"remove"})
	@Secured
	public void onRemove(Message message) {
		
	}
	
	@Command({"wield"})
	@Secured
	public void onWield(Message message) {
		
	}
	
	@Command({"hold"})
	@Secured
	public void onHold(Message message) {
		
	}

	protected String printItems(Collection<Item> itemSet) {
		StringBuilder sb = new StringBuilder();
		
		boolean first = true;
		for (Item item : itemSet) {
			if (!item.hasTag(Tags.HIDDEN)) {
				if (!first) {
					sb.append(", ");
				}
				sb.append(item);
				first = false;
			}
		}
		return sb.toString();
	}
	
	protected Item getBestMatch(Collection<Item> itemSet, String matchTerm) {
		for (Item item : itemSet) {
			if (item.matches(matchTerm)){
				return item;
			}
		}
		
		return null;
	}
	
}
