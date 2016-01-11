package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;
import java.util.List;

import uk.me.webpigeon.phd.mud.engine.MessageType;
import uk.me.webpigeon.phd.mud.engine.Percept;
import uk.me.webpigeon.phd.mud.modules.test.Avatar;

/**
 * Precept describing the player's inventory
 */
public class InventoryPercept extends Percept {
	public final Avatar avatar;
	public final Collection<Item> inventory;

	public InventoryPercept(Avatar avatar, Collection<Item> inventory) {
		super(MessageType.DEFAULT);
		this.avatar = avatar;
		this.inventory = inventory;
	}
	
	@Override
	public String toString() {
		return avatar.getName();
	}

}
