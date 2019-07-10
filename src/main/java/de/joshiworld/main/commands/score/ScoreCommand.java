package de.joshiworld.main.commands.score;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Score;
import java.util.HashMap;
import java.util.Map;
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
public class ScoreCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.score").asBoolean()) {
                String arg = (String) args.getOne(Text.of("type")).get();
                
                if(arg.equalsIgnoreCase("update")) {
                    Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                        Score.updateScore(all);
                    });
                    
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §aScoreboard updated"));
                } else if(arg.equalsIgnoreCase("set")) {
                    Sponge.getServer().getOnlinePlayers().stream().forEach(all -> {
                        Score.setScore(all);
                    });
                    
                    p.sendMessage(Text.of(Ozone.getPrefix() + " §aScoreboard set"));
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        Map<String, Object> choices = new HashMap<>();
        choices.put("update", "update");
        choices.put("set", "set");
        
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.choices(Text.of("type"), choices))))
                .executor(new ScoreCommand())
                .build();
    }
    
}
