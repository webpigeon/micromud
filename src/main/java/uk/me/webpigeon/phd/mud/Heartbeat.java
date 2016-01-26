package uk.me.webpigeon.phd.mud;

import java.util.ArrayList;
import java.util.List;

public class Heartbeat implements Runnable {
	private static final Integer BEAT_TIME_MS = 300;

	private final List<CycleListener> listeners;

	public Heartbeat() {
		this.listeners = new ArrayList<CycleListener>();
	}

	@Override
	public void run() {

		for (CycleListener listener : listeners) {
			listener.onInit();
		}

		try {
			while (!Thread.interrupted()) {

				for (CycleListener listener : listeners) {
					listener.onBeat();
				}

				Thread.sleep(BEAT_TIME_MS);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

		for (CycleListener listener : listeners) {
			listener.onShutdown();
		}

	}

}
