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
public class Config {
    
    public static File folder, config;
    public static ConfigurationLoader<CommentedConfigurationNode> loader;
    public static CommentedConfigurationNode cfgNode;
    
    //<editor-fold defaultstate="collapsed" desc="Config file">
    public static void setupFile(File file, ConfigurationLoader<CommentedConfigurationNode> load) {
        config = file;
        loader = load;
    }
    
    public static void loadFile() {
        try {
            if(!config.exists()) {
                config.createNewFile();
            
                cfgNode = loader.load();
                add();
                loader.save(cfgNode);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void add() {
        cfgNode.getNode("Spieler", "Nummer 1").setValue("OGMember1").setComment("Erster Spieler");
        
        cfgNode.getNode("Spieler", "Nummer 2").setValue("JoshiWorld").setComment("Zweiter Spieler");
        cfgNode.getNode("Spieler", "Nummer 2", "Alter").setValue("16");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Config folder">
    public static void setupFolder(File fold) {
        folder = fold;
    }
    
    public static void loadFolder() {
        if(!folder.exists()) {
            folder.mkdir();
        }
        
        try {
            config = new File(folder, "configuration.conf");
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
        if(config == null) {
            loadFolder();
        } 
        
        return cfgNode;
    }
    //</editor-fold>
    
}
