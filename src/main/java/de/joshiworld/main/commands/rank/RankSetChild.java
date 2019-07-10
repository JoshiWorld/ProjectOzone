package de.joshiworld.main.commands.rank;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Score;
import java.util.concurrent.TimeUnit;
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
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

/**
 *
 * @author JoshiWorld
 */
public class RankSetChild implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            Player target = (Player) args.getOne(Text.of("player")).get();
            String rank = (String) args.getOne(Text.of("rank")).get();
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.rank").asBoolean()) {
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + target.getName() + " parent set " + rank.toLowerCase());
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + target.getName() + " " + rank.toLowerCase());
                
                Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                    Score.setScore(all);
                });
                
                Task.builder().execute(() -> {
                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
                }).delay(250, TimeUnit.MILLISECONDS).name(target.getName() + "_rank_" + rank).submit(Ozone.getPlugin());
                
                p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast §e" + target.getName() + " §ain die Gruppe: §e" + rank.toLowerCase() + " §aversetzt"));
            }
        } else {
            Player target = (Player) args.getOne(Text.of("player")).get();
            String rank = (String) args.getOne(Text.of("rank")).get();
            
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "lp user " + target.getName() + " parent set " + rank.toLowerCase());
            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "ranks set " + target.getName() + " " + rank.toLowerCase());

            Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                Score.setScore(all);
            });
            
            Task.builder().execute(() -> {
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), "update");
            }).delay(250, TimeUnit.MILLISECONDS).name(target.getName() + "_rank_" + rank).submit(Ozone.getPlugin());

            src.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast §e" + target.getName() + " §ain die Gruppe: §e" + rank.toLowerCase() + " §aversetzt"));
        }
        return CommandResult.success();
    }
    
    public static CommandSpec base() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))), 
                        GenericArguments.string(Text.of("rank"))))
                .executor(new RankSetChild())
                .build();
    }
    
}
