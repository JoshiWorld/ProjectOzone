package de.joshiworld.main.commands.tpw;

import de.joshiworld.main.Ozone;
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
public class TpwCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            Player player = (Player) args.getOne(Text.of("player")).get();
            Player target = (Player) args.getOne(Text.of("target")).get();
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.tpw").asBoolean()) {
                if(player.getName().equals(target.getName())) {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst diesen Spieler nicht zu sich selbst teleportieren"));
                } else {
                    player.transferToWorld(target.getWorld());
                    player.setLocation(target.getLocation());

                    p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast §e" + player.getName() + " §azu §e" + target.getName() + " §ain die Welt teleportiert"));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                        GenericArguments.onlyOne(GenericArguments.player(Text.of("target")))))
                .executor(new TpwCommand())
                .build();
    }
    
}
