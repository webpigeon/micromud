package uk.me.webpigeon.phd.mud.dataModel.orm;

import java.sql.SQLException;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import uk.me.webpigeon.phd.mud.dataModel.DataController;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.items.Inventory;
import uk.me.webpigeon.phd.mud.modules.items.InventoryModel;
import uk.me.webpigeon.phd.mud.modules.items.Item;

public class OrmController implements DataController {

	private ConnectionSource conn;

	public OrmController(String databaseUrl) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		this.conn = new JdbcConnectionSource(databaseUrl, "mud", "password");
	}

	@Override
	public AccountModel getAccountModel() throws Exception {
		return new OrmAccountModel(DaoManager.createDao(conn, Account.class));
	}

	@Override
	public InventoryModel getInventoryModel() throws Exception {
		return new OrmInventoryModel(DaoManager.createDao(conn, Inventory.class));
	}

	@Override
	public void init() throws Exception {
		TableUtils.createTable(conn, Account.class);
		TableUtils.createTable(conn, Item.class);

	}

}
