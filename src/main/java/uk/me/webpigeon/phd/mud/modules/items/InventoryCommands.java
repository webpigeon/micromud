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
	
	@Command({"items", "xi"})
	public void onItems(Message message) {
		Session session = message.getSession();
		
		String currentRoom = session.getProp(Account.ROOM_PROP, null);
		if (currentRoom == null) {
			message.respond("You don't appear to be playing");
			return;
		}
		
		Collection<Item> roomItems = items.getInventory("room", currentRoom);
		message.respond("You see: "+ItemUtils.printItems(roomItems));
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
			message.respond("What do you want to look at?");
		}
		
		//generate item sets
		Collection<Item> roomItems = items.getInventory("room", currentRoom);
		Collection<Item> accountItems = items.getInventory("account", account);
		
		//find a matching item
		Item selectedItem = ItemUtils.findItem(keyword, roomItems, accountItems);
		if (selectedItem == null) {
			message.respond("You can't see that item");
			return;
		}
		
		message.respond(selectedItem.getDescription());
		if (selectedItem.hasFlag(Tags.CONTAINER)){
			message.respond(selectedItem+" contains "+ItemUtils.printItems(selectedItem.getChildren()));
		}
		
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
	
	@Command({"put"})
	@Secured
	public void onPut(Message message) {
		Session session = message.getSession();
		String account = session.getProp(Account.NAME_PROP, null);
		String currentRoom = session.getProp(Account.ROOM_PROP, null);
		
		if (account == null || currentRoom == null) {
			message.respond("You don't appear to be playing");
			return;
		}
		
		String itemToPickup = message.getArgument(2, null);
		String placeToPut = message.getArgument(3, "ground");
		if (itemToPickup == null || placeToPut == null) {
			message.respond("What do you want to pick up?");
			return;
		}
		
		Collection<Item> itemsInRoom = items.getInventory("room", currentRoom);
		Collection<Item> itemsInInventory = items.getInventory("account", account);
		
		Item selectedItem = ItemUtils.findKeyItem(itemToPickup, itemsInInventory);
		Item selectedContainer = ItemUtils.findItem(placeToPut, itemsInRoom, itemsInInventory);
		
		if (!selectedContainer.hasFlag(Tags.CONTAINER)) {
			message.respond("That is not a container...");
			return;
		}
		
		selectedContainer.addChild(selectedItem);
		items.takeItem("account", account, selectedItem);
	}
	
	@Command({"take"})
	@Secured
	public void onTake(Message message) {
		Session session = message.getSession();
		String account = session.getProp(Account.NAME_PROP, null);
		String currentRoom = session.getProp(Account.ROOM_PROP, null);
		
		if (account == null || currentRoom == null) {
			message.respond("You don't appear to be playing");
			return;
		}
		
		String itemToPickup = message.getArgument(2, null);
		String placeToPut = message.getArgument(3, "ground");
		if (itemToPickup == null || placeToPut == null) {
			message.respond("What do you want to pick up?");
			return;
		}
		
		Collection<Item> itemsInRoom = items.getInventory("room", currentRoom);
		Collection<Item> itemsInInventory = items.getInventory("account", account);
		
		Item selectedContainer = ItemUtils.findItem(placeToPut, itemsInRoom, itemsInInventory);
		if (!selectedContainer.hasFlag(Tags.CONTAINER)) {
			message.respond("That is not a container...");
			return;
		}
		
		Item selectedItem = ItemUtils.findKeyItem(itemToPickup, selectedContainer.getChildren());
		selectedContainer.removeChild(selectedItem);
		items.putItem("account", account, selectedItem);
	}
	
	@Command({"pickup", "take"})
	@Secured
	public void onPickup(Message message) {
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
		
		Item selectedItem = ItemUtils.findKeyItem(itemToPickup, itemsInRoom);
		if (selectedItem == null) {
			message.respond("I didn't understand that item");
			return;
		}
		
		if (selectedItem.hasFlag(Tags.ANCHORED)) {
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
		
		Item selectedItem = ItemUtils.findKeyItem(itemToDrop, playerItems);
		if (selectedItem == null) {
			message.respond("I didn't understand that item");
			return;
		}
		
		items.takeItem("account", account, selectedItem);
		items.putItem("room", currentRoom, selectedItem);
	}
	
}
