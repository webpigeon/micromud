package uk.me.webpigeon.phd.mud.modules.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AccountDBModel implements AccountModel {
	private PreparedStatement getAccount;
	private PreparedStatement createAccount;
	
	private PreparedStatement getProperties;
	private PreparedStatement setProperties;
	
	public AccountDBModel(Connection conn) throws SQLException {
		this.getAccount = conn.prepareStatement("SELECT username,password FROM accounts WHERE username=?");
		this.createAccount = conn.prepareStatement("INSERT INTO accounts VALUES (?,?)");
		
		this.getProperties = conn.prepareStatement("SELECT key,value from account_properties WHERE username=?");
	}
	
	@Override
	public Account getAccount(String username) {
		try {
			getAccount.clearParameters();
			getAccount.setString(1, username);
			
			ResultSet rs = getAccount.executeQuery();
			
			if(!rs.next()) {
				return null;
			}
			
			Account account = new Account(rs.getString(1));
			account.setPassword(rs.getString(2));
			rs.close();
			
			Map<String,String> properties = new HashMap<String,String>();
			getProperties.clearParameters();
			getProperties.setString(1, properties.get(username));
			
			ResultSet rsProp = getProperties.executeQuery();
			while(rs.next()) {
				properties.put(rs.getString(1), rs.getString(2));
			}
			rsProp.close();
			
			account.setProperties(properties);
			
			return account;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean createAccount(String username, String password) {
		try {
			createAccount.clearBatch();
			createAccount.setString(1, username);
			createAccount.setString(2, password);
			int result = createAccount.executeUpdate();
			return result == 1;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public void lock(String account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlock(String account) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void save(Account currPlayer) {
		// TODO Auto-generated method stub
		
	}
	

}
