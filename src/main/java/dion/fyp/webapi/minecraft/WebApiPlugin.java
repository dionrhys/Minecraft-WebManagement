package dion.fyp.webapi.minecraft;

import dion.fyp.webapi.minecraft.resources.PlayersResource;
import com.sun.net.httpserver.HttpServer;
import dion.fyp.webapi.minecraft.entities.ConsoleResource;
import dion.fyp.webapi.minecraft.resources.ServerResource;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.apache.logging.log4j.Level;
import org.bukkit.plugin.java.JavaPlugin;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class WebApiPlugin extends JavaPlugin {

    private static WebApiPlugin instance = null;

    private HttpServer httpServer;
    private ByteArrayAppender consoleLog;

    @Override
    public synchronized void onEnable() {
        setInstance(this);

        // Create an in-memory log appender and hook it into the root logger
        consoleLog = ByteArrayAppender.createStringAppender();
        consoleLog.start();
        consoleLog.addToLogger("", Level.INFO);

        // Configure and start up the web server
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig config = new ResourceConfig(
                ConsoleResource.class,
                PlayersResource.class,
                ServerResource.class,
                JacksonFeature.class
        );
        httpServer = JdkHttpServerFactory.createHttpServer(baseUri, config);

        //getLogger().info("Enabled!");
    }

    @Override
    public synchronized void onDisable() {
        // Stop the web server immediately
        httpServer.stop(0);

        // Remove our log appender from the root logger
        consoleLog.removeFromLogger("");
        consoleLog.stop();

        //getLogger().info("Disabled!");

        httpServer = null;
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

    public synchronized String getConsoleOutput() {
        return consoleLog.getOutput();
    }

}
