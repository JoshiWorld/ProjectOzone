package de.joshiworld.main.commands.island;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

/**
 *
 * @author JoshiWorld
 */
public class IslandAcceptChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            Player target = (Player) args.getOne(Text.of("player")).get();
            
            if(Players.loadPlayerNode(p.getName()).getNode("MemberOf") != null) {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu bist bereits auf einer anderen Insel. Mache §o/island leave"));
            } else {
                if(!Ozone.islandInvite.containsKey(target.getName() + "_" + p.getName())) {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Spieler hat dir keine Einladung geschickt"));
                } else {
                    Ozone.islandInvite.get(target.getName() + "_" + p.getName()).cancel();
                    Ozone.islandInvite.remove(target.getName() + "_" + p.getName());
                    
                    if(Players.loadPlayerNode(p.getName()).getNode("Island").getBoolean() == false) {
                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear " + p.getName());
                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear --confirm");

                        ItemStack book = ItemStack.of(Sponge.getRegistry().getType(ItemType.class, "opencomputers:tool").get(), 1);

                        p.getInventory().offer(ItemStack.builder().fromContainer(book.toContainer().set(DataQuery.of("UnsafeDamage"), 4)).build());
                        p.getInventory().offer(ItemStack.of(Sponge.getGame().getRegistry().getType(ItemType.class, "bqt:mpad").get(), 1));
                        p.getInventory().offer(ItemStack.of(Sponge.getGame().getRegistry().getType(ItemType.class, "everlastingabilities:ability_bottle").get(), 1));
                        p.getInventory().offer(ItemStack.of(Sponge.getGame().getRegistry().getType(ItemType.class, "botania:lexicon").get(), 1));
                    }
                    
                    Players.loadPlayerNode(p.getName()).getNode("MemberOf").setValue(target.getName());
                    Players.save();
                    
                    Location loc = new Location(
                            Sponge.getServer().getWorld(Players.loadPlayerNode(target.getName()).getNode("Island-Location", "world").getString()).get(), 
                            Players.loadPlayerNode(target.getName()).getNode("Island-Location", "x").getDouble(), 
                            Players.loadPlayerNode(target.getName()).getNode("Island-Location", "y").getDouble(), 
                            Players.loadPlayerNode(target.getName()).getNode("Island-Location", "z").getDouble());

                    p.transferToWorld(Sponge.getServer().getWorld(Players.loadPlayerNode(target.getName()).getNode("Island-Location", "world").getString()).get());
                    p.setLocationSafely(loc);
                    
                    target.sendMessage(Text.of(Ozone.getPrefix() + " §e" + p.getName() + " §ahat deine Einladung angenommen"));
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast die Einladung von §e" + target.getName() + " §aangenommen"));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(new IslandAcceptChild())
                .build();
    }
    
}
