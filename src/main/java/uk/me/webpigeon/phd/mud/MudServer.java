package uk.me.webpigeon.phd.mud;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.datanucleus.exceptions.NucleusObjectNotFoundException;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import uk.me.webpigeon.phd.mud.engine.MudService;
import uk.me.webpigeon.phd.mud.engine.NetServer;
import uk.me.webpigeon.phd.mud.modules.world.WorldService;

/**
 * Run the mud service standalone (useful for debugging)
 *
 */
public class MudServer {
	private static final Logger LOG = Logger.getLogger(MudServer.class.getCanonicalName());
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		LOG.info("Mud server starting");
		
		//Create list of modules from config
		EngineConfig config = loadInternalFile("spec/engine.json", EngineConfig.class);
		
		Injector injector = buildInjector(config.modules);
		
		//create the core services
		MudService mud = injector.getInstance(MudService.class);
		
		WorldService world = injector.getInstance(WorldService.class);
		world.init();
		
		//Start the netserver
		NetServer netServer = injector.getInstance(NetServer.class);
		netServer.start();
		netServer.debugJoin();	
		
		LOG.info("Mud server closed");
	}
	
	private static Injector buildInjector(List<String> moduleNames) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Module> modules = new ArrayList<Module>();
		for (String moduleName : moduleNames) {
			Class<?> moduleClass = Class.forName(moduleName);
			Module module = (Module)moduleClass.newInstance();
			modules.add(module);
		}
		return Guice.createInjector(modules);
	}
	
	public static <T>  T loadInternalFile(String path, Class<T> clazz) {
		Gson gson = new Gson();
		try {
			ClassLoader loader = MudServer.class.getClassLoader();
			InputStream is = loader.getResourceAsStream(path);
			BufferedReader bis = new BufferedReader(new InputStreamReader(is));
			return gson.fromJson(bis, clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
