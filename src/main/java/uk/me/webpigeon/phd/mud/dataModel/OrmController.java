package uk.me.webpigeon.phd.mud.dataModel;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;

public class OrmController implements DataController {

	private ConnectionSource conn;
	
	public OrmController(String databaseUrl) throws SQLException {
		this.conn = new JdbcConnectionSource(databaseUrl);
	}
	
	@Override
	public AccountModel getAccountModel() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
