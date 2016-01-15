package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Deal with items in the game world.
 * 
 */
public class DemoItemModel implements ItemModel {

	private Map<String, Collection<Item>> inventories;

	public DemoItemModel() {
		this.inventories = new HashMap<String, Collection<Item>>();
	}

	@Override
	public Collection<Item> getInventory(String type, String id) {
		Collection<Item> itemSet = inventories.get(type+"-"+id);
		if (itemSet == null) {
			return Collections.emptyList();
		}
		
		return Collections.unmodifiableCollection(itemSet);
	}

	@Override
	public void putItem(String type, String id, Item item) {
		Collection<Item> itemSet = inventories.get(type+"-"+id);
		if (itemSet == null) {
			itemSet = new HashSet<Item>();
			inventories.put(type+"-"+id, itemSet);
		}
		itemSet.add(item);
	}

	@Override
	public void takeItem(String type, String id, Item item) {
		Collection<Item> itemSet = inventories.get(type+"-"+id);
		if (itemSet == null) {
			return;
		}
		itemSet.remove(item);
	}

	@Override
	public void destoryItem(Item item) {
		
	}

	
}
