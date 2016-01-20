package uk.me.webpigeon.phd.mud.dataModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import uk.me.webpigeon.phd.mud.modules.accounts.AccountDBModel;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;

public class PostgresController implements DataController {
	private static final String CONNECTION_STRING = "jdbc:postgresql://%s/%s";
	
	private Connection connection;
	
	public PostgresController(String host, String db, String username, String password) throws SQLException {
		String connStr = String.format(CONNECTION_STRING, host, db);
		Connection conn = DriverManager.getConnection(connStr, username, password);
		connection = conn;
	}
	
	public AccountModel getAccountModel() throws SQLException {
		return new AccountDBModel(connection);
	}
	
}
