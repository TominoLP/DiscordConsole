package de.tomino.discordconsole.utils;

import de.tomino.discordconsole.DiscordConsole;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnableCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("discordconsole.enable")) {

                if (DiscordConsole.enabled) {
                    DiscordConsole.enabled = false;
                } else {
                    DiscordConsole.enabled = true;
                }


            }
        }

        return false;
    }
}
