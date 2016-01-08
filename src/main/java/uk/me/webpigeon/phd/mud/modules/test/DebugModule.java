package uk.me.webpigeon.phd.mud.modules.test;

import com.google.inject.AbstractModule;

public class DebugModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AvatarService.class).to(DebugAvatarService.class);	
	}

}
