package uk.me.webpigeon.phd.mud.modules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MudObject {
	protected Set<String> flags;
	
	public MudObject() {
		this.flags = new HashSet<String>();
	}

	public boolean hasFlag(String flag){
		return flags.contains(flag);
	}
	
	public void setFlag(String flag){
		flags.add(flag);
	}
	
	public void unsetFlag(String flag){
		flags.remove(flag);
	}
	
	public Set<String> getFlags() {
		return Collections.unmodifiableSet(flags);
	}
	
	
}
