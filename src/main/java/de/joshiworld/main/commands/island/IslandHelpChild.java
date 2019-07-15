package de.joshiworld.main.commands.island;

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
public class IslandHelpChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            p.sendMessage(Text.of("§a/island create §8Insel erstellen"));
            p.sendMessage(Text.of("§a/is §8Zur Insel teleportieren"));
            p.sendMessage(Text.of("§a/island invite <player> §8Spieler zur Insel einladen"));
            p.sendMessage(Text.of("§a/island accept <player> §8Einladung annehmen"));
            p.sendMessage(Text.of("§a/island deny <player> §8Einladung ablehnen"));
            p.sendMessage(Text.of("§a/island kick <player> §8Spieler kicken"));
            p.sendMessage(Text.of("§a/island leave §8Island verlassen"));
            p.sendMessage(Text.of("§a/island members §8Island-Members auflisten"));
            p.sendMessage(Text.of("§a/island setspawn §8Island-Spawn neu setzen"));
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .executor(new IslandHelpChild())
                .build();
    }
}
