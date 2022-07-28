package eu.glowmc.knockbackffa.commands;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:23
 */

import eu.glowmc.knockbackffa.KnockBackService;
import eu.glowmc.knockbackffa.types.StatisticType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length == 0) {
                player.sendMessage(KnockBackService.getPrefix() + "Deine Statistiken§8:");
                player.sendMessage(KnockBackService.getPrefix() + "");
                player.sendMessage(KnockBackService.getPrefix() + "Kills§8: §a" + KnockBackService.getInstance().getStatisticManager().getStatistics(player, StatisticType.KILLS));
                player.sendMessage(KnockBackService.getPrefix() + "Deaths§8: §a" +KnockBackService.getInstance().getStatisticManager().getStatistics(player, StatisticType.DEATHS));
            }
        }
        return false;
    }
}
