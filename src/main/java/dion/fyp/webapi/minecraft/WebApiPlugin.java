package dion.fyp.webapi.minecraft;

import dion.fyp.webapi.minecraft.resources.PlayerResource;
import com.sun.net.httpserver.HttpServer;
import java.net.URI;
import java.util.logging.Logger;
import javax.ws.rs.core.UriBuilder;
import org.apache.logging.log4j.Level;
import org.bukkit.plugin.java.JavaPlugin;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class WebApiPlugin extends JavaPlugin {

    private static WebApiPlugin instance = null;
    
    private Logger log;
    private HttpServer httpServer;
    private ByteArrayAppender consoleLog;
    
    @Override
    public synchronized void onEnable() {
        setInstance(this);
        log = getLogger();
        
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig(PlayerResource.class);
        httpServer = JdkHttpServerFactory.createHttpServer(baseUri, config);
        
        // Create an in-memory log appender and hook it into the root logger
        consoleLog = ByteArrayAppender.createStringAppender();
        consoleLog.start();
        consoleLog.addToLogger("", Level.INFO);
        
        log.info("Enabled!");
    }

    @Override
    public synchronized void onDisable() {
        httpServer.stop(0);
        
        // Remove our log appender from the root logger
        consoleLog.removeFromLogger("");
        consoleLog.stop();
        log.info(consoleLog.getOutput());
        
        log.info("Disabled!");
        
        httpServer = null;
        log = null;
        setInstance(null);
    }
    
    private static synchronized void setInstance(WebApiPlugin plugin) {
        if (instance != null && plugin != null) {
            throw new IllegalStateException("The plugin's singleton instance is already set.");
        }
        instance = plugin;
    }
    
    public static synchronized WebApiPlugin getInstance() {
        if (instance == null) {
            throw new IllegalStateException("The plugin's singleton instance has not been initialized.");
        }
        return instance;
    }
    
}
