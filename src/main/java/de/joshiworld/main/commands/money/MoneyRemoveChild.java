package de.joshiworld.main.commands.money;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Config;
import de.joshiworld.main.config.Money;
import de.joshiworld.main.config.Score;
import me.lucko.luckperms.api.caching.PermissionData;
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
public class MoneyRemoveChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.money").asBoolean()) {
                Player target = (Player) args.getOne(Text.of("player")).get();
                int amount = (int) args.getOne(Text.of("amount")).get();
            
                Money.removeMoney(target.getName(), amount);
                
                String prefix = Config.cfgNode.getNode("Prefix", Ozone.getPermsApi().getUser(target.getName()).getPrimaryGroup()).getString();
                
                Score.setScore(target);
                
                p.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(Ozone.getPrefix() + " §aDu hast " + prefix + target.getName() + " §c" + amount + " §aRubine entfernt"));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))), 
                        GenericArguments.integer(Text.of("amount"))))
                .executor(new MoneyRemoveChild())
                .build();
    }
    
}
