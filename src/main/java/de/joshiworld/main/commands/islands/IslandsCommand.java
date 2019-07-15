package de.joshiworld.main.commands.islands;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import java.io.File;
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
public class IslandsCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.players").asBoolean()) {
                String msg = "";
            
                for(File players : Players.getPlayers()) {
                    String target = players.getName().replaceAll(".conf", "");
                    
                    if(Players.loadPlayerNode(target).getNode("Island").getBoolean() == true) {
                        msg = msg + "§a" + target + "§7, ";
                    }
                }

                msg = msg.substring(0, msg.length() - 2);

                p.sendMessage(Text.of(Ozone.getPrefix() + " " + msg));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .executor(new IslandsCommand())
                .build();
    }
    
}
