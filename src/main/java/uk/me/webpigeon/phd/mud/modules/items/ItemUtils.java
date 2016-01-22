package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;
import java.util.List;

public class ItemUtils {

	public static String printItems(Collection<Item> itemSet) {
		if (itemSet.isEmpty()) {
			return "nothing";
		}
		
		StringBuilder sb = new StringBuilder();
		
		boolean first = true;
		for (Item item : itemSet) {
			if (!item.hasFlag(Tags.HIDDEN)) {
				if (!first) {
					sb.append(", ");
				}
				sb.append(item);
				first = false;
			}
		}
		return sb.toString();
	}
	
	public static Item findTaggedItem(String tag, Collection<Item> itemSet) {
		for (Item item : itemSet) {
			if (item.matches(tag)){
				return item;
			}
		}
		
		return null;
	}
	
	public static Item findKeyItem(String keyword, Collection<Item> itemSet) {
		for (Item item : itemSet) {
			if (item.matches(keyword)){
				System.out.println(item+" matched "+keyword);
				return item;
			} else {
				System.out.println(item+" was not a "+keyword);
			}
		}
		
		return null;
	}
	
	public static Item findItem(String keyword, Collection<Item> ... itemSets) {
		
		for (Collection<Item> itemSet : itemSets) {
			Item item = findKeyItem(keyword, itemSet);
			if (item != null) {
				return item;
			}
		}
		
		return null;
	}
	
}
