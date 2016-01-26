package uk.me.webpigeon.phd.mud.dataModel.orm;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;

import uk.me.webpigeon.phd.mud.modules.accounts.Account;
import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;

public class OrmAccountModel implements AccountModel {

	private Dao<Account, String> accountDao;

	public OrmAccountModel(Dao<Account, ?> dao) {
		this.accountDao = (Dao<Account, String>) dao;
	}

	@Override
	public Account getAccount(String username) {
		try {
			return accountDao.queryForId(username);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean createAccount(String username, String password) {
		try {
			Account account = new Account(username);
			account.setPassword(password);
			int shrug = accountDao.create(account);
			return true;
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
		try {
			accountDao.update(currPlayer);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
