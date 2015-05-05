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
        pl.ping = 999; // TODO (requires NMS)
        pl.isBot = false;
        pl.isLocal = false;
        pl.score = player.getTotalExperience(); // TODO total exp != score?
        return pl;
    }
    
    private String id;
    private String name;
    private Float playingTime;
    private Integer ping;
    private Boolean isBot;
    private Boolean isLocal;
    private Integer score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(Float playingTime) {
        this.playingTime = playingTime;
    }

    public Integer getPing() {
        return ping;
    }

    public void setPing(Integer ping) {
        this.ping = ping;
    }

    public Boolean getIsBot() {
        return isBot;
    }

    public void setIsBot(Boolean isBot) {
        this.isBot = isBot;
    }

    public Boolean getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(Boolean isLocal) {
        this.isLocal = isLocal;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
