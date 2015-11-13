package uk.me.webpigeon.phd.tinymud.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import uk.me.webpigeon.phd.tinymud.AgentController;
import uk.me.webpigeon.phd.tinymud.Move;
import uk.me.webpigeon.phd.tinymud.Precept;
import uk.me.webpigeon.phd.tinymud.data.GameState;

public class NetAgent implements AgentController, Runnable {
	private Socket socket;
	private Move nextMove;
	private Gson gson;
	
	PrintStream os;
	Scanner is;
	
	private List<Precept> bundle;
	
	public NetAgent(Socket socket) {
		this.socket = socket;
		this.gson = new Gson();
	}

	@Override
	public void setup(String worldType, String agentID) {
		os.println(gson.toJson(worldType));
	}

	@Override
	public Move getMove(GameState state, List<Precept> bundle) {
		this.bundle = bundle;
		
		Move currMove = nextMove;
		nextMove = null;
		return currMove;
	}

	@Override
	public void run() {
		try {
			os = new PrintStream(socket.getOutputStream());
			is = new Scanner(socket.getInputStream());
			
			while (Thread.interrupted()) {
				os.println(gson.toJson(bundle));
				bundle = null;
				
				String inputLine = is.nextLine();
				
			}
			
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}

}
