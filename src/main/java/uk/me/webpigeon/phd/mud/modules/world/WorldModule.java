package uk.me.webpigeon.phd.mud.modules.world;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.modules.world.impl.ExitsCommand;
import uk.me.webpigeon.phd.mud.modules.world.impl.GraphWorldService;
import uk.me.webpigeon.phd.mud.modules.world.impl.LookCommand;
import uk.me.webpigeon.phd.mud.modules.world.impl.MoveCommand;

public class WorldModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(WorldService.class).to(GraphWorldService.class);
		
		MapBinder<String,Command> commandBinder = MapBinder.newMapBinder(binder(), String.class, Command.class);
		commandBinder.addBinding("go").to(MoveCommand.class);
		commandBinder.addBinding("look").to(LookCommand.class);
		commandBinder.addBinding("exits").to(ExitsCommand.class);
	}

}
