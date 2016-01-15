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

public class SecurityManager {
    private Map<String, Session> sessions;

    public SecurityManager() {
        this.sessions = new HashMap<String, Session>();
    }
    
    public void startSession(String sessionKey) {
        Session session = new Session();
        sessions.put(sessionKey, session);
    }
   
    public void endSession(String sessionKey){
        sessions.remove(sessionKey);
    }

    public boolean hasActiveSession(String sessionKey) {
        return sessions.containsKey(sessionKey);
    }

    public Session getSession(String sessionKey) {
        return sessions.get(sessionKey);
    }

}
