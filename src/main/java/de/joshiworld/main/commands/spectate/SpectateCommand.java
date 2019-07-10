package de.joshiworld.main.commands.spectate;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Config;
import me.lucko.luckperms.api.caching.PermissionData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

/**
 *
 * @author JoshiWorld
 */
public class SpectateCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player p = (Player) src;
            
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));
            
            if(pd.getPermissionValue("projectozone.spectate").asBoolean()) {
                if(args.getOne(Text.of("player")).isPresent()) {
                    Player target = (Player) args.getOne(Text.of("player")).get();
                    
                    if(!Ozone.spectate.contains(p)) {
                        Ozone.spectate.add(p);
                        
                        PotionEffect invis = PotionEffect.builder().potionType(PotionEffectTypes.INVISIBILITY).duration(Integer.MAX_VALUE).amplifier(3).build();
                        PotionEffectData effect = p.getOrCreate(PotionEffectData.class).get().addElement(invis);
                        p.offer(effect);

                        p.transferToWorld(target.getWorld());
                        p.setLocation(target.getLocation());

                        String prefixe = Config.cfgNode.getNode("Prefixe", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
                        String prefix = Config.cfgNode.getNode("Prefix", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();

                        Sponge.getServer().getBroadcastChannel().send(TextSerializers.FORMATTING_CODE.deserialize("&c« " + prefixe + " " + prefix + p.getName() + " &c«"));
                    } else {
                        Ozone.spectate.remove(p);
                        
                        PotionEffect invis = PotionEffect.builder().potionType(PotionEffectTypes.INVISIBILITY).duration(Integer.MAX_VALUE).amplifier(3).build();
                        PotionEffectData effect = p.getOrCreate(PotionEffectData.class).get().remove(invis);
                        p.offer(effect);
                        
                        String prefixe = Config.cfgNode.getNode("Prefixe", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
                        String prefix = Config.cfgNode.getNode("Prefix", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
                        
                        Sponge.getServer().getBroadcastChannel().send(TextSerializers.FORMATTING_CODE.deserialize("&a» " + prefixe + " " + prefix + p.getName() + " &a»"));
                    }
                } else {
                    if(!Ozone.spectate.contains(p)) {
                        Ozone.spectate.add(p);
                        
                        PotionEffect invis = PotionEffect.builder().potionType(PotionEffectTypes.INVISIBILITY).duration(Integer.MAX_VALUE).amplifier(3).build();
                        PotionEffectData effect = p.getOrCreate(PotionEffectData.class).get().addElement(invis);
                        p.offer(effect);

                        String prefixe = Config.cfgNode.getNode("Prefixe", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
                        String prefix = Config.cfgNode.getNode("Prefix", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();

                        Sponge.getServer().getBroadcastChannel().send(TextSerializers.FORMATTING_CODE.deserialize("&c« " + prefixe + " " + prefix + p.getName() + " &c«"));
                    } else {
                        Ozone.spectate.remove(p);
                        
                        PotionEffect invis = PotionEffect.builder().potionType(PotionEffectTypes.INVISIBILITY).duration(Integer.MAX_VALUE).amplifier(3).build();
                        PotionEffectData effect = p.getOrCreate(PotionEffectData.class).get().remove(invis);
                        p.offer(effect);
                        
                        String prefixe = Config.cfgNode.getNode("Prefixe", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
                        String prefix = Config.cfgNode.getNode("Prefix", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
                        
                        Sponge.getServer().getBroadcastChannel().send(TextSerializers.FORMATTING_CODE.deserialize("&a» " + prefixe + " " + prefix + p.getName() + " &a»"));
                    }
                }
            }
        }
        return CommandResult.success();
    }
    
    public static CommandSpec build() {
        return CommandSpec.builder()
                .arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.optional(GenericArguments.player(Text.of("player"))))))
                .executor(new SpectateCommand())
                .build();
    }
    
}
