package dion.fyp.webapi.minecraft.resources;

import dion.fyp.webapi.minecraft.WebApiPlugin;
import dion.fyp.webapi.minecraft.entities.PlayerEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Produces("application/json")
@Path("players")
public class PlayersResource {

    @GET
    public Response getAll() {
        Future<Response> future = Bukkit.getScheduler().callSyncMethod(WebApiPlugin.getInstance(), () -> {
            //Bukkit.getLogger().log(Level.INFO, "GET /players");
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();

            ArrayList<PlayerEntity> playerEnts = new ArrayList<>();
            for (Player player : players) {
                playerEnts.add(PlayerEntity.fromBukkitPlayer(player));
            }

            return Response.ok(playerEnts).build();
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(PlayersResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("{playerID}")
    public Response get(@PathParam("playerID") UUID playerID) {
        Future<Response> future = Bukkit.getScheduler().callSyncMethod(WebApiPlugin.getInstance(), () -> {
            //Bukkit.getLogger().log(Level.INFO, "GET /players/{0}", playerID);
            org.bukkit.entity.Player player = Bukkit.getPlayer(playerID);

            if (player == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            return Response.ok(PlayerEntity.fromBukkitPlayer(player)).build();
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(PlayersResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }

}
