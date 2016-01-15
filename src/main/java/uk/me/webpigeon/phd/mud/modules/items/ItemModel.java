package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;

public interface ItemModel {

	public Collection<Item> getInventory(String type, String id);
	
	public void putItem(String type, String id, Item item);
	public void takeItem(String type, String id, Item item);
	
	void destoryItem(Item item);
}