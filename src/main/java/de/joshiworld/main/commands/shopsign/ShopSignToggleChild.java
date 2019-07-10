package de.joshiworld.main.commands.shopsign;

import de.joshiworld.main.Ozone;
import me.lucko.luckperms.api.caching.PermissionData;
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
public class ShopSignToggleChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.money").asBoolean()) {
                if(Ozone.shopsign.containsKey(p)) {
                    Ozone.shopsign.remove(p);
                    
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §aShopsign-Modus: §cDeaktiviert"));
                } else {
                    Ozone.shopsign.put(p, p.getLocation());
                    
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §aShopsign-Modus: §aAktiviert"));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .executor(new ShopSignToggleChild())
                .build();
    }
    
}
