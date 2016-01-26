package uk.me.webpigeon.phd.mud.dataModel.debug;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import uk.me.webpigeon.phd.mud.modules.MudObject;
import uk.me.webpigeon.phd.mud.modules.items.Item;
import uk.me.webpigeon.phd.mud.modules.items.ItemModel;

/**
 * Deal with items in the game world.
 * 
 */
public class BasicItemModel implements ItemModel {

	private Map<String, Collection<Item>> inventories;

	public BasicItemModel() {
		this.inventories = new HashMap<String, Collection<Item>>();
	}

	public Collection<Item> getInventory(String type, String id) {
		Collection<Item> itemSet = inventories.get(type + "-" + id);
		if (itemSet == null) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableCollection(itemSet);
	}

	public void putItem(String type, String id, Item item) {
		Collection<Item> itemSet = inventories.get(type + "-" + id);
		if (itemSet == null) {
			itemSet = new HashSet<Item>();
			inventories.put(type + "-" + id, itemSet);
		}
		itemSet.add(item);
	}

	public void takeItem(String type, String id, Item item) {
		Collection<Item> itemSet = inventories.get(type + "-" + id);
		if (itemSet == null) {
			return;
		}
		itemSet.remove(item);
	}

	@Override
	public void destoryItem(Item item) {

	}

	@Override
	public Collection<Item> getInventory(MudObject inventory) {
		String invID = getInventoryID(inventory);
		Collection<Item> itemSet = inventories.get(invID);
		if (itemSet == null) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableCollection(itemSet);
	}

	@Override
	public Item findInInventory(MudObject inventory, String keyword) {
		String invID = getInventoryID(inventory);
		Collection<Item> itemSet = inventories.get(invID);
		if (itemSet == null) {
			return null;
		}

		for (Item item : itemSet) {
			if (item.matches(keyword)) {
				return item;
			}
		}

		return null;
	}

	@Override
	public boolean takeFromInventory(MudObject inventory, Item item) {
		String invID = getInventoryID(inventory);
		Collection<Item> itemSet = inventories.get(invID);
		if (itemSet == null) {
			return false;
		}
		return itemSet.remove(item);
	}

	public String getInventoryID(MudObject object) {
		String id = String.format("%s-%s", object.getType(), object.getID());
		return id;
	}

	@Override
	public boolean putInInventory(MudObject inventory, Item item) {
		String invID = getInventoryID(inventory);
		Collection<Item> itemSet = inventories.get(invID);
		if (itemSet == null) {
			itemSet = new HashSet<Item>();
			inventories.put(invID, itemSet);
		}
		return itemSet.add(item);
	}

	@Override
	public boolean moveItemInventory(MudObject oldInv, MudObject newInv, Item item) {
		return takeFromInventory(oldInv, item) && putInInventory(newInv, item);
	}

}
