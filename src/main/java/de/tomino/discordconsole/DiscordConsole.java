package de.tomino.discordconsole;

import com.google.gson.Gson;
import de.tomino.discordconsole.utils.DiscordBot;
import de.tomino.discordconsole.utils.EnableCommand;
import de.tomino.discordconsole.utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Objects;

public final class DiscordConsole extends JavaPlugin {

    public static boolean enabled = true;
    private static Plugin plugin;
    Gson gson = new Gson();
    private JDA jda;
    private Logger appender;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {


        Bukkit.getPluginManager().registerEvents(new Logger.CLogger(), this);
        Objects.requireNonNull(getCommand("enabledDC")).setExecutor(new EnableCommand());

        try {
            DiscordBot.main();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        // Plugin startup logic

        if (enabled) {
            appender = new Logger(this, this.jda);
            try {
                final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
                logger.addAppender(appender);
            } catch (Exception e) {
            }

        }

        plugin = this;
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Server");
        eb.setDescription("STARTED");
        eb.setColor(Color.GREEN);
        DiscordBot.sendEmbed(eb);


    }

    @Override
    public void onDisable() {
        plugin = null;
        final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
        appender.stop();
        logger.removeAppender(appender);
        // Plugin shutdown logic

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Server");
        eb.setDescription("STOPPED");
        eb.setColor(Color.RED);
        DiscordBot.sendEmbed(eb);

    }
}
