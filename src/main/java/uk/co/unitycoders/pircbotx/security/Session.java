/**
 * Copyright © 2014 Unity Coders
 *
 * This file is part of uc_pircbotx.
 *
 * uc_pircbotx is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * uc_pircbotx is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * uc_pircbotx. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.unitycoders.pircbotx.security;

import java.util.HashMap;
import java.util.Map;

public class Session {
	private final String sessionKey;
	private final Map<String, String> properties;
	
    public Session(String sessionKey) {
		this.sessionKey = sessionKey;
		this.properties = new HashMap<String, String>();
	}
    
    public void setProp(String key, String value) {
    	properties.put(key, value);
    }
    
    public String getProp(String key, String defaultValue) {
    	String returned = properties.get(key);
    	if (returned == null) {
    		return defaultValue;
    	}
    	
    	return returned;
    }

	// TODO permission checks per session
    public boolean hasPermissions(String[] permissions) {
        return true;
    }

    // TODO permission checks per session
    public boolean hasPermission(String permission) {
        return true;
    }
    
	public boolean isLoggedIn() {
		return true;
	}

	public String getCurrentUser() {
		return null;
	}

	public String getKey() {
		return sessionKey;
	}
}
