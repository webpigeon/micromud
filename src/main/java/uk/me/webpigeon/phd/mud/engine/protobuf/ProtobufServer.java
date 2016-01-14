package uk.me.webpigeon.phd.mud.engine.protobuf;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.inject.Inject;

import uk.me.webpigeon.phd.mud.engine.CommandRegistry;
import uk.me.webpigeon.phd.mud.engine.MudService;
import uk.me.webpigeon.phd.mud.engine.NetServer;
import uk.me.webpigeon.phd.mud.engine.SessionManager;
import uk.me.webpigeon.phd.mud.engine.impl.CommandParser;
import uk.me.webpigeon.phd.mud.engine.impl.TextClientSession;

public class ProtobufServer implements NetServer, Runnable {
	private static final Integer DEFAULT_SERVER_PORT = 8080;
	private Thread thread;
	
	private ExecutorService clients;
	private CommandRegistry registry;
	private MudService service;
	private SessionManager sessions;
	
	@Inject
	public ProtobufServer(MudService service, CommandRegistry registry, SessionManager sessions) {
		this.clients = Executors.newCachedThreadPool();
		this.service = service;
		this.sessions = sessions;
		this.registry = registry;
	}
	
	@Override
	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		String portString = System.getenv("NPC_SERVER_PORT");
		int port = portString==null?DEFAULT_SERVER_PORT:Integer.parseInt(portString);
		
		try ( ServerSocket serverSocket = new ServerSocket(port); ) {
			
			while(!Thread.interrupted()) {
				Socket clientSocket = serverSocket.accept();
				
				//build and register the client
				CommandParser parser = new CommandParser(registry, service);
				ProtobufSession client = new ProtobufSession(clientSocket, parser);
				
				sessions.register(client);
				clients.execute(client);
			}
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}

	@Override
	public void debugJoin() {
		// TODO Auto-generated method stub
		
	}

}
