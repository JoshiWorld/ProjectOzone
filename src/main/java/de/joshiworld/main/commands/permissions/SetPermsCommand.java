package de.joshiworld.main.commands.permissions;

import de.joshiworld.main.Ozone;
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

/**
 *
 * @author JoshiWorld
 */
public class SetPermsCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(!pd.getPermissionValue("projectozone.setperms").asBoolean()) {
                return CommandResult.empty();
            }
        }
        
        String permission = (String) args.getOne(Text.of("permission")).get();
        String mode = (String) args.getOne(Text.of("mode")).get();
        
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission viewer1 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission viewer2 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission viewer3 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission viewer4 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission viewer5 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission viewer6 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission viewer7 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission sub1 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission sub2 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission sub3 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission sub4 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission sub5 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission sub6 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission sub7 " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission vip " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission supp " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission editor " + permission.toLowerCase() + " " + mode.toLowerCase());
        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set_permission mod " + permission.toLowerCase() + " " + mode.toLowerCase());
        
        src.sendMessage(Text.of(Ozone.getPrefix() + " §aRanks set permission: §e" + permission.toLowerCase() + " §awith mode: §e" + mode.toLowerCase()));
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("permission"))), 
                        GenericArguments.string(Text.of("mode"))))
                .executor(new SetPermsCommand())
                .build();
    }
    
}
