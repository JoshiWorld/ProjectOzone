package de.joshiworld.main.commands.warp;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Warps;
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
public class WarpsCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            String msg = "";
            
            for(File warp : Warps.getWarps()) {
                msg = msg + "§a" +  warp.getName().replaceAll(".conf", "") + "§7, ";
            }
            
            msg = msg.substring(0, msg.length() - 2);
            
            p.sendMessage(Text.of(Ozone.getPrefix() + " " + msg));
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .executor(new WarpsCommand())
                .build();
    }
    
}
