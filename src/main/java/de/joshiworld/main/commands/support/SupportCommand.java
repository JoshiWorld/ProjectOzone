package de.joshiworld.main.commands.support;

import de.joshiworld.main.Ozone;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

/**
 *
 * @author JoshiWorld
 */
public class SupportCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            try {
                URL discord = new URL("https://discord.gg/4RkWEBQ");
                
                p.sendMessage(Text.builder(Ozone.getPrefix() + " §aSchilder im Textchannel vom Discord dein Problem: §ehttps://discord.gg/4RkWEBQ").onClick(TextActions.openUrl(discord)).build());
            } catch (MalformedURLException ex) {
                Logger.getLogger(SupportCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .executor(new SupportCommand())
                .build();
    }
    
}
