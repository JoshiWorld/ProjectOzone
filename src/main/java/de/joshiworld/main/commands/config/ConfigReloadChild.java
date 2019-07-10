package de.joshiworld.main.commands.config;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Config;
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
public class ConfigReloadChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Config.loadFolder();
        src.sendMessage(Text.of(Ozone.getPrefix() + " §aConfig reloadet!"));
        
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .permission("projectozone.config")
                .executor(new ConfigReloadChild())
                .build();
    }
    
}
