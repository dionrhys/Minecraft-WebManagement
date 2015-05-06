package dion.fyp.webapi.minecraft.resources;

import dion.fyp.webapi.minecraft.WebApiPlugin;
import dion.fyp.webapi.minecraft.entities.ServerBroadcastCommand;
import dion.fyp.webapi.minecraft.entities.ServerEntity;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.bukkit.Bukkit;

@Consumes("application/json")
@Produces("application/json")
@Path("server")
public class ServerResource {
    
    @GET
    public Response get() {
        Future<Response> future = Bukkit.getScheduler().callSyncMethod(WebApiPlugin.getInstance(), () -> {
            return Response.ok(ServerEntity.fromBukkit()).build();
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ServerResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("broadcast")
    public Response postBroadcast(ServerBroadcastCommand command) {
        assert command != null;
        assert command.message != null; // TODO: Proper validation

        Future<Response> future = Bukkit.getScheduler().callSyncMethod(WebApiPlugin.getInstance(), () -> {
            Bukkit.broadcastMessage("[Server] " + command.message);
            return Response.noContent().build();
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ServerResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
}
