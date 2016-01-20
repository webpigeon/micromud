package uk.me.webpigeon.phd.mud.dataModel;

import uk.me.webpigeon.phd.mud.modules.accounts.AccountModel;
import uk.me.webpigeon.phd.mud.modules.accounts.BasicAccountModel;

public class DebugController implements DataController {

	@Override
	public AccountModel getAccountModel() throws Exception {
		return new BasicAccountModel();
	}

}
