package de.joshiworld.main.config;

import java.util.UUID;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class Playtime {
    
    //<editor-fold defaultstate="collapsed" desc="setPlaytime">
    public static void setPlaytime(String uuid, int tage, int stunden, int minuten) {
        if(Sponge.getServer().getPlayer(UUID.fromString(uuid)).isPresent()) {
            Player p = (Player) Sponge.getServer().getPlayer(UUID.fromString(uuid)).get();
            
            Players.loadPlayerNode(p.getName()).getNode("playtime", "minuten").setValue(minuten);
            Players.save();
            Players.loadPlayerNode(p.getName()).getNode("playtime", "stunden").setValue(stunden);
            Players.save();
            Players.loadPlayerNode(p.getName()).getNode("playtime", "tage").setValue(tage);
            Players.save();
            
            if(Players.loadPlayerNode(p.getName()).getNode("playtime", "minuten").getInt() > 59) {
                Players.loadPlayerNode(p.getName()).getNode("playtime", "minuten").setValue(0);
                Players.save();
                
                int s = Players.loadPlayerNode(p.getName()).getNode("playtime", "stunden").getInt() + 1;
                Players.loadPlayerNode(p.getName()).getNode("playtime", "stunden").setValue(s);
                Players.save();
                
                Players.loadPlayerNode(p.getName()).getNode("playtime", "hours").setValue(s);
                Players.save();
            } 
            
            if(Players.loadPlayerNode(p.getName()).getNode("playtime", "stunden").getInt() > 23) {
                Players.loadPlayerNode(p.getName()).getNode("playtime", "stunden").setValue(0);
                Players.save();
                
                int d = Players.loadPlayerNode(p.getName()).getNode("playtime", "tage").getInt() + 1;
                Players.loadPlayerNode(p.getName()).getNode("playtime", "tage").setValue(d);
                
                Players.save();
            } 
        } else {
            Sponge.getServer().getBroadcastChannel().send(Text.of("§cNot present"));
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getPlaytime">
    public static String getPlaytime(String uuid) {
        String playtime = "";
        
        if(Sponge.getServer().getPlayer(UUID.fromString(uuid)).isPresent()) {
            Player p = (Player) Sponge.getServer().getPlayer(UUID.fromString(uuid)).get();
            
            int tage = Players.loadPlayerNode(p.getName()).getNode("playtime", "tage").getInt();
            int stunden = Players.loadPlayerNode(p.getName()).getNode("playtime", "stunden").getInt();
            int minuten = Players.loadPlayerNode(p.getName()).getNode("playtime", "minuten").getInt();
            
            String days = "§e" + tage;
            
            if(tage == 1) {
                days = "§e" + tage + " §aTag§7, ";
            } else {
                days = "§e" + tage + " §aTage§7, ";
            }
            
            
            
            String hours = "§e" + stunden;
            
            if(stunden == 1) {
                hours = "§e" + stunden + " §aStunde§7, ";
            } else {
                hours = "§e" + stunden + " §aStunden§7, ";
            }
            
            
            
            String minutes = "§e" + minuten;
            
            if(minuten == 1) {
                minutes = "§e" + minuten + " §aMinute";
            } else {
                minutes = "§e" + minuten + " §aMinuten";
            }
            
            playtime = days + hours + minutes;
        }
        
        return playtime;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getDays">
    public static int getDays(String uuid) {
        int days = 0;
        
        if(Sponge.getServer().getPlayer(UUID.fromString(uuid)).isPresent()) {
            Player p = (Player) Sponge.getServer().getPlayer(UUID.fromString(uuid)).get();
            
            days = Players.loadPlayerNode(p.getName()).getNode("playtime", "tage").getInt();
        }
        
        return days;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getHours">
    public static int getHours(String uuid) {
        int hours = 0;
        
        if(Sponge.getServer().getPlayer(UUID.fromString(uuid)).isPresent()) {
            Player p = (Player) Sponge.getServer().getPlayer(UUID.fromString(uuid)).get();
            
            hours = Players.loadPlayerNode(p.getName()).getNode("playtime", "stunden").getInt();
        }
        
        return hours;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getMinutes">
    public static int getMinutes(String uuid) {
        int minutes = 0;
        
        if(Sponge.getServer().getPlayer(UUID.fromString(uuid)).isPresent()) {
            Player p = (Player) Sponge.getServer().getPlayer(UUID.fromString(uuid)).get();
            
            minutes = Players.loadPlayerNode(p.getName()).getNode("playtime", "minuten").getInt();
        }
        
        return minutes;
    }
    //</editor-fold>
    
}
