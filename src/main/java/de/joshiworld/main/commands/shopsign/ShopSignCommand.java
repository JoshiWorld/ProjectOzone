package de.joshiworld.main.commands.shopsign;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;

/**
 *
 * @author JoshiWorld
 */
public class ShopSignCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        return CommandResult.empty();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .child(ShopSignToggleChild.base(), "toggle")
                .child(ShopSignSetChild.base(), "set")
                .executor(new ShopSignCommand())
                .build();
    }
    
}
