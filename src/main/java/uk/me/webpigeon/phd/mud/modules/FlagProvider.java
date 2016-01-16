package uk.me.webpigeon.phd.mud.modules;

import java.util.Set;

public interface FlagProvider {

	public boolean hasFlag(String flag);
	public void setFlag(String flag);
	public void unsetFlag(String flag);
	public Set<String> getFlags();
	
}
