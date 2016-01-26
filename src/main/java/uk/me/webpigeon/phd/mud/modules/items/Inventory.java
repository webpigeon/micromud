package uk.me.webpigeon.phd.mud.modules.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Inventory {

	@DatabaseField(id = true)
	private final String id;

	@ForeignCollectionField
	private final ForeignCollection<Item> items;

	public Inventory() {
		this(null);
	}

	public Inventory(String id) {
		this.id = id;
		this.items = null;
	}

	public Item findItem(String keyword) {
		for (Item item : items) {
			if (item.matches(keyword)) {
				return item;
			}
		}
		return null;
	}

	public Collection<Item> getItems(){
		return Collections.unmodifiableCollection(items);
	}
	
	@Override
	public String toString() {
		return items.toString();
	}

}
