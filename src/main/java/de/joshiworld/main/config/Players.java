package de.joshiworld.main.config;

import static de.joshiworld.main.config.Warps.folder;
import java.io.File;
import java.io.IOException;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

/**
 *
 * @author JoshiWorld
 */
public class Players {
    
    public static File folder, config;
    public static ConfigurationLoader<CommentedConfigurationNode> loader;
    public static CommentedConfigurationNode cfgNode;
    
    public static void setup(File fold) {
        fold = new File(fold.getPath() + "/Players/");
        folder = fold;
    }
    
    public static void load(String player) {
        if(!folder.exists()) {
            folder.mkdir();
        }
        
        try {
            config = new File(folder, player + ".conf");
            loader = HoconConfigurationLoader.builder().setFile(config).build();
            
            if(!config.exists()) {
                config.createNewFile();
                cfgNode = loader.load();
                loader.save(cfgNode);
            }
            
            cfgNode = loader.load();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void save() {
        try {
            loader.save(cfgNode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static File getPlayersFile(String player) {
        if(new File(folder, player + ".conf").exists()) {
            return new File(folder, player + ".conf");
        } else {
            return null;
        }
    }
    
    public static boolean playerExist(String player) {
        if(new File(folder, player + ".conf").exists()) {
            return true;
        } else {
            return false;
        }
    }
    
    public static CommentedConfigurationNode loadPlayerNode(String player) {
        try {
            loader = HoconConfigurationLoader.builder().setFile(getPlayersFile(player)).build();
            cfgNode = loader.load();
            return cfgNode;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static File[] getPlayers() {
        return folder.listFiles();
    }
    
}
