package de.joshiworld.main.commands.island;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import me.lucko.luckperms.api.caching.PermissionData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

/**
 *
 * @author JoshiWorld
 */
public class IslandTeleportChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            String target = (String) args.getOne(Text.of("player")).get();
            
            if(pd.getPermissionValue("projectozone.island").asBoolean()) {
                if(Players.playerExist(target) && Players.loadPlayerNode(target).getNode("Island").getBoolean() == true) {
                    Location loc = new Location(
                            Sponge.getServer().getWorld(Players.loadPlayerNode(p.getName()).getNode("Island-Location", "world").getString()).get(), 
                            Players.loadPlayerNode(p.getName()).getNode("Island-Location", "x").getDouble(), 
                            Players.loadPlayerNode(p.getName()).getNode("Island-Location", "y").getDouble(), 
                            Players.loadPlayerNode(p.getName()).getNode("Island-Location", "z").getDouble());
                    
                    p.transferToWorld(Sponge.getServer().getWorld(Players.loadPlayerNode(p.getName()).getNode("Island-Location", "world").getString()).get());
                    p.setLocationSafely(loc);
                } else {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Spieler exestiert nicht oder besitzt keine Insel §i(Eingegebener Spieler: " + target + ")"));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("player")))))
                .executor(new IslandTeleportChild())
                .build();
    }
    
}
