package dion.fyp.webapi.minecraft;

import dion.fyp.webapi.minecraft.resources.PlayerResource;
import com.sun.net.httpserver.HttpServer;
import java.net.URI;
import java.util.logging.Logger;
import javax.ws.rs.core.UriBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class WebApiPlugin extends JavaPlugin {

    private Logger log;
    private HttpServer httpServer;
    
    @Override
    public void onEnable() {
        log = getLogger();
        
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig(PlayerResource.class);
        httpServer = JdkHttpServerFactory.createHttpServer(baseUri, config);
        
        log.info("Enabled!");
    }

    @Override
    public void onDisable() {
        httpServer.stop(0);
        
        log.info("Disabled!");
        
        httpServer = null;
        log = null;
    }
    
}
