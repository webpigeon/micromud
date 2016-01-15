package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;

/**
 * Deal with items in the game world.
 * 
 */
public class DemoItemModel {
	
	public Collection<Item> getItemsInRoom(String room){ return null; }
	public void putItemInroom(String room, Item item){}
	
	public Collection<Item> getItemsInInventory(String player){ return null; }
	public void putItemInInventory(String player, Item item){}
	
	public void destoryItem(Item item){}

}
