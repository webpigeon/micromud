package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;
import java.util.List;

public class ItemUtils {

	public static String printItems(Collection<Item> itemSet) {
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
				return item;
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
