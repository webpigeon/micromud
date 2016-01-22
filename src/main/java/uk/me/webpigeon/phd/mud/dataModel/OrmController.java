package uk.me.webpigeon.phd.mud.dataModel;

import java.sql.SQLException;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import uk.me.webpigeon.phd.mud.modules.accounts.Account;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.accounts.OrmAccountModel;

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

}
