package de.joshiworld.main.commands.warp;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Warps;
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

/**
 *
 * @author JoshiWorld
 */
public class DelwarpCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.money").asBoolean()) {
                String warp = (String) args.getOne(Text.of("warp")).get();
                
                if(Warps.getWarpExists(warp.toLowerCase())) {
                    Warps.getWarpFile(warp.toLowerCase()).delete();
                    
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast den Warp gelöscht: §e§l" + warp.toLowerCase()));
                } else {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDer Warp: §e§l" + warp.toLowerCase() + " §cexestiert nicht"));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("warp")))))
                .executor(new DelwarpCommand())
                .build();
    }
    
}
