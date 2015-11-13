package uk.me.webpigeon.phd.tinymud.net;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

import uk.me.webpigeon.phd.tinymud.GameRuntime;

public class MudSocketServer implements Callable {
	public static final Integer TCP_PORT = 1337;
	private GameRuntime runtime;
	
	public MudSocketServer(GameRuntime runtime) {
		this.runtime = runtime;
	}

	@Override
	public Object call() throws Exception {
		ServerSocket server = new ServerSocket(TCP_PORT);
		
		Socket socket = server.accept();
		NetAgent agent = new NetAgent(socket);
		new Thread(agent).start();
		
		runtime.addAgent(agent);
		
		// TODO Auto-generated method stub
		return null;
	}


}
