package de.joshiworld.main.commands.playtime;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Playtime;
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
public class PlaytimeCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDeine Spielzeit: " + Playtime.getPlaytime(p.getUniqueId().toString())));
        } else {
            src.sendMessage(Text.of(Ozone.getPrefix() + " §cNur für Spieler"));
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .executor(new PlaytimeCommand())
                .build();
    }
    
}
