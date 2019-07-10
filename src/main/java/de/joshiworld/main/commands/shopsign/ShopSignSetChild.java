package de.joshiworld.main.commands.shopsign;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.ShopSigns;
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
public class ShopSignSetChild implements CommandExecutor {
    
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.money").asBoolean()) {
                String type = (String) args.getOne(Text.of("shoptype")).get();
                String item_id = (String) args.getOne(Text.of("item_id")).get();
                
                ShopSigns.getNode().getNode("admin", type.toLowerCase(),
                        p.getWorld().getName() + ", " +
                        Ozone.shopsign.get(p).getBlockX() + ", " + 
                        Ozone.shopsign.get(p).getBlockY() + ", " +
                        Ozone.shopsign.get(p).getBlockZ()).setValue(item_id);
                ShopSigns.save();
                
                p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast das Adminshop-Sign geändert zu: §e§l" + item_id));
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("shoptype"))), 
                        GenericArguments.remainingJoinedStrings(Text.of("item_id"))))
                .executor(new ShopSignSetChild())
                .build();
    }
    
}
