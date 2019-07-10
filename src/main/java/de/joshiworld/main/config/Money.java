package de.joshiworld.main.config;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class Money {
    
    //<editor-fold defaultstate="collapsed" desc="playersExists">
    private static boolean playerExists(String player) {
        if(!Players.playerExist(player)) {
            return false;
        } else {
            return true;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getMoney">
    public static int getMoney(String player) {
        int i = 0;
        
        if(!playerExists(player)) {
            return 0;
        } else {
            i = Players.loadPlayerNode(player).getNode("Money").getInt();
        }
        
        return i;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setMoney">
    public static void setMoney(String player, int money) {
        if(!playerExists(player)) {
            return;
        } else {
            Players.loadPlayerNode(player).getNode("Money").setValue(money);
            Players.save();
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="removeMoney">
    public static void removeMoney(String player, int money) {
        if(!playerExists(player)) {
            return;
        } else {
            int rubys = getMoney(player);
            Players.loadPlayerNode(player).getNode("Money").setValue(rubys - money);
            Players.save();
            
            Sponge.getServer().getPlayer(player).get().sendMessage(Text.of("§8[§c-§8] §c" + money));
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="addMoney">
    public static void addMoney(String player, int money) {
        if(!playerExists(player)) {
            return;
        } else {
            int rubys = getMoney(player);
            Players.loadPlayerNode(player).getNode("Money").setValue(rubys + money);
            Players.save();
            
            Sponge.getServer().getPlayer(player).get().sendMessage(Text.of("§8[§a+§8] §a" + money));
        }
    }
    //</editor-fold>
    
}
