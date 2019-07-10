package de.joshiworld.main.commands.money;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Money;
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
public class MoneyCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast §c" + Money.getMoney(p.getName()) + " §aRubine"));
        } else {
            src.sendMessage(Text.of("§c/money <get | set | add | remove> <player> <amount>"));
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .child(MoneySetChild.base(), "set")
                .child(MoneyGetChild.base(), "get")
                .child(MoneyAddChild.base(), "add")
                .child(MoneyRemoveChild.base(), "remove")
                .executor(new MoneyCommand())
                .build();
    }
    
}
