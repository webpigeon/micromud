package uk.me.webpigeon.phd.mud.modules.world;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.datanucleus.store.connection.ConnectionFactory;

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
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/mud","postgres", "password42");
			bind(Connection.class).toInstance(conn);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		bind(WorldService.class).to(GraphWorldService.class);
		
		MapBinder<String,Command> commandBinder = MapBinder.newMapBinder(binder(), String.class, Command.class);
		commandBinder.addBinding("go").to(MoveCommand.class);
		commandBinder.addBinding("look").to(LookCommand.class);
		commandBinder.addBinding("exits").to(ExitsCommand.class);
	}

}
