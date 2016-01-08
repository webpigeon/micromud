package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;

import uk.me.webpigeon.phd.mud.modules.test.Avatar;

public interface InventoryService {

	public void addItem(Avatar avatar, Item item);
	
	public Collection<Item> getItems(Avatar avatar);
	
}
