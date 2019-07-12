package de.joshiworld.main.commands.hilfe;

import de.joshiworld.main.Ozone;
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
public class HilfeCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            p.sendMessage(Text.of("§a/is create §8Insel erstellen"));
            p.sendMessage(Text.of("§a/is §8Zur Insel teleportieren"));
            p.sendMessage(Text.of("§a/home <home> §8Zum Home"));
            p.sendMessage(Text.of("§a/sethome <home> §8Home setzen"));
            p.sendMessage(Text.of("§a/delhome <home> §8Home löschen"));
            p.sendMessage(Text.of("§a/warps §8Alle Warps"));
            p.sendMessage(Text.of("§a/warp <warp> §8Alle Warps"));
            p.sendMessage(Text.of("§a/tpa <player> §8Teleportierungsanfrage"));
            p.sendMessage(Text.of("§a/support §8Support-Ticket"));
            p.sendMessage(Text.of("§a/bodenwelt §8Zur Bodenwelt"));
            p.sendMessage(Text.of("§a/skyblock §8Zur Skywelt"));
            p.sendMessage(Text.of("§a/bodenshop §8Zum Bodenshop"));
            p.sendMessage(Text.of("§a/skyshop §8Zum Skyshop"));
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .executor(new HilfeCommand())
                .build();
    }
    
}
