package de.joshiworld.main.commands.pay;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Config;
import de.joshiworld.main.config.Money;
import de.joshiworld.main.config.Score;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

/**
 *
 * @author JoshiWorld
 */
public class PayCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            Player target = (Player) args.getOne(Text.of("player")).get();
            String amount = (String) args.getOne(Text.of("amount")).get();
            
            if(target.getName().equals(p.getName())) {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst dir selber nichts überweisen"));
            } else {
                if(Integer.valueOf(amount) > Money.getMoney(p.getName())) {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast nicht so viel Rubine"));
                } else {
                    Money.removeMoney(p.getName(), Integer.valueOf(amount));
                    Money.addMoney(target.getName(), Integer.valueOf(amount));

                    String prefixP = Config.cfgNode.getNode("Prefix", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
                    String prefixT = Config.cfgNode.getNode("Prefix", Ozone.getPermsApi().getUser(target.getName()).getPrimaryGroup()).getString();

                    Score.setScore(p);
                    Score.setScore(target);
                    
                    p.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(Ozone.getPrefix() + " §aDu hast " + prefixT + target.getName() + " §c" + amount + " §aRubine überwiesen"));
                    target.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(Ozone.getPrefix() + " " + prefixP + p.getName() + " §ahat dir §c" + amount + " §aRubine überwiesen"));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))), 
                        GenericArguments.integer(Text.of("amount"))))
                .executor(new PayCommand())
                .build();
    }
    
}
