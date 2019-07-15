package de.joshiworld.main.listeners;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Config;
import de.joshiworld.main.config.Money;
import de.joshiworld.main.config.Players;
import de.joshiworld.main.config.Playtime;
import de.joshiworld.main.config.Score;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.effect.sound.SoundCategories;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.entity.living.player.tab.TabListEntry;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

/**
 *
 * @author JoshiWorld
 */
public class PlayerJoinListener {
    
    @Listener
    public void onJoin(ClientConnectionEvent.Join e) {
        if(e.getSource() instanceof Player) {
            Player p = (Player) e.getSource();
            
            if(Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup().equalsIgnoreCase("default")) {
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + p.getName() + " parent set viewer1");
            }
            
            if(!Players.playerExist(p.getName())) {
                Players.load(p.getName());
                Players.cfgNode.getNode("Money").setValue(500);
                Players.cfgNode.getNode("Muted").setValue(false);
                Players.cfgNode.getNode("Island").setValue(false);
                Players.cfgNode.getNode("playtime", "minuten").setValue(0);
                Players.cfgNode.getNode("playtime", "stunden").setValue(0);
                Players.cfgNode.getNode("playtime", "tage").setValue(0);
                Players.cfgNode.getNode("playtime", "hours").setValue(0);
                Players.save();
                
                if(Sponge.getServer().getWorld("Bodenwelt").isPresent()) {
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_BODENWELT");
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add Bodenwelt " + p.getName().toUpperCase() + "_BODENWELT");
                }
                
                if(Sponge.getServer().getWorld("Skyblock").isPresent()) {
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_SKYBLOCK");
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add Skyblock " + p.getName().toUpperCase() + "_SKYBLOCK");
                }
            }
            
            if(Sponge.getServer().getWorld("world").isPresent()) {
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory create " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory add " + p.getWorld().getName() + " " + p.getName().toUpperCase() + "_" + p.getWorld().getName().toUpperCase());
            }
            
