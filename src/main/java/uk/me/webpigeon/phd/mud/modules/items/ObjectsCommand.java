package uk.me.webpigeon.phd.mud.modules.items;

import java.util.Collection;

import javax.jdo.PersistenceManager;

import com.google.inject.Inject;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.Context;
import uk.me.webpigeon.phd.mud.engine.Session;
import uk.me.webpigeon.phd.mud.modules.test.Avatar;

/**
 * List the contents of the player's inventory.
 */
public class ObjectsCommand implements Command {
	private InventoryService inventory;
	
	@Inject
	public ObjectsCommand(InventoryService inventory) {
		this.inventory = inventory;
	}

	@Override
	public void execute(Context context, PersistenceManager pm) {
		Session session = context.getSession();
		Avatar avatar = session.getAvatar();
		
		Collection<Item> items = inventory.getItems(avatar);
		session.addPercept(new InventoryPercept(avatar, items));
	}

}
