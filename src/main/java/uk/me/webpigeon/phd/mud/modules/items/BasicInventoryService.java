package uk.me.webpigeon.phd.mud.modules.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.me.webpigeon.phd.mud.modules.test.Avatar;

public class BasicInventoryService implements InventoryService  {
	private Map<Avatar, List<Item>> inventory;
	
	public BasicInventoryService() {
		this.inventory = new HashMap<>();
	}
	
	public void addItem(Avatar avatar, Item item) {
		List<Item> items = inventory.get(avatar);
		if (items == null) {
			items = new ArrayList<Item>();
			inventory.put(avatar, items);
		}
		
		items.add(item);
	}
	
	public Collection<Item> getItems(Avatar avatar) {
		List<Item> items = inventory.get(avatar);
		if (items == null) {
			return Collections.emptyList();
		}
		
		return inventory.get(avatar);
	}

}
