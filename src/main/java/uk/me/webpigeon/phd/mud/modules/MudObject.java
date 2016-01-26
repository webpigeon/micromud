package uk.me.webpigeon.phd.mud.modules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class MudObject {
	protected Set<String> flags;

	public MudObject() {
		this.flags = new HashSet<String>();
	}

	public boolean hasFlag(String flag) {
		return flags.contains(flag);
	}

	public void setFlag(String flag) {
		flags.add(flag);
	}

	public void unsetFlag(String flag) {
		flags.remove(flag);
	}

	public Set<String> getFlags() {
		return Collections.unmodifiableSet(flags);
	}

	public abstract String getID();

	public abstract String[] getKeywords();

	public abstract String getType();

	// narrative
	public abstract String getShortName();

	public abstract String getDescription();

}
