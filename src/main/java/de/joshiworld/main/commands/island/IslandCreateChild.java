package de.joshiworld.main.commands.island;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Players;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.RepresentedItemData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class IslandCreateChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            if(p.getWorld().getName().equalsIgnoreCase("skyblock")) {
                if(Players.loadPlayerNode(p.getName()).getNode("Island").getBoolean() == false || Players.loadPlayerNode(p.getName()).getNode("Island").getValue() == null) {
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "botania-skyblock-spread " + p.getName() + " 10000");

                    Players.cfgNode.getNode("Island").setValue(true);

                    Players.cfgNode.getNode("Island-Location", "world").setValue(p.getWorld().getName());
                    Players.cfgNode.getNode("Island-Location", "x").setValue(p.getLocation().getX());
                    Players.cfgNode.getNode("Island-Location", "y").setValue(p.getLocation().getY());
                    Players.cfgNode.getNode("Island-Location", "z").setValue(p.getLocation().getZ());

                    Players.save();
                    
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear " + p.getName());
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "inventory clear --confirm");
                    
                    //RepresentedItemData data = Sponge.getGame().getDataManager().getManipulatorBuilder(RepresentedItemData.class).get().create();
                    
                    p.getInventory().offer(ItemStack.of(Sponge.getGame().getRegistry().getType(ItemType.class, "opencomputers:tool").get(), 1));
                    p.getInventory().offer(ItemStack.of(Sponge.getGame().getRegistry().getType(ItemType.class, "bqt:mpad").get(), 1));
                    p.getInventory().offer(ItemStack.of(Sponge.getGame().getRegistry().getType(ItemType.class, "everlastingabilities:ability_bottle").get(), 1));
                    p.getInventory().offer(ItemStack.of(Sponge.getGame().getRegistry().getType(ItemType.class, "botania:lexicon").get(), 1));

                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDeine Insel wurde erstellt"));
                } else {
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu besitzt bereits eine Insel"));
                }
            } else {
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu musst in der Skywelt sein! /skyblock"));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .executor(new IslandCreateChild())
                .build();
    }
    
}
