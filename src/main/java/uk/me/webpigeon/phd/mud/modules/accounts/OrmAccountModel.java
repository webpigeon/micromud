package uk.me.webpigeon.phd.mud.modules.accounts;

import com.j256.ormlite.dao.Dao;

public class OrmAccountModel implements AccountModel {

	private Dao<Account, String> accountDao;
	
	public OrmAccountModel(Dao<Account, String> dao) {
		this.accountDao = dao;
	}
	
	@Override
	public Account getAccount(String username) {
		return accountDao.queryForId(username);
	}

	@Override
	public boolean createAccount(String username, String password) {
		// TODO Auto-generated method stub
		return false;
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
