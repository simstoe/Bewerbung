package eu.glowmc.knockbackffa.commands;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:21
 */

import eu.glowmc.knockbackffa.KnockBackService;
import eu.glowmc.knockbackffa.types.HeightType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            if (commandSender.hasPermission("volverix.admin")) {
                Player player = (Player) commandSender;

                if (args.length == 0) {
                    player.sendMessage(KnockBackService.getPrefix() + "Setup-Commands");
                    player.sendMessage(KnockBackService.getPrefix() + "§a");
                    player.sendMessage(KnockBackService.getPrefix() + "§a/setup set <Spawn, HitHeight, DeathHeight, MapName> [MapName]");
                    player.sendMessage(KnockBackService.getPrefix() + "§a/setup info");
                    return true;
                }

                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length >= 2) {
                        if (args[1].equalsIgnoreCase("spawn")) {
                            if (!KnockBackService.getInstance().getLocationManager().getLocationHashMap().containsKey("spawn")) {
                                KnockBackService.getInstance().getLocationManager().setLocation("spawn", player.getLocation());
                                player.sendMessage(KnockBackService.getPrefix() + "Du hast den §aSpawn §7erfolgreich gesetzt!");
                                return true;
                            }

                            player.sendMessage(KnockBackService.getPrefix() + "Der §aSpawn §7wurde bereits gesetzt!");
                            return true;
                        }

                        if (args[1].equalsIgnoreCase("hitheight")) {
                            if (!KnockBackService.getInstance().getHeightManager().getHeightCache().containsKey(HeightType.HIT)) {
                                KnockBackService.getInstance().getHeightManager().setHeight(HeightType.HIT, player.getLocation().getBlockY());
                                player.sendMessage(KnockBackService.getPrefix() + "Du hast die §aHitHeight §7erfolgreich gesetzt!");
                                return true;
                            }

                            player.sendMessage(KnockBackService.getPrefix() + "Die §aHitHeight §7wurde bereits gesetzt!");
                            return true;
                        }

                        if (args[1].equalsIgnoreCase("deathheight")) {
                            if (!KnockBackService.getInstance().getHeightManager().getHeightCache().containsKey(HeightType.DEATH)) {
                                KnockBackService.getInstance().getHeightManager().setHeight(HeightType.DEATH, player.getLocation().getBlockY());
                                player.sendMessage(KnockBackService.getPrefix() + "Du hast die §aDeathHeight §7erfolgreich gesetzt!");
                                return true;
                            }

                            player.sendMessage(KnockBackService.getPrefix() + "Die §aDeathHeight §7wurde bereits gesetzt!");
                            return true;
                        }

                        if (args[1].equalsIgnoreCase("mapname")) {
                            if (args.length >= 3) {
                                if (KnockBackService.getInstance().getMapManager().getMapNameArrayList().size() == 0) {
                                    KnockBackService.getInstance().getMapManager().setMapName(args[2]);
                                    player.sendMessage(KnockBackService.getPrefix() + "Du hast den §aMapName §7erfolgreich gesetzt!");
                                    return true;
                                }

                                player.sendMessage("Der §aMapName §7wurde bereits gesetzt!");
                                return true;
                            }
                        }
                    }
                }

                if (args[0].equalsIgnoreCase("info")) {
                    player.sendMessage(KnockBackService.getPrefix() + "Infos");
                    player.sendMessage("§a");

                    if (KnockBackService.getInstance().getLocationManager().getLocationHashMap().get("spawn") != null)
                        player.sendMessage(KnockBackService.getPrefix() + "Spawn§8: §aGesetzt");
                    else player.sendMessage(KnockBackService.getPrefix() + "Spawn§8: §aNicht gesetzt");

                    if (KnockBackService.getInstance().getHeightManager().getHeightCache().get(HeightType.HIT) != null)
                        player.sendMessage(KnockBackService.getPrefix() + "HitHeight§8: §a" + KnockBackService.getInstance().getHeightManager().getHeightCache().get(HeightType.HIT));
                    else player.sendMessage(KnockBackService.getPrefix() + "HitHeight§8: §aNicht gesetzt");

                    if (KnockBackService.getInstance().getHeightManager().getHeightCache().get(HeightType.DEATH) != null)
                        player.sendMessage(KnockBackService.getPrefix() + "DeathHeight§8: §a" + KnockBackService.getInstance().getHeightManager().getHeightCache().get(HeightType.DEATH));
                    else player.sendMessage(KnockBackService.getPrefix() + "DeathHeight§8: §aNicht gesetzt");
                    return true;
                }

                player.sendMessage(KnockBackService.getPrefix() + "Setup-Commands");
                player.sendMessage(KnockBackService.getPrefix() + "§a");
                player.sendMessage(KnockBackService.getPrefix() + "§a/setup info");
                player.sendMessage(KnockBackService.getPrefix() + "§a/setup set <Spawn, HitHeight, DeathHeight>");
            }
        }
        return false;
    }
}
