package uk.me.webpigeon.phd.mud.engine.impl;

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

public class TelnetDebugServer implements NetServer, Runnable {
	private static final Integer DEFAULT_SERVER_PORT = 1337;
	private Thread thead;
	
	private ExecutorService clients;
	private CommandRegistry registry;
	private MudService service;
	private SessionManager sessions;
	
	@Inject
	public TelnetDebugServer(CommandRegistry registry, MudService service, SessionManager sessions) {
		this.registry = registry;
		this.service = service;
		this.sessions = sessions;
	}
	
	@Override
	public void start() {
		this.thead = new Thread(this);
		this.clients = Executors.newCachedThreadPool();
		thead.start();
	}

	@Override
	public void run() {
		String portString = System.getenv("SERVER_PORT");
		int port = portString==null?DEFAULT_SERVER_PORT:Integer.parseInt(portString);
		
		try ( ServerSocket serverSocket = new ServerSocket(port); ){
			
			while(!Thread.interrupted()) {
				Socket clientSocket = serverSocket.accept();
				
				//build and register the client
				CommandParser parser = new CommandParser(registry, service);
				TextClientSession client = new TextClientSession(clientSocket, parser);
				
				sessions.register(client);
				clients.execute(client);
			}
			
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}
	
	public void debugJoin() {
		try{
			thead.join();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
