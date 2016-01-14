package uk.me.webpigeon.phd.mud.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import uk.me.webpigeon.phd.mud.protocol.MudCore.ServerResponse;

public class DummyClient {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		Socket socket = new Socket("localhost", 8080);
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		PrintStream ps = new PrintStream(os);
		ps.println("debug");
		
		while(!Thread.interrupted()) {
			System.out.println("Awaiting response from server... ");
			ServerResponse resp = ServerResponse.parseFrom(is);
			System.out.println("RESP: "+resp);
			Thread.sleep(1000);
		}
	}

}
