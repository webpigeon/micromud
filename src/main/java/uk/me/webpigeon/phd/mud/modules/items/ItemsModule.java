package uk.me.webpigeon.phd.mud.modules.items;

import com.google.inject.AbstractModule;

public class ItemsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(InventoryService.class).to(BasicInventoryService.class);
		
	}

}