            if(Sponge.getServer().getOnlinePlayers().size() < 2) {
                Task.builder().execute(new Runnable() {
                    @Override
                    public void run() {
                        Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                            Playtime.setPlaytime(
                                    all.getUniqueId().toString(), 
                                    Playtime.getDays(all.getUniqueId().toString()), 
                                    Playtime.getHours(all.getUniqueId().toString()), 
                                    Playtime.getMinutes(all.getUniqueId().toString()) + 1);
                            
                            //<editor-fold defaultstate="collapsed" desc="viewer promote">
                            if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().startsWith("viewer")) {
                                switch(Players.loadPlayerNode(all.getName()).getNode("playtime", "hours").getInt()) {
                                    case 8:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote viewer");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " viewer2");
                                            Money.addMoney(all.getName(), 200);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Viewer Level §e2"));
                                        }
                                        break;
                                    case 18:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote viewer");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " viewer3");
                                            Money.addMoney(all.getName(), 300);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Viewer Level §e3"));
                                        }
                                        break;
                                    case 30:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote viewer");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " viewer4");
                                            Money.addMoney(all.getName(), 400);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Viewer Level §e4"));
                                        }
                                        break;
                                    case 50:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote viewer");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " viewer5");
                                            Money.addMoney(all.getName(), 500);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Viewer Level §e5"));
                                        }
                                        break;
                                    case 70:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote viewer");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " viewer6");
                                            Money.addMoney(all.getName(), 600);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Viewer Level §e6"));
                                        }
                                        break;
                                    case 80:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote viewer");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " viewer7");
                                            Money.addMoney(all.getName(), 700);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Viewer Level §e7"));
                                        }
                                        break;
                                    default:
                                        break;
                                }
                            }
                            //</editor-fold>
                            
                            //<editor-fold defaultstate="collapsed" desc="sub promote">
                            if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().startsWith("sub")) {
                                switch(Players.loadPlayerNode(all.getName()).getNode("playtime", "hours").getInt()) {
                                    case 8:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote sub");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " sub2");
                                            Money.addMoney(all.getName(), 400);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Sub Level §e2"));
                                            break;
                                        }
                                    case 18:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote sub");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " sub3");
                                            Money.addMoney(all.getName(), 600);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Sub Level §e3"));
                                            break;
                                        }
                                    case 30:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote sub");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " sub4");
                                            Money.addMoney(all.getName(), 800);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Sub Level §e4"));
                                            break;
                                        }
                                    case 50:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote sub");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " sub5");
                                            Money.addMoney(all.getName(), 1000);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Sub Level §e5"));
                                            break;
                                        }
                                    case 70:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote sub");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " sub6");
                                            Money.addMoney(all.getName(), 1200);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Sub Level §e6"));
                                            break;
                                        }
                                    case 80:
                                        if(Players.loadPlayerNode(all.getName()).getNode("playtime", "minuten").getInt() == 0) {
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + all.getName() + " promote sub");
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + all.getName() + " sub7");
                                            Money.addMoney(all.getName(), 1400);
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                                            p.playSound(SoundTypes.ENTITY_PLAYER_LEVELUP, SoundCategories.HOSTILE, p.getLocation().getPosition(), 1);
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu bist jetzt Sub Level §e7"));
                                            break;
                                        }
                                    default:
                                        break;
                                }
                            }
                            //</editor-fold>
                        });
                    }
                }).delay(100, TimeUnit.MILLISECONDS).interval(1, TimeUnit.MINUTES).name("playtime").submit(Ozone.getPlugin());
                
                
                
                Task.builder().execute(new Runnable() {
                    @Override
                    public void run() {
                        Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                            if(all.getWorld().getName().equalsIgnoreCase("skyblock") ||
                                    all.getWorld().getName().equalsIgnoreCase("world")) {
                                if(!all.isOnGround() && all.getLocation().getY() < 50) {
                                    all.gameMode().set(GameModes.CREATIVE);
                                    all.setLocationSafely(Ozone.backPort.get((Entity) all));
                                    all.gameMode().set(GameModes.SURVIVAL);
                                }
                            }
                        });
                    }
                }).delay(100, TimeUnit.MILLISECONDS).interval(200, TimeUnit.MILLISECONDS).name("skyPlayerLocCheck").submit(Ozone.getPlugin());
                
                
                
                Task.builder().execute(new Runnable() {
                    @Override
                    public void run() {
                        Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                            if(all.health().get() < 20.0 && all.foodLevel().get() > 15) {
                                HealthData health = all.getHealthData();
                                health.set(Keys.HEALTH, health.health().get() + 1);
                                all.offer(health);
                            }
                        });
                    }
                }).delay(100, TimeUnit.MILLISECONDS).interval(5, TimeUnit.SECONDS).name("playerHeal").submit(Ozone.getPlugin());
            }
            
            String prefixe = Config.getNode().getNode("Prefixe", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
            String prefix = Config.getNode().getNode("Prefix", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
            
            p.getTabList().setHeader(TextSerializers.FORMATTING_CODE.deserialize("§8§l« §a§lWillkommen " + prefix + "§l" + p.getName() + " §8§l»\n"
                    + "§8§l« §a§lauf §d§lProject-Ozone §8§l»\n"
                    + "§8§l« §a§lDein Rang: " + prefixe + " §8§l»"));
            p.getTabList().setFooter(Text.of("§8§l« §7Wichtige Befehle: /hilfe, /warp, /rubine, §8§l»\n"
                    + "      §8§l« §7/support, /spielzeit, /home §8§l»\n"
                    + "      §8§l« §c§oSponsored by §e§oArmyOfMC.com §8§l»"));
            
            
            Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                /*Sponge.getServer().getOnlinePlayers().stream().forEach(sep -> {
                    TabListEntry entry = all.getTabList().getEntry(sep.getUniqueId()).get();
                    
                    String prefixeSep = Config.getNode().getNode("Prefixe", Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup()).getString();
                    String prefixSep = Config.getNode().getNode("Prefix", Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup()).getString();
                    
                    entry.setDisplayName(TextSerializers.FORMATTING_CODE.deserialize(prefixeSep + " " + prefixSep + sep.getName()));
                });*/
                
                if(!all.getTabList().getEntry(p.getUniqueId()).isPresent()) {
                    all.getTabList().addEntry(TabListEntry.builder().profile(GameProfile.of(p.getUniqueId())).build());
                }
                
                Score.setScore(all);
            });
            
            e.setMessage(TextSerializers.FORMATTING_CODE.deserialize("&a» " + prefixe + " " + prefix + p.getName() + " &a»"));
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Score">
    private void setScore(Player p) {
        Scoreboard board = Sponge.getRegistry().createBuilder(Scoreboard.Builder.class).build();
        Objective obj = Sponge.getRegistry().createBuilder(Objective.Builder.class).name("status").criterion(Criteria.DUMMY).displayName(Text.of("§aDummys")).build();
        
        obj.getOrCreateScore(Text.of("Online:")).setScore(1);
        
        if(!board.getTeam("online").isPresent()) {
            Set<Text> text = new HashSet<>();
            text.add(Text.of("§3"));
            
            board.registerTeam(Team.builder()
                    .name("online")
                    .prefix(Text.of("§f" + Sponge.getServer().getOnlinePlayers().size() + "§f/50"))
                    .members(text)
                    .build());
            p.sendMessage(Text.of("Online not present"));
        } else {
            board.getTeam("online").get().setPrefix(Text.of("§f" + Sponge.getServer().getOnlinePlayers().size() + "§f/50"));
            board.getTeam("online").get().setDisplayName(Text.of("§3"));
        }
        
        obj.getOrCreateScore(Text.of("§3")).setScore(0);
        
        board.addObjective(obj);
        board.updateDisplaySlot(obj, DisplaySlots.SIDEBAR);
        
        p.setScoreboard(board);
    }
    
    private void updateScore(Player p) {
        Optional<Objective> obj = p.getScoreboard().getObjective("status");
        
        if(!obj.isPresent() || p.getScoreboard() == null) {
            setScore(p);
        }
        
        Optional<Team> online = p.getScoreboard().getTeam("online");
        if(!online.isPresent()) {
            Set<Text> text = new HashSet<>();
            text.add(Text.of("§3"));
            
            p.getScoreboard().registerTeam(Team.builder()
                    .name("online")
                    .prefix(Text.of("§f" + Sponge.getServer().getOnlinePlayers().size() + "§f/50"))
                    .members(text)
                    .build());
            p.sendMessage(Text.of("Online not present"));
        } else {
            online.get().setPrefix(Text.of("§f" + Sponge.getServer().getOnlinePlayers().size() + "§f/50"));
            online.get().setDisplayName(Text.of("§3"));
        }
    }
    //</editor-fold>
    
}
