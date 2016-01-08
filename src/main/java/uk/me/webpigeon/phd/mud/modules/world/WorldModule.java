package uk.me.webpigeon.phd.mud.modules.world;

import com.google.inject.AbstractModule;

import uk.me.webpigeon.phd.mud.modules.world.impl.GraphWorldService;

public class WorldModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(WorldService.class).to(GraphWorldService.class);
		
	}

}
