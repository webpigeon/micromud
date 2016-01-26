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
import uk.me.webpigeon.phd.mud.netty.ChannelService;

/**
 * Commands for inventory management and access.
 */
public class InventoryCommands extends AnnotationModule {
	
	private InventoryModel inventories;
	private AccountModel accounts;
	private ChannelService channels;
	
	public InventoryCommands(InventoryModel inventories, ChannelService channels, AccountModel accounts) {
		super("inv");
		this.inventories = inventories;
		this.accounts = accounts;
		this.channels = channels;
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
		
		Inventory roomItems = inventories.getInventory(room);
		message.respond("on the floor, you see: "+roomItems);
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
		
		//find a matching item
		Item selectedItem = inventories.findItem(keyword, room, account);
		if (selectedItem == null) {
			message.respond("You can't see that item");
			return;
		}
		
		message.respond(selectedItem.getDescription());
		if (selectedItem.hasFlag(Tags.CONTAINER)){
			Inventory containerInventory = inventories.getInventory(selectedItem);
			message.respond(selectedItem+" contains "+containerInventory);
		}
		
	}
	
	@Command({"inventory", "i"})
	@Secured
	public void onInventory(Message message) {
		Account account = getAccount(message.getSession());
		
		Inventory inv = inventories.getInventory(account);
		message.respond("You are holding: "+inv);
	}
	
	@Command({"put"})
	@Secured
	@HelpText("put an item from your inventory into a container")
	public void onPut(Message message) {
		Account account = getAccount(message.getSession());
		Room room = account.getLocation();
		
		//if they said put cheese ground they meant drop cheese.
		String placeToPut = message.getArgument(3, "ground");
		if ("ground".equals(placeToPut)) {
			onDrop(message);
			return;
		}
		
		
		String itemToPut = message.getArgument(2, null);
		if (itemToPut == null) {
			message.respond("What do you want to pick up?");
			return;
		}
		
		Inventory myItems = inventories.getInventory(account);
		Item selectedItem = myItems.findItem(itemToPut);
		if (selectedItem == null) {
			message.respond("You arn't carrying a "+itemToPut);
			return;
		}
		
		//the container we want to put stuff in might be in the room or our inventory
		Item selectedContainer = inventories.findItem(placeToPut, room, account);
		//check that the thing that you want to put the item in is a container
		if (selectedContainer == null || !selectedContainer.hasFlag(Tags.CONTAINER)) {
			message.respond("That is not a container...");
			return;
		}
		
		//putting things in themselves is silly
		if (selectedContainer.equals(selectedItem)) {
			message.respond("That's just silly...");
			return;
		}
		
		inventories.transfer(selectedItem, account, selectedContainer);
		
		String messageToUser = String.format("You place the %s into %s", selectedItem, selectedContainer);
		String messageFormat = String.format("%s places %s in %s", account, selectedItem, selectedContainer);
		channels.sendToGroup("room-"+room.getID(), messageFormat);
		message.respond(messageToUser);
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
		Item selectedContainer = inventories.findItem(itemToFind, account, room);
		if (selectedContainer == null || !selectedContainer.hasFlag(Tags.CONTAINER) ) {
			message.respond("That doesn't appear to be a container...");
			return;
		}
		
		Item selectedItem = inventories.findItem(itemToFind, selectedContainer);
		if (selectedItem == null) {
			message.respond("I couldn't find that item in the container");
			return;
		}
		
		inventories.transfer(selectedItem, selectedContainer, account);
		
		String messageFormat = String.format("%s has taken %s from %s", account, selectedItem, selectedContainer);
		channels.sendToGroup("room-"+room.getID(), messageFormat);
		message.respond(String.format("you take the %s from %s", selectedItem, selectedContainer));
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
		
		Item selectedItem = inventories.findItem(itemToPickup, room);
		if (selectedItem == null) {
			message.respond("I didn't understand that item");
			return;
		}
		
		if (selectedItem.hasFlag(Tags.ANCHORED)) {
			message.respond("You cannot pick that up");
			return;
		}
		
		inventories.transfer(selectedItem, room, account);
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
			
		Inventory playerItems = inventories.getInventory(account);
		Item selectedItem = playerItems.findItem(itemToDrop);
		if (selectedItem == null) {
			message.respond("I didn't understand that item");
			return;
		}
		
		inventories.transfer(selectedItem, account, account.getLocation());
	}
	
}
