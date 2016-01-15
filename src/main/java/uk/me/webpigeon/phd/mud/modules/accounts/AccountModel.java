package uk.me.webpigeon.phd.mud.modules.accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountModel {
	//TODO datebase this
	
	private Map<String, Account> accounts;
	
	public AccountModel(){
		this.accounts = new HashMap<String, Account>();
	}
	
	public Account getAccount(String username) {
		return accounts.get(username);
	}

	public Account createAccount(String username, String password) {
		if (accounts.containsKey(username)) {
			throw new RuntimeException("that username is taken");
		}
		
		Account account = new Account(username);
		account.setPassword(password);
		accounts.put(username, account);
		
		return account;
	}

}
