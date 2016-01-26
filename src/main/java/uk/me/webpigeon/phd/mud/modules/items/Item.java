package uk.me.webpigeon.phd.mud.modules.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import uk.me.webpigeon.phd.mud.modules.MudObject;

@DatabaseTable
public class Item extends MudObject {

	@DatabaseField(id=true)
	private final String id;

	@DatabaseField
	public final String name;

	@DatabaseField
	public String description;
	
	@DatabaseField
	public String shortDesc;
	
	@DatabaseField
	public double weight; // weight in grams
	
	@DatabaseField(foreign=true)
	public Inventory containedIn;
	
	public final String[] keywords;
	public Set<String> tags;

	private List<Item> contained;
	private Item parent;

	public Item() {
		this("db-error");
	}
	
	public Item(String name, String... keywords) {
		this.id = "item-" + System.currentTimeMillis();
		this.name = name;
		this.keywords = keywords;
		this.tags = new HashSet<String>();
		this.contained = new ArrayList<Item>();
	}

	@Override
	public String getID() {
		return id;
	}

	/*
	 * public void addChild(Item item) { assert tags.contains(Tags.CONTAINER) :
	 * "Tried put something in an non-container"; contained.add(item);
	 * 
	 * item.setContained(this); weight += item.weight; }
	 * 
	 * public void removeChild(Item item) { assert
	 * tags.contains(Tags.CONTAINER); assert this.equals(item.parent); assert
	 * item != null;
	 * 
	 * item.setContained(null); weight -= item.weight; }
	 * 
	 * private void setContained(Item item) { assert parent == null || item ==
	 * null; this.parent = item; }
	 */

	public void setDescription(String description) {
		this.description = description;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean matches(String checkName) {
		return name.equalsIgnoreCase(checkName);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getDescription() {
		if (description != null) {
			return String.format("%s: %s", name, description);
		} else {
			return String.format("You see an ordinary %s", name);
		}
	}

	/*
	 * public Collection<Item> getChildren() { //return
	 * Collections.unmodifiableCollection(contained); }
	 */

	@Override
	public String[] getKeywords() {
		return keywords;
	}

	@Override
	public String getType() {
		return "item";
	}

	@Override
	public String getShortName() {
		return name;
	}

}
