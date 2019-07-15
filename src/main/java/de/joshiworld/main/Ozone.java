package de.joshiworld.main;

import com.google.inject.Inject;
import de.joshiworld.main.commands.CommandInit;
import de.joshiworld.main.config.Config;
import de.joshiworld.main.config.Players;
import de.joshiworld.main.config.ShopSigns;
import de.joshiworld.main.config.Warps;
import de.joshiworld.main.listeners.ListenersInit;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

/**
 *
 * @author JoshiWorld
 */
@Plugin(id = "projectozone", 
        name = "ProjectOzone", 
        version = "1.0", 
        description = "ProjectOzone plugin", 
        authors = "JoshiWorld", 
        dependencies = {@Dependency(id = "luckperms")})
public class Ozone {
    private static Ozone plugin;
    private static LuckPermsApi permsApi;
    
    private static String prefix = "§8§l[§d§lOzone§8§l]";
    
    public static List<Player> bau = new ArrayList<>();
    public static List<Player> fight = new ArrayList<>();
    public static List<String> tpa = new ArrayList<>();
    public static List<Player> spectate = new ArrayList<>();
    public static Map<Player, Location> shopsign = new HashMap<>();
    public static Map<Player, Task> teleport = new HashMap<>();
    public static Map<String, Task> tpaTask = new HashMap<>();
    public static Map<Entity, Location> backPort = new HashMap<>();
    public static Map<String, Task> islandInvite = new HashMap<>();
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Config file">
    @Inject
    @DefaultConfig(sharedRoot = true)
    private File file;
    
    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Config folder">
    @Inject
    @ConfigDir(sharedRoot = false)
    private File folder;
    //</editor-fold>
    
    
    
    @Inject
    private Logger logger;
    
    @Listener
    public void onPreInit(GamePreInitializationEvent e) {
        folderfiles();
        //Config.setupFile(file, loader);
        //Config.loadFile();
    }
    
    private void folderfiles() {
        Config.setupFolder(folder);
        
        Players.setup(folder);
        ShopSigns.setup(folder);
        Warps.setup(folder);
    }
    
    
    @Listener
    public void reload(GameReloadEvent e) {
        folderfiles();
        CommandInit.reloadInit();
        ListenersInit.reloadInit();
        
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
        Sponge.getServer().getBroadcastChannel().send(Text.of(prefix + " §aReloadet!"));
    }
    
    
    @Listener
    public void onGameInit(GameInitializationEvent e) {
        plugin = this;
        permsApi = LuckPerms.getApi();
        
        CommandInit.init();
        ListenersInit.init();
        
        logger.info(prefix + " §awurde erfolgreich iniziiert");
    }
    
    @Listener
    public void onStart(GameStartedServerEvent e) {
        /*
        Task.builder().execute(() -> {
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "world load DIM-1");
        }).delay(30, TimeUnit.SECONDS).name("DIM-1_LOAD").submit(plugin);
        
        Task.builder().execute(() -> {
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "world load DIM-1");
        }).delay(10, TimeUnit.SECONDS).name("DIM-1_LOAD").submit(plugin);
        */
    }
    
    //<editor-fold defaultstate="collapsed" desc="getPrefix">
    public static String getPrefix() {
        return prefix;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getPlugin">
    public static Ozone getPlugin() {
        return plugin;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getPermsApi">
    public static LuckPermsApi getPermsApi() {
        return permsApi;
    }
    //</editor-fold>
    
}
