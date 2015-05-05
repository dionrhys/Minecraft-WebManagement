package dion.fyp.webapi.minecraft.entities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerEntity {
    
    private PlayerEntity() {}
    
    public static PlayerEntity fromBukkitPlayer(Player player) {
        assert Bukkit.isPrimaryThread();
        
        PlayerEntity pl = new PlayerEntity();
        pl.id = player.getUniqueId().toString();
        pl.name = player.getName();
        pl.playingTime = null; // TODO
        pl.ping = 0; // TODO (requires NMS)
        pl.isBot = false;
        pl.isLocal = false;
        pl.score = player.getTotalExperience(); // TODO total exp != score?
        return pl;
    }
    
    public String id;
    public String name;
    public Float playingTime;
    public Integer ping;
    public Boolean isBot;
    public Boolean isLocal;
    public Integer score;
}
