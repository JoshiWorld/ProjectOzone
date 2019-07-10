package de.joshiworld.main.commands.update;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Config;
import de.joshiworld.main.config.Score;
import me.lucko.luckperms.api.caching.PermissionData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.tab.TabListEntry;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

/**
 *
 * @author JoshiWorld
 */
public class UpdateCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.update").asBoolean()) {
                Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                    /*Sponge.getServer().getOnlinePlayers().stream().forEach(sep -> {
                        TabListEntry entry = all.getTabList().getEntry(sep.getUniqueId()).get();

                        String prefixeSep = Config.getNode().getNode("Prefixe", Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup()).getString();
                        String prefixSep = Config.getNode().getNode("Prefix", Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup()).getString();

                        entry.setDisplayName(TextSerializers.FORMATTING_CODE.deserialize(prefixeSep + " " + prefixSep + sep.getName()));
                    });*/
                    
                    Score.setScore(all);
                });
            }
        } else {
            Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                Sponge.getServer().getOnlinePlayers().stream().forEach(sep -> {
                    TabListEntry entry = all.getTabList().getEntry(sep.getUniqueId()).get();
                    
                    String prefixeSep = Config.getNode().getNode("Prefixe", Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup()).getString();
                    String prefixSep = Config.getNode().getNode("Prefix", Ozone.getPermsApi().getUser(sep.getName()).getPrimaryGroup()).getString();
                    
                    entry.setDisplayName(TextSerializers.FORMATTING_CODE.deserialize(prefixeSep + " " + prefixSep + sep.getName()));
                });
                
                Score.setScore(all);
            });
                
            Sponge.getServer().getConsole().sendMessage(Text.of(Ozone.getPrefix() + " §aUpdate complete"));
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .executor(new UpdateCommand())
                .build();
    }
    
}
