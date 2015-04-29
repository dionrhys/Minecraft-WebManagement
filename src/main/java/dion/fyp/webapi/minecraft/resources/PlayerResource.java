package dion.fyp.webapi.minecraft.resources;

import dion.fyp.webapi.minecraft.WebApiPlugin;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.bukkit.Bukkit;

@Produces("text/plain")
@Path("player")
public class PlayerResource {
    
    @GET
    public String getHello() {
        return "Hello, world!";
    }
    
    @GET
    @Path("{playerID}")
    public String getPlayer(@PathParam("playerID") String playerID) {
        Future<?> future = Bukkit.getScheduler().callSyncMethod(WebApiPlugin.getInstance(), () -> {
            Bukkit.getLogger().log(Level.INFO, "Test: {0}", playerID);
            return null;
        });
        
        try {
            future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(PlayerResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return playerID;
    }
    
}
