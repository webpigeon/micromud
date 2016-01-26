package uk.me.webpigeon.phd.mud.dataModel;

import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.items.InventoryModel;

public interface DataController {

	public void init() throws Exception;
	public AccountModel getAccountModel() throws Exception;
	public InventoryModel getInventoryModel() throws Exception;
	
}
