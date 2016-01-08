package uk.me.webpigeon.phd.mud.modules.test;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

import uk.me.webpigeon.phd.mud.engine.Command;

public class DebugModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AvatarService.class).to(DebugAvatarService.class);	
		
		MapBinder<String,Command> commandBinder = MapBinder.newMapBinder(binder(), String.class, Command.class);
		commandBinder.addBinding("debug").to(DebugSpawn.class);
		commandBinder.addBinding("test").to(TestCommand.class);
	}

}
