package uk.me.webpigeon.phd.mud;

import uk.me.webpigeon.phd.mud.modules.items.BasicItemModel;
import uk.me.webpigeon.phd.mud.modules.items.Item;
import uk.me.webpigeon.phd.mud.modules.items.ItemModel;
import uk.me.webpigeon.phd.mud.modules.items.Tags;
import uk.me.webpigeon.phd.mud.modules.world.BasicGraphWorld;
import uk.me.webpigeon.phd.mud.modules.world.Direction;
import uk.me.webpigeon.phd.mud.modules.world.Room;
import uk.me.webpigeon.phd.mud.modules.world.WorldModel;

public class DebugUtils {
	
	public static WorldModel buildWorld() {
		WorldModel world = new BasicGraphWorld();
		
		Room mainStreet = new Room("Market street");
		mainStreet.setDescription("The small cobbled street is lined with shops constructed with stone bricks on all sides.");
		world.createRoom(mainStreet);
		
		//bakery
		Room bakery = new Room("bakery");
		bakery.setDescription("A room with a wooden floor containing an array of shleves lined with cakes.");
		world.createRoom(bakery);
		world.link(mainStreet, bakery, Direction.WEST);
		world.link(bakery, mainStreet, Direction.EAST);
		
		//blacksmith
		Room blacksmith = new Room("blacksmith");
		blacksmith.setDescription("A hot, smoke filled room with a roaring furance placed promently in its center. To the left of the furnace you see a large metal anvil.");
		world.createRoom(blacksmith);
		world.link(mainStreet, blacksmith, Direction.EAST);
		world.link(blacksmith, mainStreet, Direction.WEST);
		
		//tavern
		Room tavern = new Room("tavern");
		tavern.setDescription("A small drinking establishment.");
		world.createRoom(tavern);
		world.link(mainStreet, tavern, Direction.SOUTH);
		world.link(tavern, mainStreet, Direction.NORTH);
		
		//additional places:
		//-> carpenter
		//-> keep
		//-> butcher
		
		//debug place
		Room limbo = new Room("limbo");
		limbo.setDescription("You appear to be in a large empty space filled with fog. You're not sure how you got here but you feel like you should go North.");
		world.createRoom(limbo);
		world.link(limbo, mainStreet, Direction.NORTH);
		
		return world;
	}

	public static ItemModel buildInventory() {		
		ItemModel model = new BasicItemModel();
		
		Item shelf = new Item("shelf", "cupboard");
		shelf.setDescription("The sturdy oak coloured wooden shelf used to display items for sale. It shows some signs of wear from repeated usage.");
		shelf.setFlag(Tags.ANCHORED);
		shelf.setFlag(Tags.CONTAINER);
		shelf.setWeight(45_000);
		
		//put some cakes into the shelf
		shelf.addChild(new Item("cake"));
		shelf.addChild(new Item("cake"));
		shelf.addChild(new Item("cake"));
		shelf.addChild(new Item("cake"));
		
		Item furnace = new Item("furnace");
		furnace.setDescription("A roaring furnace.");
		furnace.setFlag(Tags.ANCHORED);
		furnace.setWeight(450_000);
		
		Item anvil = new Item("anvil");
		anvil.setDescription("A well used anvil used for shaping metal.");
		anvil.setFlag(Tags.ANCHORED);
		anvil.setFlag(Tags.HIDDEN);
		anvil.setWeight(450_000);
		
		Item bag = new Item("bag");
		bag.setDescription("A small woven bag");
		bag.setWeight(10);
		bag.setFlag(Tags.CONTAINER);
		
		//put some dummy items into the world
		model.putItem("room", "bakery", shelf);
		model.putItem("room", "bakery", new Item("bread"));
		model.putItem("room", "bakery", new Item("cake"));
		
		model.putItem("room", "blacksmith", furnace);
		model.putItem("room", "blacksmith", anvil);
		model.putItem("room", "tavern", bag);
		
		return model;
	}

}
