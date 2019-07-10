package de.joshiworld.main.commands.home;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import me.lucko.luckperms.api.caching.PermissionData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class SethomeCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            if(args.getOne(Text.of("home")).isPresent()) {
                String home = (String) args.getOne(Text.of("home")).get();

                PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));

                if(pd.getPermissionValue("projectozone.homes.2").asBoolean()) {
                    if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() < 2) {
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "world").setValue(p.getWorld().getName());
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "x").setValue(p.getLocation().getX());
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "y").setValue(p.getLocation().getY());
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "z").setValue(p.getLocation().getZ());
                        Players.save();

                        p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast den Home-Punkt: §e" + home.toLowerCase() + " §agesetzt"));
                    } else {
                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst nur maximal 2 Home-Punkte gleichzeitig haben"));
                    }
                }



                if(pd.getPermissionValue("projectozone.homes.3").asBoolean()) {
                    if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() < 2) {
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "world").setValue(p.getWorld().getName());
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "x").setValue(p.getLocation().getX());
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "y").setValue(p.getLocation().getY());
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "z").setValue(p.getLocation().getZ());
                        Players.save();

                        p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast den Home-Punkt: §e" + home.toLowerCase() + " §agesetzt"));
                    } else {
                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst nur maximal 3 Home-Punkte gleichzeitig haben"));
                    }
                }



                if(pd.getPermissionValue("projectozone.homes.5").asBoolean()) {
                    if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() < 2) {
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "world").setValue(p.getWorld().getName());
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "x").setValue(p.getLocation().getX());
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "y").setValue(p.getLocation().getY());
                        Players.cfgNode.getNode("homes", home.toLowerCase(), "z").setValue(p.getLocation().getZ());
                        Players.save();

                        p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast den Home-Punkt: §e" + home.toLowerCase() + " §agesetzt"));
                    } else {
                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst nur maximal 5 Home-Punkte gleichzeitig haben"));
                    }
                }
            } else {
                PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));

                if(pd.getPermissionValue("projectozone.homes.2").asBoolean()) {
                    if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() < 2) {
                        Players.cfgNode.getNode("homes", "home", "world").setValue(p.getWorld().getName());
                        Players.cfgNode.getNode("homes", "home", "x").setValue(p.getLocation().getX());
                        Players.cfgNode.getNode("homes", "home", "y").setValue(p.getLocation().getY());
                        Players.cfgNode.getNode("homes", "home", "z").setValue(p.getLocation().getZ());
                        Players.save();

                        p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast den Home-Punkt: §e" + "home" + " §agesetzt"));
                    } else {
                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst nur maximal 2 Home-Punkte gleichzeitig haben"));
                    }
                }



                if(pd.getPermissionValue("projectozone.homes.3").asBoolean()) {
                    if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() < 2) {
                        Players.cfgNode.getNode("homes", "home", "world").setValue(p.getWorld().getName());
                        Players.cfgNode.getNode("homes", "home", "x").setValue(p.getLocation().getX());
                        Players.cfgNode.getNode("homes", "home", "y").setValue(p.getLocation().getY());
                        Players.cfgNode.getNode("homes", "home", "z").setValue(p.getLocation().getZ());
                        Players.save();

                        p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast den Home-Punkt: §e" + "home" + " §agesetzt"));
                    } else {
                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst nur maximal 3 Home-Punkte gleichzeitig haben"));
                    }
                }



                if(pd.getPermissionValue("projectozone.homes.5").asBoolean()) {
                    if(Players.loadPlayerNode(p.getName()).getNode("homes").getChildrenMap().size() < 2) {
                        Players.cfgNode.getNode("homes", "home", "world").setValue(p.getWorld().getName());
                        Players.cfgNode.getNode("homes", "home", "x").setValue(p.getLocation().getX());
                        Players.cfgNode.getNode("homes", "home", "y").setValue(p.getLocation().getY());
                        Players.cfgNode.getNode("homes", "home", "z").setValue(p.getLocation().getZ());
                        Players.save();

                        p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast den Home-Punkt: §e" + "home" + " §agesetzt"));
                    } else {
                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst nur maximal 5 Home-Punkte gleichzeitig haben"));
                    }
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.string(Text.of("home"))))))
                .executor(new SethomeCommand())
                .build();
    }
    
}
