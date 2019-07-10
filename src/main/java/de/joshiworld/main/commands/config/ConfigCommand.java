package de.joshiworld.main.commands.config;

import de.joshiworld.main.Ozone;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class ConfigCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of(Ozone.getPrefix() + " §c/config <reload>"));
        
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission("projectozone.config")
                .child(ConfigReloadChild.base(), "reload", "rl")
                .executor(new ConfigCommand())
                .build();
    }
    
}
