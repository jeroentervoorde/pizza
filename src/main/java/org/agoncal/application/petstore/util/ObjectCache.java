package org.agoncal.application.petstore.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jtervoorde
 */
public class ObjectCache extends HashMap<ObjectCache.Key, Object> {
    private static Logger LOGGER = Logger.getLogger(ObjectCache.class.getName());
    private static ObjectCache instance = null;
    public synchronized static ObjectCache getInstance() {        
        if (instance == null) {
            instance = new ObjectCache();
        }
        LOGGER.log(Level.FINE, "Got instance cache " + instance);
        return instance;
    }
    
    public static class Key {
        private final String key;
        
        public Key(String key) {
            this.key = key;
        }
        
        public Key(Long key) {
            this.key = key.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Key other = (Key) obj;
            if ((this.key == null) ? (other.key != null) : !this.key.equals(other.key)) {
                return false;
            }
            return true;
        }

    }
    
}
