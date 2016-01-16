package uk.me.webpigeon.phd.mud.modules.accounts;

public interface AccountModel {

	Account getAccount(String username);

	Account createAccount(String username, String password);

}