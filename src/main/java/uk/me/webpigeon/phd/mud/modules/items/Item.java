package uk.me.webpigeon.phd.mud.modules.items;

import java.util.HashSet;
import java.util.Set;

public class Item {
	
	public final String name;
	public String description;
	public double weight; //weight in grams
	public Set<String> tags;
	
	public Item(String name) {
		this.name = name;
		this.tags = new HashSet<String>();
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}
	
	public void removeTag(String tag) {
		tags.remove(tag);
	}
	
	public boolean hasTag(String tag) {
		return tags.contains(tag);
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public boolean matches(String checkName) {
		return name.equalsIgnoreCase(checkName);
	}

	public String toString() {
		return name;
	}
	
	public String getDescription(){
		if (description != null) {
			return String.format("%s: %s", name, description);
		} else {
			return String.format("You see an ordinary %s",name);	
		}
	}
	
}
