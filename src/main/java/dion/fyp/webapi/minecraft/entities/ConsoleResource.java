package dion.fyp.webapi.minecraft.entities;

import dion.fyp.webapi.minecraft.WebApiPlugin;
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
@Path("console")
public class ConsoleResource {
    
    @GET
    public Response get() {
        ConsoleEntity consoleEntity = new ConsoleEntity(WebApiPlugin.getInstance().getConsoleOutput());
        return Response.ok(consoleEntity).build();
    }
    
    @POST
    public Response post(ConsoleExecuteCommand command) {
        assert command != null;
        assert command.command != null; // TODO: Proper validation
        
        Future<Response> future = Bukkit.getScheduler().callSyncMethod(WebApiPlugin.getInstance(), () -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.command);
            return Response.noContent().build();
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ConsoleResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
    
    public class ConsoleEntity {
        public ConsoleEntity(String consoleOutput) {
            text = consoleOutput;
        }
        
        public String text;
    }
    
}
