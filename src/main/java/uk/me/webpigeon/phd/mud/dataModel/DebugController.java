package uk.me.webpigeon.phd.mud.dataModel;

import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.accounts.BasicAccountModel;
import uk.me.webpigeon.phd.mud.modules.items.InventoryModel;

public class DebugController implements DataController {

	@Override
	public AccountModel getAccountModel() throws Exception {
		return new BasicAccountModel();
	}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public InventoryModel getInventoryModel() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
