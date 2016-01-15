package uk.me.webpigeon.phd.mud;

import uk.me.webpigeon.phd.mud.world.BasicGraphWorld;
import uk.me.webpigeon.phd.mud.world.Direction;
import uk.me.webpigeon.phd.mud.world.Room;
import uk.me.webpigeon.phd.mud.world.WorldModel;

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

}
