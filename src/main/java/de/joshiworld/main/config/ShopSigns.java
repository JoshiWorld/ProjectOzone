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
public class ShopSigns {
    
    public static File folder, config;
    public static ConfigurationLoader<CommentedConfigurationNode> loader;
    public static CommentedConfigurationNode cfgNode;
    
    public static void setup(File fold) {
        fold = new File(fold.getPath() + "/Signs/");
        folder = fold;
    }
    
    public static void load() {
        if(!folder.exists()) {
            folder.mkdir();
        }
        
        try {
            config = new File(folder, "shopsigns.conf");
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
    
    public static CommentedConfigurationNode getNode() {
        load();
        return cfgNode;
    }
    
}
