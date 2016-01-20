package uk.me.webpigeon.phd.mud.modules.accounts;

public interface AccountModel {

	Account getAccount(String username);

	boolean createAccount(String username, String password);

	void lock(String account);

	void unlock(String account);

	void save(Account currPlayer);

}