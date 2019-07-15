package de.joshiworld.main.commands.island;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
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
public class IslandDenyChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            Player target = (Player) args.getOne(Text.of("player")).get();
            
            if(Players.loadPlayerNode(p.getName()).getNode("MemberOf").getValue() != null) {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu bist bereits auf einer anderen Insel. Mache §o/island leave"));
            } else {
                if(!Ozone.islandInvite.containsKey(target.getName() + "_" + p.getName())) {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Spieler hat dir keine Einladung geschickt"));
                } else {
                    Ozone.islandInvite.get(target.getName() + "_" + p.getName()).cancel();
                    Ozone.islandInvite.remove(target.getName() + "_" + p.getName());
                    
                    target.sendMessage(Text.of(Ozone.getPrefix() + " §e" + p.getName() + " §chat deine Einladung abgelehnt"));
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast die Einladung von §e" + target.getName() + " §cabgelehnt"));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new IslandDenyChild())
                .build();
    }
    
}
