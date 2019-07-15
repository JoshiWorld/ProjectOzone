package de.joshiworld.main.config;

import de.joshiworld.main.Ozone;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.tab.TabListEntry;
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
public class Score {
    
    @SuppressWarnings("deprecation")
    public static void setScore(Player p){
        Scoreboard board = Sponge.getRegistry().createBuilder(Scoreboard.Builder.class).build();
        Objective o = Sponge.getRegistry().createBuilder(Objective.Builder.class).name("test").criterion(Criteria.DUMMY).displayName(Text.of("§8§l► §d§lOZONE §8§l◄")).build();
        
	o.getOrCreateScore(Text.of("§0")).setScore(12);
	o.getOrCreateScore(Text.of("§cTeamspeak-IP§8:")).setScore(11);
	o.getOrCreateScore(Text.of("§farmyofmc.com")).setScore(10);
	o.getOrCreateScore(Text.of("§1")).setScore(9);
	o.getOrCreateScore(Text.of("§7Dein Rang§8:")).setScore(8);
        
        String prefixe = Config.getNode().getNode("Prefixe", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
        
        if(!board.getTeam("rang").isPresent()) {
            Set<Text> members = new HashSet<>();
            members.add(Text.of("§2"));
            
            board.registerTeam(Team.builder()
                    .name("rang")
                    .prefix(TextSerializers.FORMATTING_CODE.deserialize(prefixe))
                    .members(members)
                    .build());
        } else {
            board.getTeam("rang").get().setPrefix(TextSerializers.FORMATTING_CODE.deserialize(prefixe));
            board.getTeam("rang").get().addMember(Text.of("§2"));
        }
	    
        o.getOrCreateScore(Text.of("§2")).setScore(7);
        o.getOrCreateScore(Text.of("§6")).setScore(6);
               
        o.getOrCreateScore(Text.of("§cRubine§8:")).setScore(5);
        
        if(!board.getTeam("coins").isPresent()) {
            Set<Text> members = new HashSet<>();
            members.add(Text.of("§8"));
            
            board.registerTeam(Team.builder()
                    .name("coins")
                    .prefix(Text.of("§f" + Players.loadPlayerNode(p.getName()).getNode("Money").getInt()))
                    .members(members)
                    .build());
        } else {
            board.getTeam("coins").get().setPrefix(Text.of("§f" + Players.loadPlayerNode(p.getName()).getNode("Money").getInt()));
            board.getTeam("coins").get().addMember(Text.of("§8"));
        }
	    
        o.getOrCreateScore(Text.of("§8")).setScore(4);
        o.getOrCreateScore(Text.of("§5")).setScore(3);   
        o.getOrCreateScore(Text.of("§aOnline-Spieler§8:")).setScore(2);
	    
        if(!board.getTeam("online").isPresent()) {
            Set<Text> members = new HashSet<>();
            members.add(Text.of("§3"));
            
            board.registerTeam(Team.builder()
                    .name("online")
                    .prefix(Text.of("§f" + Sponge.getServer().getOnlinePlayers().size()))
                    .members(members)
                    .build());
        } else {
            board.getTeam("online").get().setPrefix(Text.of("§f" + Sponge.getServer().getOnlinePlayers().size()));
            board.getTeam("online").get().addMember(Text.of("§3"));
        }
        
	o.getOrCreateScore(Text.of("§3")).setScore(1);
	o.getOrCreateScore(Text.of("§4")).setScore(0);
	   
	board.addObjective(o);
        board.updateDisplaySlot(o, DisplaySlots.SIDEBAR);
        
        p.setScoreboard(board);
        
        for(Player all : Sponge.getServer().getOnlinePlayers()) {
            for(Player sep : Sponge.getServer().getOnlinePlayers()) {
                Optional<TabListEntry> entryOpt = all.getTabList().getEntry(sep.getUniqueId());
                
                if(entryOpt.isPresent()) {
                    TabListEntry entry = entryOpt.get();

                    String prefixeSep = Config.getNode().getNode("Prefixe", Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup()).getString();
                    String prefixSep = Config.getNode().getNode("Prefix", Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup()).getString();
                    String suffixeSep = Config.getNode().getNode("Suffixe", Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup()).getString();

                    if(Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup().startsWith("viewer") || Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup().startsWith("sub")) {
                        entry.setDisplayName(TextSerializers.FORMATTING_CODE.deserialize(prefixeSep + " " + prefixSep + sep.getName() + " " + suffixeSep));
                    } else {
                        entry.setDisplayName(TextSerializers.FORMATTING_CODE.deserialize(prefixeSep + " " + prefixSep + sep.getName()));
                    }
                } else {
                    //all.sendMessage(Text.of(Ozone.getPrefix() + " §cEs scheint, du hast einen Fehler mit deiner Tablist. Bitte reconnecte, um das Problem zu beheben"));
                    //sep.sendMessage(Text.of(Ozone.getPrefix() + " §cEs scheint, du hast einen Fehler mit deiner Tablist. Bitte reconnecte, um das Problem zu beheben"));
                }
            }
        }
        
        //<editor-fold defaultstate="collapsed" desc="Ränge">
        /*
        //<editor-fold defaultstate="collapsed" desc="Admin">
        if(!board.getTeam("0001Admin").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0001Admin")
                    .prefix(Text.of("§4§lAdmin §4"))
                    .build());
        } else {
            board.getTeam("0001Admin").get().setPrefix(Text.of("§4§lAdmin §4"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Mod">
        if(!board.getTeam("0002Mod").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0002Mod")
                    .prefix(Text.of("§c§lMod §c"))
                    .build());
        } else {
            board.getTeam("0002Mod").get().setPrefix(Text.of("§c§lMod §c"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Editor">
        if(!board.getTeam("0003Editor").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0003Editor")
                    .prefix(Text.of("§e§lEditor §e"))
                    .build());
        } else {
            board.getTeam("0003Editor").get().setPrefix(Text.of("§e§lEditor §e"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Supp">
        if(!board.getTeam("0004Supp").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0004Supp")
                    .prefix(Text.of("§9§lSupp §9"))
                    .build());
        } else {
            board.getTeam("0004Supp").get().setPrefix(Text.of("§9§lSupp §9"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Vip">
        if(!board.getTeam("0005Vip").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0005Vip")
                    .prefix(Text.of("§6§lVIP §6"))
                    .build());
        } else {
            board.getTeam("0005Vip").get().setPrefix(Text.of("§6§lVIP §6"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Sub7">
        if(!board.getTeam("0006Sub7").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0006Sub7")
                    .prefix(Text.of("§5§lSub §5"))
                    .suffix(Text.of(" §7[§a7§7]"))
                    .build());
        } else {
            board.getTeam("0006Sub7").get().setPrefix(Text.of("§5§lSub §5"));
            board.getTeam("0006Sub7").get().setSuffix(Text.of(" §7[§a7§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Sub6">
        if(!board.getTeam("0007Sub6").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0007Sub6")
                    .prefix(Text.of("§5§lSub §5"))
                    .suffix(Text.of(" §7[§a6§7]"))
                    .build());
        } else {
            board.getTeam("0007Sub6").get().setPrefix(Text.of("§5§lSub §5"));
            board.getTeam("0007Sub6").get().setSuffix(Text.of(" §7[§a6§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Sub5">
        if(!board.getTeam("0008Sub5").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0008Sub5")
                    .prefix(Text.of("§5§lSub §5"))
                    .suffix(Text.of(" §7[§a5§7]"))
                    .build());
        } else {
            board.getTeam("0008Sub5").get().setPrefix(Text.of("§5§lSub §5"));
            board.getTeam("0008Sub5").get().setSuffix(Text.of(" §7[§a5§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Sub4">
        if(!board.getTeam("0009Sub4").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0009Sub4")
                    .prefix(Text.of("§5§lSub §5"))
                    .suffix(Text.of(" §7[§a4§7]"))
                    .build());
        } else {
            board.getTeam("0009Sub4").get().setPrefix(Text.of("§5§lSub §5"));
            board.getTeam("0009Sub4").get().setSuffix(Text.of(" §7[§a4§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Sub3">
        if(!board.getTeam("0010Sub3").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0010Sub3")
                    .prefix(Text.of("§5§lSub §5"))
                    .suffix(Text.of(" §7[§a3§7]"))
                    .build());
        } else {
            board.getTeam("0010Sub3").get().setPrefix(Text.of("§5§lSub §5"));
            board.getTeam("0010Sub3").get().setSuffix(Text.of(" §7[§a3§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Sub2">
        if(!board.getTeam("0011Sub2").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0011Sub2")
                    .prefix(Text.of("§5§lSub §5"))
                    .suffix(Text.of(" §7[§a2§7]"))
                    .build());
        } else {
            board.getTeam("0011Sub2").get().setPrefix(Text.of("§5§lSub §5"));
            board.getTeam("0011Sub2").get().setSuffix(Text.of(" §7[§a2§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Sub1">
        if(!board.getTeam("0012Sub1").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0012Sub1")
                    .prefix(Text.of("§5§lSub §5"))
                    .suffix(Text.of(" §7[§a1§7]"))
                    .build());
        } else {
            board.getTeam("0012Sub1").get().setPrefix(Text.of("§5§lSub §5"));
            board.getTeam("0012Sub1").get().setSuffix(Text.of(" §7[§a1§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Viewer7">
        if(!board.getTeam("0013Viewer7").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0013Viewer7")
                    .prefix(Text.of("§8§lViewer §8"))
                    .suffix(Text.of(" §7[§a7§7]"))
                    .build());
        } else {
            board.getTeam("0013Viewer7").get().setPrefix(Text.of("§8§lViewer §8"));
            board.getTeam("0013Viewer7").get().setSuffix(Text.of(" §7[§a7§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Viewer6">
        if(!board.getTeam("0014Viewer6").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0014Viewer6")
                    .prefix(Text.of("§8§lViewer §8"))
                    .suffix(Text.of(" §7[§a6§7]"))
                    .build());
        } else {
            board.getTeam("0014Viewer6").get().setPrefix(Text.of("§8§lViewer §8"));
            board.getTeam("0014Viewer6").get().setSuffix(Text.of(" §7[§a6§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Viewer5">
        if(!board.getTeam("0015Viewer5").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0015Viewer5")
                    .prefix(Text.of("§8§lViewer §8"))
                    .suffix(Text.of(" §7[§a5§7]"))
                    .build());
        } else {
            board.getTeam("0015Viewer5").get().setPrefix(Text.of("§8§lViewer §8"));
            board.getTeam("0015Viewer5").get().setSuffix(Text.of(" §7[§a5§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Viewer4">
        if(!board.getTeam("0016Viewer4").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0016Viewer4")
                    .prefix(Text.of("§8§lViewer §8"))
                    .suffix(Text.of(" §7[§a4§7]"))
                    .build());
        } else {
            board.getTeam("0016Viewer4").get().setPrefix(Text.of("§8§lViewer §8"));
            board.getTeam("0016Viewer4").get().setSuffix(Text.of(" §7[§a4§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Viewer3">
        if(!board.getTeam("0017Viewer3").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0017Viewer3")
                    .prefix(Text.of("§8§lViewer §8"))
                    .suffix(Text.of(" §7[§a3§7]"))
                    .build());
        } else {
            board.getTeam("0017Viewer3").get().setPrefix(Text.of("§8§lViewer §8"));
            board.getTeam("0017Viewer3").get().setSuffix(Text.of(" §7[§a3§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Viewer2">
        if(!board.getTeam("0018Viewer2").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0018Viewer2")
                    .prefix(Text.of("§8§lViewer §8"))
                    .suffix(Text.of(" §7[§a2§7]"))
                    .build());
        } else {
            board.getTeam("0018Viewer2").get().setPrefix(Text.of("§8§lViewer §8"));
            board.getTeam("0018Viewer2").get().setSuffix(Text.of(" §7[§a2§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Viewer1">
        if(!board.getTeam("0019Viewer1").isPresent()) {
            board.registerTeam(Team.builder()
                    .name("0019Viewer1")
                    .prefix(Text.of("§8§lViewer §8"))
                    .suffix(Text.of(" §7[§a1§7]"))
                    .build());
        } else {
            board.getTeam("0019Viewer1").get().setPrefix(Text.of("§8§lViewer §8"));
            board.getTeam("0019Viewer1").get().setSuffix(Text.of(" §7[§a1§7]"));
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Teams add">
        Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
            if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("admin")) {
                board.getTeam("0001Admin").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("mod")) {
                board.getTeam("0002Mod").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("editor")) {
                board.getTeam("0003Editor").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("Supp")) {
                board.getTeam("0004Supp").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("vip")) {
                board.getTeam("0005Vip").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("sub7")) {
                board.getTeam("0006Sub7").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("sub6")) {
                board.getTeam("0007Sub6").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("sub5")) {
                board.getTeam("0008Sub5").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("sub4")) {
                board.getTeam("0009Sub4").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("sub3")) {
                board.getTeam("0010Sub3").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("sub2")) {
                board.getTeam("0011Sub2").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("sub1")) {
                board.getTeam("0012Sub1").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("viewer7")) {
                board.getTeam("0013Viewer7").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("viewer6")) {
                board.getTeam("0014Viewer6").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("viewer5")) {
                board.getTeam("0015Viewer5").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("viewer4")) {
                board.getTeam("0016Viewer4").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("viewer3")) {
                board.getTeam("0017Viewer3").get().addMember(Text.of(all.getName()));
            } else if(Ozone.getPermsApi().getUser(all.getName()).getPrimaryGroup().equalsIgnoreCase("viewer2")) {
                board.getTeam("0018Viewer2").get().addMember(Text.of(all.getName()));
            } else {
                board.getTeam("0019Viewer1").get().addMember(Text.of(all.getName()));
            }
        });
*/
        //</editor-fold>
        //</editor-fold>
    }

    //<editor-fold defaultstate="collapsed" desc="updateScore">
    public static void updateScore(Player p){
	Optional<Objective> o = p.getScoreboard().getObjective("test");
        
	if(!o.isPresent() || p.getScoreboard() == null) {
            setScore(p);
	}
		
	Scoreboard board = p.getScoreboard();
        
        String prefixe = Config.getNode().getNode("Prefixe", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
        
        if(!board.getTeam("rang").isPresent()) {
            Set<Text> members = new HashSet<>();
            members.add(Text.of("§2"));
            
            board.registerTeam(Team.builder()
                    .name("rang")
                    .prefix(TextSerializers.FORMATTING_CODE.deserialize(prefixe))
                    .members(members)
                    .build());
        } else {
            board.getTeam("rang").get().setPrefix(TextSerializers.FORMATTING_CODE.deserialize(prefixe));
            board.getTeam("rang").get().addMember(Text.of("§2"));
        }
        
        
        
        if(!board.getTeam("coins").isPresent()) {
            Set<Text> members = new HashSet<>();
            members.add(Text.of("§8"));
            
            board.registerTeam(Team.builder()
                    .name("coins")
                    .prefix(Text.of("§f" + Players.loadPlayerNode(p.getName()).getNode("Money").getInt()))
                    .members(members)
                    .build());
        } else {
            board.getTeam("coins").get().setPrefix(Text.of("§f" + Players.loadPlayerNode(p.getName()).getNode("Money").getInt()));
            board.getTeam("coins").get().addMember(Text.of("§8"));
        }
        
        
        
        if(!board.getTeam("online").isPresent()) {
            Set<Text> members = new HashSet<>();
            members.add(Text.of("§3"));
            
            board.registerTeam(Team.builder()
                    .name("online")
                    .prefix(Text.of("§f" + Sponge.getServer().getOnlinePlayers().size()))
                    .members(members)
                    .build());
        } else {
            board.getTeam("online").get().setPrefix(Text.of("§f" + Sponge.getServer().getOnlinePlayers().size()));
            board.getTeam("online").get().addMember(Text.of("§3"));
        }
    }
    //</editor-fold>
}
