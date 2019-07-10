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
public class SetwarpCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.warp").asBoolean()) {
                String warp = (String) args.getOne(Text.of("warp")).get();
            
                Warps.getNode(warp.toLowerCase()).getNode("world").setValue(p.getWorld().getName());
                Warps.getNode(warp.toLowerCase()).getNode("x").setValue(p.getLocation().getX());
                Warps.getNode(warp.toLowerCase()).getNode("y").setValue(p.getLocation().getY());
                Warps.getNode(warp.toLowerCase()).getNode("z").setValue(p.getLocation().getZ());
                
                Warps.save();

                p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast einen neuen Warp gesetzt: §e§l" + warp.toLowerCase()));
            } else {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast leider keine Berechtigung für diesen Befehl!"));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("warp")))))
                .executor(new SetwarpCommand())
                .build();
    }
    
}
