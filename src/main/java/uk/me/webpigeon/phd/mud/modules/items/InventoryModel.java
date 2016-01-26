package uk.me.webpigeon.phd.mud.modules.items;

import uk.me.webpigeon.phd.mud.modules.MudObject;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;
import uk.me.webpigeon.phd.mud.modules.world.Room;

public interface InventoryModel {
	
	Inventory getInventory(MudObject object);

	void save(Inventory myItems);

	Item findItem(String keyword, MudObject ... objects);

	void transfer(Item item, MudObject from, MudObject to);
	void transfer(Item item, Inventory from, Inventory to);

}
