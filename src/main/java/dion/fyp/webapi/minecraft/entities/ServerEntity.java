package dion.fyp.webapi.minecraft.entities;

import org.bukkit.Bukkit;

public class ServerEntity {
    
    private ServerEntity() {}
    
    public static ServerEntity fromBukkit() {
        assert Bukkit.isPrimaryThread();
        
        ServerEntity sv = new ServerEntity();
        sv.state = "online"; // Minecraft doesn't have any other state
        sv.name = Bukkit.getServerName();
        sv.maxPlayers = Bukkit.getMaxPlayers();
        sv.numPlayers = Bukkit.getOnlinePlayers().size();
        sv.gameMode = Bukkit.getDefaultGameMode().toString();
        sv.uptime = 0.0f; // TODO
        sv.address = Bukkit.getIp() + ":" + Bukkit.getPort();
        sv.game = Bukkit.getName();
        sv.version = Bukkit.getVersion();
        sv.platform = System.getProperty("os.name");
        return sv;
    }
    
    public String state;
    public String name;
    public Integer maxPlayers;
    public Integer numPlayers;
    public String gameMode;
    public Float uptime;
    public String address;
    public String game;
    public String version;
    public String platform;
    public String mapName;
}
