package de.joshiworld.main.listeners;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Config;
import de.joshiworld.main.config.Players;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

/**
 *
 * @author JoshiWorld
 */
public class PlayerChatListener {
    
    @Listener
    public void onChat(MessageChannelEvent.Chat e) {
        if(e.getSource() instanceof Player) {
            Player p = (Player) e.getSource();
            
            if(Players.loadPlayerNode(p.getName()).getNode("Muted").getBoolean() == true) {
                e.setCancelled(true);
                
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu bist gestummt. Melde dich bei einem Team-Mitglied, wenn der Stumm deiner Meinung nach unberechtigt ist"));
            } else {
                e.setCancelled(false);
                
                String msg = e.getRawMessage().toPlain();

                String prefixe = Config.cfgNode.getNode("Prefixe", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();
                String prefix = Config.cfgNode.getNode("Prefix", Ozone.getPermsApi().getUser(p.getName()).getPrimaryGroup()).getString();

                e.setMessage(TextSerializers.FORMATTING_CODE.deserialize(prefixe + " " + prefix + p.getName() + "&7: &f" + msg));
            }
        }
    }
    
}
