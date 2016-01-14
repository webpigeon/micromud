package uk.me.webpigeon.phd.mud.engine.impl;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

import uk.me.webpigeon.phd.mud.engine.Command;
import uk.me.webpigeon.phd.mud.engine.CommandRegistry;
import uk.me.webpigeon.phd.mud.engine.MudService;
import uk.me.webpigeon.phd.mud.engine.NetServer;
import uk.me.webpigeon.phd.mud.engine.SessionManager;
import uk.me.webpigeon.phd.mud.engine.protobuf.ProtobufServer;

public class BasicServerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MudService.class).to(ExecutorMudService.class);
		bind(CommandRegistry.class).to(SimpleCommandRegistry.class);
		bind(NetServer.class).annotatedWith(Names.named("client")).to(TelnetDebugServer.class);
		bind(NetServer.class).annotatedWith(Names.named("npc")).to(ProtobufServer.class);
		bind(SessionManager.class).to(SimpleSessionManager.class);
	}

}
