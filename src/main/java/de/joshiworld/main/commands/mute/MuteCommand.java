package de.joshiworld.main.commands.mute;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
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
public class MuteCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            Player target = (Player) args.getOne(Text.of("player")).get();
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.mute").asBoolean()) {
                if(Players.loadPlayerNode(target.getName()).getNode("Muted").getBoolean() == true) {
                    Players.loadPlayerNode(target.getName()).getNode("Muted").setValue(false);
                    Players.save();

                    p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast §e" + target.getName() + " §aentstummt"));
                } else {
                    Players.loadPlayerNode(target.getName()).getNode("Muted").setValue(true);
                    Players.save();

                    p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast §e" + target.getName() + " §agestummt"));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new MuteCommand())
                .build();
    }
    
}
