package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;

import uk.co.unitycoders.pircbotx.commandprocessor.Command;
import uk.co.unitycoders.pircbotx.commandprocessor.HelpText;
import uk.co.unitycoders.pircbotx.commandprocessor.Message;
import uk.co.unitycoders.pircbotx.modules.AnnotationModule;
import uk.co.unitycoders.pircbotx.security.Secured;
import uk.co.unitycoders.pircbotx.security.Session;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.world.Room;

/**
 * Commands for inventory management and access.
 */
public class InventoryCommands extends AnnotationModule {
	
	private ItemModel items;
	private AccountModel accounts;

	public InventoryCommands(ItemModel items, AccountModel accounts) {
		super("items");
		this.items = items;
		this.accounts = accounts;
	}

	private Account getAccount(Session session) {
		String accountName = session.getProp(Account.NAME_PROP, null);
		if (accountName == null) {
			throw new RuntimeException("You are not playing");
		}
		
		Account account = accounts.getAccount(accountName);
		if (account == null) {
			throw new RuntimeException("You are not playing");
		}
		
		return account;
	}
	
	@Command({"items", "xi"})
	public void onItems(Message message) {
		Account account = getAccount(message.getSession());
		Room room = account.getLocation();
		
		Collection<Item> roomItems = items.getInventory(room);
		message.respond("on the floor, you see: "+ItemUtils.printItems(roomItems));
	}
	
	@Command({"examine", "x"})
	@Secured
	public void onExamine(Message message) {
		Account account = getAccount(message.getSession());
		Room room = account.getLocation();
		
		//check if an item was provided
		String keyword = message.getArgument(2, null);
		if (keyword == null) {
			onItems(message);
			return;
		}
		
		//generate item sets
		Collection<Item> roomItems = items.getInventory(room);
		Collection<Item> accountItems = items.getInventory(account);
		
		//find a matching item
		Item selectedItem = ItemUtils.findItem(keyword, roomItems, accountItems);
		if (selectedItem == null) {
			message.respond("You can't see that item");
			return;
		}
		
		message.respond(selectedItem.getDescription());
		if (selectedItem.hasFlag(Tags.CONTAINER)){
			Collection<Item> containerInventory = items.getInventory(selectedItem);
			message.respond(selectedItem+" contains "+ItemUtils.printItems(containerInventory));
		}
		
	}
	
	@Command({"inventory", "i"})
	@Secured
	public void onInventory(Message message) {
		Account account = getAccount(message.getSession());
		
		Collection<Item> playerItems = items.getInventory(account);
		message.respond("You are holding: "+playerItems);
	}
	
	@Command({"put"})
	@Secured
	@HelpText("put an item from your inventory into a container")
	public void onPut(Message message) {
		Account account = getAccount(message.getSession());
		Room room = account.getLocation();
		
		String itemToPut = message.getArgument(2, null);
		String placeToPut = message.getArgument(3, "ground");
		if (itemToPut == null || placeToPut == null) {
			message.respond("What do you want to pick up?");
			return;
		}
		
		//if they said put cheese ground they meant drop cheese.
		if ("ground".equals(placeToPut)) {
			onDrop(message);
			return;
		}
		
		Collection<Item> myItems = items.getInventory(account);
		Item selectedItem = ItemUtils.findKeyItem(itemToPut, myItems);
		if (selectedItem == null) {
			message.respond("You arn't carrying a "+itemToPut);
			return;
		}
		
		//the container we want to put stuff in might be in the room or our inventory
		Collection<Item> itemsOnFloor = items.getInventory(room);
		Item selectedContainer = ItemUtils.findItem(placeToPut, itemsOnFloor, myItems);
		
		//check that the thing that you want to put the item in is a container
		if (selectedContainer != null && !selectedContainer.hasFlag(Tags.CONTAINER)) {
			message.respond("That is not a container...");
			return;
		}
		
		//putting things in themselves is silly
		if (selectedContainer.equals(selectedItem)) {
			message.respond("That's just silly...");
			return;
		}
		
		items.moveItemInventory(account, selectedContainer, selectedItem);
		
		//let everyone know what happened
		message.respond(String.format("you place the %s into %s", selectedItem, selectedContainer));
		message.broadcast(String.format("%s has put %s in %s", account.getUsername(), selectedItem, selectedContainer));
	}
	
	@Command({"take"})
	@Secured
	@HelpText("put an item from a container into your inventory")
	public void onTake(Message message) {
		Account account = getAccount(message.getSession());
		Room room = account.getLocation();
		
		String itemToFind = message.getArgument(2, null);
		String containerToSearch = message.getArgument(3, "ground");
		if (itemToFind == null) {
			message.respond("What do you want to take?");
			return;
		}
		
		//if they want to pick it up from the ground they meant pickup
		if ("ground".equals(containerToSearch)){
			onPickup(message);
			return;
		}
		
		//we need to find the container the user meant
		Collection<Item> myItems = items.getInventory(account);
		Collection<Item> itemsOnFloor = items.getInventory(room);
		Item selectedContainer = ItemUtils.findItem(containerToSearch, itemsOnFloor, myItems);
		
		//check the container we found was actually a container
		if (selectedContainer != null && !selectedContainer.hasFlag(Tags.CONTAINER)) {
			message.respond("That is not a container...");
			return;
		}
		
		//now we need to find the item in the container
		Collection<Item> containerInventory = items.getInventory(selectedContainer);
		System.out.println("checking inventory: "+containerInventory);
		Item selectedItem = ItemUtils.findKeyItem(itemToFind, containerInventory);
		if (selectedItem == null) {
			message.respond("I couldn't find a "+itemToFind+" on "+selectedContainer);
			return;
		}
		
		items.moveItemInventory(selectedContainer, account, selectedItem);
		
		message.respond(String.format("you take the %s from %s", selectedItem, selectedContainer));
		message.broadcast(String.format("%s has taken %s from %s", account.getUsername(), selectedItem, selectedContainer));
	}
	
	@Command({"pickup"})
	@Secured
	public void onPickup(Message message) {
		Account account = getAccount(message.getSession());
		Room room = account.getLocation();
		
		String itemToPickup = message.getArgument(2, null);
		if (itemToPickup == null) {
			message.respond("What do you want to pick up?");
			return;
		}
		
		Collection<Item> itemsInRoom = items.getInventory(room);
		
		Item selectedItem = ItemUtils.findKeyItem(itemToPickup, itemsInRoom);
		if (selectedItem == null) {
			message.respond("I didn't understand that item");
			return;
		}
		
		if (selectedItem.hasFlag(Tags.ANCHORED)) {
			message.respond("You cannot pick that up");
			return;
		}
		
		items.moveItemInventory(room, account, selectedItem);
	}
	
	@Command({"drop"})
	@Secured
	public void onDrop(Message message) {
		Account account = getAccount(message.getSession());
		
		String itemToDrop = message.getArgument(2, null);
		if (itemToDrop == null) {
			message.respond("What do you want to pick up?");
			return;
		}
			
		Collection<Item> playerItems = items.getInventory(account);
		Item selectedItem = ItemUtils.findKeyItem(itemToDrop, playerItems);
		if (selectedItem == null) {
			message.respond("I didn't understand that item");
			return;
		}
		
		items.moveItemInventory(account, account.getLocation(), selectedItem);
	}
	
}
