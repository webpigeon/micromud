package uk.me.webpigeon.phd.mud.modules.items;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class Inventory {

	@DatabaseField(id = true)
	private final String id;

	@ForeignCollectionField
	private final List<Item> items;

	public Inventory() {
		this(null);
	}

	public Inventory(String id) {
		this.id = id;
		this.items = new ArrayList<>();
	}

	public Item findItem(String keyword) {
		for (Item item : items) {
			if (item.matches(keyword)) {
				return item;
			}
		}
		return null;
	}

	public void add(Item selectedItem) {
		items.add(selectedItem);
	}

	public void remove(Item selectedItem) {
		items.remove(selectedItem);
	}

	@Override
	public String toString() {
		return items.toString();
	}

}
