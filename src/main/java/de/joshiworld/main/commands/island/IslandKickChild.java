package de.joshiworld.main.commands.island;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import java.io.File;
import java.util.List;
import org.spongepowered.api.Sponge;
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
public class IslandKickChild implements CommandExecutor {
    
    List<String> members;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            String target = (String) args.getOne(Text.of("player")).get();
            
            for(File member : Players.getPlayers()) {
                if(Players.loadPlayerNode(member.getName()).getNode("MemberOf").getString().equalsIgnoreCase(p.getName())) {
                    members.add(member.getName().replaceAll(".conf", ""));
                }
            }
            
            if(!members.contains(target)) {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Spieler befindet sich nicht auf deiner Insel. Mache §e§o/island members §cum deine Mitglieder zu sehen"));
            } else {
                Players.loadPlayerNode(target).getNode("MemberOf").setValue(null);
                Players.save();
                
                Sponge.getServer().getPlayer(target).ifPresent(tp -> {
                    tp.sendMessage(Text.of(Ozone.getPrefix() + " §cDu wurdest aus deiner Insel vom Leader gekickt"));
                });
                
                p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast §e" + target + " §cgekickt"));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("player")))))
                .executor(new IslandKickChild())
                .build();
    }
    
}
