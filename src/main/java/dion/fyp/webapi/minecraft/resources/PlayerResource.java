package dion.fyp.webapi.minecraft.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("player")
public class PlayerResource {
    
    @GET
    @Produces("text/plain")
    public String getHello() {
        return "Hello, world!";
    }
    
}
