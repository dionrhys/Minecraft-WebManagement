package dion.fyp.webapi.minecraft;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class WebApiPlugin extends JavaPlugin {

    private Logger log;
    
    @Override
    public void onEnable() {
        log = getLogger();
        
        log.info("Enabled!");
    }

    @Override
    public void onDisable() {
        log.info("Disabled!");
        
        log = null;
    }
    
}
