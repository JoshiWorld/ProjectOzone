package de.joshiworld.main.commands.island;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import java.io.File;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class IslandMembersChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            String msg = "";
            
            for(File members : Players.getPlayers()) {
                String target = members.getName().replaceAll(".conf", "");

                if(Players.loadPlayerNode(target).getNode("MemberOf").getString().equalsIgnoreCase(p.getName())) {
                    msg = msg + "§e" + target + "§7, ";
                }
            }
            
            if(!msg.isEmpty()) {
                msg = msg.substring(0, msg.length() - 2);

                p.sendMessage(Text.of(Ozone.getPrefix() + " §aMembers: " + msg));
            } else {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu besitzt keine Member"));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .executor(new IslandMembersChild())
                .build();
    }
    
}
