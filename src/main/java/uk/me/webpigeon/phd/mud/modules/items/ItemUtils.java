package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;
import java.util.Iterator;

public class ItemUtils {

	public static String printItems(Collection<Item> items) {
		StringBuffer buff = new StringBuffer();
		
		Iterator<Item> itemItr = items.iterator();
		while(itemItr.hasNext()) {
			Item item = itemItr.next();
			buff.append(item.getShortName());
			if (itemItr.hasNext()) {
				buff.append(" ");
			}
		}
		
		return buff.toString();
	}
	
}
