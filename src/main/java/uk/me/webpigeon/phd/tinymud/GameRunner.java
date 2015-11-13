package uk.me.webpigeon.phd.tinymud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import uk.me.webpigeon.phd.tinymud.agents.local.RandomAgent;
import uk.me.webpigeon.phd.tinymud.net.MudSocketServer;
import uk.me.webpigeon.phd.tinymud.world.GraphWorld;
import uk.me.webpigeon.phd.tinymud.world.NodeRoom;
import uk.me.webpigeon.phd.tinymud.world.Room;

public class GameRunner {
	public static final Logger LOG = Logger.getLogger(GameRunner.class.getName());
	public static final Integer MAX_TICKS = 1000000000;
	
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		
		GraphWorld world = buildWorld();
		
		GameRuntime runner = new GameRuntime(world);
		
		Map<AgentController, String> agentIDs = new HashMap<>();
		for (AgentController agent : buildAgents()) {
			System.out.println("Agent added: "+agent);
			String agentID = runner.addAgent(agent);
			agentIDs.put(agent, agentID);
		}
		
		MudSocketServer socket = new MudSocketServer(runner);
		pool.submit(socket);
		
		for (int ticks = 0; ticks < MAX_TICKS; ticks++) {
			LOG.fine(String.format("[START] tick %d", ticks));
			
			for (Map.Entry<AgentController, String> agentEntry : agentIDs.entrySet()) {
				runner.doTick();
			}
			
			LOG.fine(String.format("[END] tick %d", ticks));
		}
	}
	
	public static List<AgentController> buildAgents() {
		List<AgentController> agents = new ArrayList<AgentController>();
		agents.add(new RandomAgent());
		
		return agents;
	}

	public static GraphWorld buildWorld() {
		GraphWorld world = new GraphWorld();
		
		Room street = new NodeRoom("Main Street");
		world.addRoom(street);
		
		Room bakery = new NodeRoom("Bakery");
		world.addRoom(bakery);
		
		Room well = new NodeRoom("Well");
		world.addRoom(well);
		
		world.addDuelLink(street, bakery);
		world.addLink(street, well); //trap state
		
		return world;
	}
	
}
