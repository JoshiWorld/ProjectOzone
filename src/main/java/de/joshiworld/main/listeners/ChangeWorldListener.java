package de.joshiworld.main.listeners;

import de.joshiworld.main.Ozone;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.scheduler.Task;

/**
 *
 * @author JoshiWorld
 */
public class ChangeWorldListener {
    
    @Listener
    public void onChangeWorld(MoveEntityEvent.Teleport e) {
        if(e.getTargetEntity() instanceof Player) {
            Player p = (Player) e.getTargetEntity();
            
            if(!e.getFromTransform().getExtent().getName().equals(e.getToTransform().getExtent().getName())) {
                /*Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory kit " + p.getName().toUpperCase() + "_" + e.getFromTransform().getExtent().getName().toUpperCase());
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_" + e.getFromTransform().getExtent().getName().toUpperCase());
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add " + e.getFromTransform().getExtent().getName() + " " + p.getName().toUpperCase() + "_" + e.getFromTransform().getExtent().getName().toUpperCase());

                Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory get " + p.getName().toUpperCase() + "_" + e.getToTransform().getExtent().getName().toUpperCase());
                */
                
                
                if((!e.getFromTransform().getExtent().getName().equalsIgnoreCase("Nether") && !e.getToTransform().getExtent().getName().equalsIgnoreCase("skyblock")) ||
                        (!e.getFromTransform().getExtent().getName().equalsIgnoreCase("skyblock") && !e.getToTransform().getExtent().getName().equalsIgnoreCase("Nether")) ||
                        (!e.getFromTransform().getExtent().getName().equalsIgnoreCase("bodenwelt") && !e.getToTransform().getExtent().getName().equalsIgnoreCase("Bodennether")) ||
                        (!e.getFromTransform().getExtent().getName().equalsIgnoreCase("Bodennether") && !e.getToTransform().getExtent().getName().equalsIgnoreCase("bodenwelt"))) {
                    
                    
                    Task.builder().execute(() -> {
                        if(!e.getFromTransform().getExtent().getName().equals(p.getWorld().getName())) {
                            if(p.getWorld().getName().equalsIgnoreCase("bodenwelt") || p.getWorld().getName().equalsIgnoreCase("Bodennether")) {
                                Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory kit " + p.getName().toUpperCase() + "_BODENWELT");
                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_BODENWELT");
                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add Bodenwelt " + p.getName().toUpperCase() + "_BODENWELT");
                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add Bodennether " + p.getName().toUpperCase() + "_BODENWELT");

                                //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear " + p.getName());
                                //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear --confirm");
                                Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory get " + p.getName().toUpperCase() + "_BODENWELT");
                            } else if(p.getWorld().getName().equalsIgnoreCase("skyblock") || p.getWorld().getName().equalsIgnoreCase("Nether")) {
                                Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory kit " + p.getName().toUpperCase() + "_SKYBLOCK");
                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_SKYBLOCK");
                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add Skyblock " + p.getName().toUpperCase() + "_SKYBLOCK");
                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add Nether " + p.getName().toUpperCase() + "_SKYBLOCK");

                                Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory get " + p.getName().toUpperCase() + "_SKYBLOCK");
                            } else {
                                Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory kit " + p.getName().toUpperCase() + "_" + e.getFromTransform().getExtent().getName().toUpperCase());
                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_" + e.getFromTransform().getExtent().getName().toUpperCase());
                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add " + e.getFromTransform().getExtent().getName() + " " + p.getName().toUpperCase() + "_" + e.getFromTransform().getExtent().getName().toUpperCase());

                                Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory get " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
                            }
                        }
                    }).delay(250, TimeUnit.MILLISECONDS).name(p.getName() + "_teleportWorld").submit(Ozone.getPlugin());
                }
            }
            
            //<editor-fold defaultstate="collapsed" desc="old inv changer">
            /*
            if(!e.getFromTransform().getExtent().getName().equals(e.getToTransform().getExtent().getName())) {
                if(e.getFromTransform().getExtent().getName().equalsIgnoreCase("Nether")) {
                    Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory kit " + p.getName().toUpperCase() + "_SKYBLOCK");
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_SKYBLOCK");
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add " + p.getWorld().getName() + " " + p.getName().toUpperCase() + "_SKYBLOCK");
                } else if(e.getFromTransform().getExtent().getName().equalsIgnoreCase("Bodennether")) {
                    Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory kit " + p.getName().toUpperCase() + "_BODENWELT");
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_BODENWELT");
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add " + p.getWorld().getName() + " " + p.getName().toUpperCase() + "_BODENWELT");
                } else {
                    Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory kit " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
                    //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory delete " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
                    //Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory delete --confirm ");
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add " + p.getWorld().getName() + " " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
                }
                
                
                
                Task.builder().execute(() -> {
                    if(!world.equalsIgnoreCase(p.getWorld().getName())) {
                        if(p.getWorld().getName().equalsIgnoreCase("bodenwelt") || p.getWorld().getName().equalsIgnoreCase("skyblock")) {
                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear " + p.getName());
                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear --confirm");
                            Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory get " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
                        }
                    }
                }).delay(250, TimeUnit.MILLISECONDS).name(p.getName() + "_teleportWorld").submit(Ozone.getPlugin());
                
                
                
                if(e.getToTransform().getExtent().getName().equalsIgnoreCase("Nether")) {
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear " + p.getName());
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear --confirm");
                    Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory get " + p.getName().toUpperCase() + "_SKYBLOCK");
                }
                
                if(e.getToTransform().getExtent().getName().equalsIgnoreCase("Bodennether")) {
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear " + p.getName());
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear --confirm");
                    Sponge.getCommandManager().process(p.getCommandSource().get(), "inventory get " + p.getName().toUpperCase() + "_BODENWELT");
                }
            }
            */
            //</editor-fold>
        }
    }
    
    @Listener
    public void onLocCheck(MoveEntityEvent e) {
        if(e.getTargetEntity().getWorld().getName().equalsIgnoreCase("skyblock") || 
                e.getTargetEntity().getWorld().getName().equalsIgnoreCase("world")) {
            if(e.getFromTransform().getExtent().getName().equals(e.getToTransform().getExtent().getName())) {
                if(e.getTargetEntity() instanceof Player) {
                    if(e.getTargetEntity().isOnGround()) {
                        Ozone.backPort.put(e.getTargetEntity(), e.getTargetEntity().getLocation());
                    }
                }
            }
        }
    }
    
}
