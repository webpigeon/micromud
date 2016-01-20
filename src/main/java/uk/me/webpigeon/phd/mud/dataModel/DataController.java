package uk.me.webpigeon.phd.mud.dataModel;

import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;

public interface DataController {

	public AccountModel getAccountModel() throws Exception;
	
}
