package de.tomino.discordconsole.utils;


import de.tomino.discordconsole.DiscordConsole;
import net.dv8tion.jda.api.JDA;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Logger extends AbstractAppender {

    public static Calendar cal = Calendar.getInstance(Locale.GERMANY);
    public static String time = String.format("%02d:%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    private DiscordConsole plugin;
    private JDA jda;
    private String messages = "";



    public Logger(DiscordConsole plugin, JDA j) {
        super("MyAppender", null, null);
        this.plugin = plugin;
        this.jda = j;

        start();
    }


    @Override
    public void append(LogEvent event) {


        String message = event.getMessage().getFormattedMessage();
        message = "[" + time + " " + event.getLevel().toString() + "]: " + message + "\n";
        DiscordBot.sendMessage(message);

    }

    public static class CLogger implements Listener {
        @EventHandler
        public void onServerCommand(ServerCommandEvent event) {
            String command = event.getCommand();
            command = "[" + time + " COM]: Console: /" + command + "\n";
            DiscordBot.sendMessage(command);
        }

        @EventHandler
        public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
            String command = event.getMessage();
            command = "[" + time + " COM]: " + event.getPlayer().getName() + ": " + command + "\n";
            DiscordBot.sendMessage(command);
        }

        @EventHandler
        public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
            String message = event.getMessage();
            message = "[" + time + " MSG]: " + event.getPlayer().getName() + ": " + message + "\n";
            DiscordBot.sendMessage(message);

        }
    }

}

