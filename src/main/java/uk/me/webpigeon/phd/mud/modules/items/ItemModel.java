package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;

import uk.me.webpigeon.phd.mud.modules.MudObject;

public interface ItemModel {

	public Collection<Item> getInventory(MudObject inventory);

	public Item findInInventory(MudObject inventory, String keyword);

	public boolean putInInventory(MudObject inventory, Item item);

	public boolean takeFromInventory(MudObject inventory, Item item);

	boolean moveItemInventory(MudObject oldInv, MudObject newInv, Item item);

	void destoryItem(Item item);
}