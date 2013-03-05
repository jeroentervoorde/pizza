package org.agoncal.application.petstore.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author jtervoorde
 */
@Named
@RequestScoped
public class YourkitController {
    public void startCPUProfiling() throws Exception {        
        new com.yourkit.api.Controller().startCPUSampling(null);        
    }
    
    public String captureSnapshot() throws Exception { 
        long flags = com.yourkit.api.Controller.SNAPSHOT_WITHOUT_HEAP;
        return new com.yourkit.api.Controller().captureSnapshot(flags);
    }
}
