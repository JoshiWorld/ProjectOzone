package de.joshiworld.main.config;

import java.io.File;
import java.io.IOException;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

/**
 *
 * @author JoshiWorld
 */
public class Warps {
    
    public static File folder, config;
    public static ConfigurationLoader<CommentedConfigurationNode> loader;
    public static CommentedConfigurationNode cfgNode;
    
    public static void setup(File fold) {
        fold = new File(fold.getPath() + "/Warps/");
        folder = fold;
    }
    
    public static void load(String warp) {
        if(!folder.exists()) {
            folder.mkdir();
        }
        
        try {
            config = new File(folder, warp.toLowerCase() + ".conf");
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
    
    public static CommentedConfigurationNode getNode(String warp) {
        if(!getWarpExists(warp) || config == null || !config.getPath().equals(getWarpFile(warp).getPath())) {
            load(warp);
        } 
        
        return cfgNode;
    }
    
    public static boolean getWarpExists(String warp) {
        if(new File(folder, warp.toLowerCase() + ".conf").exists()) {
            return true;
        } else {
            return false;
        }
    }
    
    public static File getWarpFile(String warp) {
        if(new File(folder, warp + ".conf").exists()) {
            return new File(folder, warp + ".conf");
        } else {
            return null;
        }
    }
    
    public static File[] getWarps() {
        return folder.listFiles();
    }
    
}
