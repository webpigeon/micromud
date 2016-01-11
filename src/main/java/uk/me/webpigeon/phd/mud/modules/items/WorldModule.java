package uk.me.webpigeon.phd.mud.modules.items;

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
		bind(InventoryService.class).to(BasicInventoryService.class);
		
		MapBinder<String,Command> commandBinder = MapBinder.newMapBinder(binder(), String.class, Command.class);
		commandBinder.addBinding("inventory").to(ObjectsCommand.class);
		//commandBinder.addBinding("wear").to(MoveCommand.class);
		
		//commandBinder.addBinding("get").to(MoveCommand.class);
		//commandBinder.addBinding("drop").to(MoveCommand.class);
		
		//wear, get, inventory (i)
	}

}
