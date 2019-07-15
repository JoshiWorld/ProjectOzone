package de.joshiworld.main.commands;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.commands.config.ConfigCommand;
import de.joshiworld.main.commands.hilfe.HilfeCommand;
import de.joshiworld.main.commands.island.IslandCommand;
import de.joshiworld.main.commands.money.MoneyCommand;
import de.joshiworld.main.commands.mute.MuteCommand;
import de.joshiworld.main.commands.pay.PayCommand;
import de.joshiworld.main.commands.permissions.SetPermsCommand;
import de.joshiworld.main.commands.players.PlayersCommand;
import de.joshiworld.main.commands.playtime.PlaytimeCommand;
import de.joshiworld.main.commands.rank.RankCommand;
import de.joshiworld.main.commands.score.ScoreCommand;
import de.joshiworld.main.commands.shopsign.ShopSignCommand;
import de.joshiworld.main.commands.spectate.SpectateCommand;
import de.joshiworld.main.commands.support.SupportCommand;
import de.joshiworld.main.commands.tpw.TpwCommand;
import de.joshiworld.main.commands.update.UpdateCommand;
import de.joshiworld.main.commands.warp.BodennetherCommand;
import de.joshiworld.main.commands.warp.BodenshopCommand;
import de.joshiworld.main.commands.warp.BodenweltCommand;
import de.joshiworld.main.commands.warp.DelwarpCommand;
import de.joshiworld.main.commands.warp.SetwarpCommand;
import de.joshiworld.main.commands.warp.SkyblockCommand;
import de.joshiworld.main.commands.warp.SkynetherCommand;
import de.joshiworld.main.commands.warp.SkyshopCommand;
import de.joshiworld.main.commands.warp.WarpCommand;
import de.joshiworld.main.commands.warp.WarpsCommand;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;

/**
 *
 * @author JoshiWorld
 */
public class CommandInit {
    
    private static CommandManager getCommandManager() {
        return Sponge.getCommandManager();
    }
    
    public static void init() {
        commands();
    }
    
    public static void reloadInit() {
        getCommandManager().getOwnedBy(Ozone.getPlugin()).forEach(c -> {
            getCommandManager().removeMapping(c);
        });
        commands();
    }
    
    private static void commands() {
        getCommandManager().register(Ozone.getPlugin(), ConfigCommand.build(), "config");
        getCommandManager().register(Ozone.getPlugin(), MoneyCommand.build(), "money", "rubine");
        getCommandManager().register(Ozone.getPlugin(), PayCommand.build(), "pay");
        getCommandManager().register(Ozone.getPlugin(), ShopSignCommand.build(), "shopsign", "ss");
        getCommandManager().register(Ozone.getPlugin(), WarpCommand.build(), "warp");
        getCommandManager().register(Ozone.getPlugin(), SetwarpCommand.build(), "setwarp");
        getCommandManager().register(Ozone.getPlugin(), DelwarpCommand.build(), "delwarp");
        getCommandManager().register(Ozone.getPlugin(), WarpsCommand.build(), "warps");
        getCommandManager().register(Ozone.getPlugin(), UpdateCommand.build(), "update");
        getCommandManager().register(Ozone.getPlugin(), BodenshopCommand.build(), "bodenshop");
        getCommandManager().register(Ozone.getPlugin(), SkyshopCommand.build(), "skyshop");
        getCommandManager().register(Ozone.getPlugin(), SkyblockCommand.build(), "skyblock");
        getCommandManager().register(Ozone.getPlugin(), BodenweltCommand.build(), "bodenwelt");
        getCommandManager().register(Ozone.getPlugin(), BodennetherCommand.build(), "bodennether");
        getCommandManager().register(Ozone.getPlugin(), SkynetherCommand.build(), "skynether");
        getCommandManager().register(Ozone.getPlugin(), MuteCommand.build(), "mute");
        getCommandManager().register(Ozone.getPlugin(), ScoreCommand.build(), "score");
        getCommandManager().register(Ozone.getPlugin(), IslandCommand.build(), "island", "is");
        getCommandManager().register(Ozone.getPlugin(), TpwCommand.build(), "tpw");
        getCommandManager().register(Ozone.getPlugin(), PlaytimeCommand.build(), "playtime", "spielzeit");
        //getCommandManager().register(Ozone.getPlugin(), SethomeCommand.build(), "sethome");
        //getCommandManager().register(Ozone.getPlugin(), HomeCommand.build(), "home");
        //getCommandManager().register(Ozone.getPlugin(), DelhomeCommand.build(), "delhome");
        //getCommandManager().register(Ozone.getPlugin(), HomesCommand.build(), "homes");
        getCommandManager().register(Ozone.getPlugin(), RankCommand.build(), "rank");
        getCommandManager().register(Ozone.getPlugin(), SpectateCommand.build(), "spectate", "spec");
        getCommandManager().register(Ozone.getPlugin(), HilfeCommand.build(), "hilfe");
        getCommandManager().register(Ozone.getPlugin(), SupportCommand.build(), "support", "supp");
        getCommandManager().register(Ozone.getPlugin(), SetPermsCommand.build(), "setperms");
        getCommandManager().register(Ozone.getPlugin(), PlayersCommand.build(), "players");
    }
    
}
