package uk.me.webpigeon.phd.mud.modules.items;

import java.util.List;

import uk.me.webpigeon.phd.mud.modules.test.Avatar;

public interface InventoryModel {

	public void addToInventory(Avatar avatar, Item item);
	
	public void removeFromInventory(Avatar avatar, Item item);
	
	public List<Item> getItems(Avatar avatar);
	
}
