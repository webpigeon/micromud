package uk.me.webpigeon.phd.mud.dataModel.orm;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;

import uk.me.webpigeon.phd.mud.modules.MudObject;
import uk.me.webpigeon.phd.mud.modules.items.Inventory;
import uk.me.webpigeon.phd.mud.modules.items.InventoryModel;
import uk.me.webpigeon.phd.mud.modules.items.Item;

public class OrmInventoryModel implements InventoryModel {
	private Dao<Inventory, String> model;

	public OrmInventoryModel(Dao<Inventory, String> model) {
		this.model = model;
	}

	@Override
	public Inventory getInventory(MudObject source) {
		try {
			Inventory inventory = getDBInventory(source);
			if (inventory == null) {
				return new Inventory(source.getType() + "-" + source.getID());
			}

			return inventory;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private Inventory getDBInventory(MudObject source) throws SQLException {
		if (source == null) {
			System.err.println("Tried to add to null object: "+source);
			return null;
		}
		
		String pk_fmt = String.format("%s-%s", source.getType(), source.getID());
		return model.queryForId(pk_fmt);
	}

	@Override
	public void save(Inventory myItems) {
		try {
			model.createOrUpdate(myItems);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Item findItem(String keyword, MudObject... objects) {

		for (MudObject object : objects) {
			Inventory inventory = getInventory(object);
			Item found = inventory.findItem(keyword);
			if (found != null) {
				return found;
			}
		}

		return null;
	}

	@Override
	public void transfer(Item item, MudObject from, MudObject to) {
		assert item != null;
		assert from != null;
		assert to != null;
		try {
			Inventory fromInv = getDBInventory(from);
			Inventory toInv = getDBInventory(to);
			transfer(item, fromInv, toInv);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void transfer(Item item, Inventory from, Inventory to) {
		assert item != null;
		assert from != null;
		assert to != null;
		try {
			from.remove(item);
			to.remove(item);
			model.createOrUpdate(from);
			model.createOrUpdate(to);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void putInv(MudObject container, Item item) {
		Inventory i = getInventory(container);
		i.add(item);
		save(i);
	}

}
