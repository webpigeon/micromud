package uk.me.webpigeon.phd.mud.dataModel.orm;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import uk.me.webpigeon.phd.mud.dataModel.DataController;
import uk.me.webpigeon.phd.mud.modules.accounts.Account;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.items.Inventory;
import uk.me.webpigeon.phd.mud.modules.items.InventoryModel;
import uk.me.webpigeon.phd.mud.modules.world.Room;
import uk.me.webpigeon.phd.mud.modules.world.RoomLink;
import uk.me.webpigeon.phd.mud.modules.world.WorldModel;

public class OrmController implements DataController {

	private ConnectionSource conn;

	public OrmController(String databaseUrl) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		this.conn = new JdbcConnectionSource(databaseUrl, "mud", "password");
	}

	@Override
	public AccountModel getAccountModel() throws Exception {
		Dao<Account,String> dao = DaoManager.createDao(conn, Account.class);
		dao.setObjectCache(true);
		
		return new OrmAccountModel(dao);
	}

	@Override
	public InventoryModel getInventoryModel() throws Exception {
		Dao<Inventory,String> dao = DaoManager.createDao(conn, Inventory.class);
		dao.setObjectCache(true);
		
		return new OrmInventoryModel(dao);
	}

	@Override
	public WorldModel getWorldModel() throws Exception {
		Dao<Room,String> dao = DaoManager.createDao(conn, Room.class);
		dao.setObjectCache(true);
		
		Dao<RoomLink,String> links = DaoManager.createDao(conn, RoomLink.class);
		links.setObjectCache(true);
		
		return new OrmWorldModel(dao, links);
	}
	
	@Override
	public void init() throws Exception {
		TableUtils.createTable(conn, Account.class);
		TableUtils.createTable(conn, Inventory.class);

	}

}
