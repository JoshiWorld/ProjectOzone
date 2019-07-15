package de.joshiworld.main.commands.island;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class IslandInviteChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            Player target = (Player) args.getOne(Text.of("player")).get();
            
            if(!Players.loadPlayerNode(target.getName()).getNode("MemberOf").getString().equalsIgnoreCase(p.getName())) {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Spieler ist bereits einer anderen Insel beigetreten"));
            } else if(Players.loadPlayerNode(target.getName()).getNode("MemberOf").getString().equalsIgnoreCase(p.getName())) {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Spieler ist bereits deiner Insel beigetreten"));
            } else {
                if(Ozone.islandInvite.containsKey(p.getName() + "_" + target.getName())) {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast diesem Spieler bereits eine Einladung geschickt"));
                } else {
                    target.sendMessage(Text.of(Ozone.getPrefix() + " §e" + p.getName() + " §ahat dich zu seiner Insel eingeladen."));
                    target.sendMessage(Text.of(Ozone.getPrefix() + " §aTippe §e§o/is accept " + p.getName() + " §aein, um die Einladung anzunehmen."));
                    target.sendMessage(Text.of(Ozone.getPrefix() + " §cTippe §e§o/is deny " + p.getName() + " §cein, um die Einladung abzulehnen."));
                    
                    Ozone.islandInvite.put(p.getName() + "_" + target.getName(), Task.builder().execute(() -> {
                        Ozone.islandInvite.remove(p.getName() + "_" + target.getName());
                        
                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDie Einladung an §e" + target.getName() + " §cist ausgelaufen"));
                        target.sendMessage(Text.of(Ozone.getPrefix() + " §cDie Einladung von §e" + p.getName() + " §cist ausgelaufen"));
                    }).delay(5, TimeUnit.MINUTES).name(p.getName() + "_" + target.getName() + "_isInvite").submit(Ozone.getPlugin()));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new IslandInviteChild())
                .build();
    }
    
}
