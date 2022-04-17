package de.tomino.discordconsole;

import com.google.gson.Gson;
import de.tomino.discordconsole.utils.DiscordBot;
import de.tomino.discordconsole.utils.DiscordEvents;
import de.tomino.discordconsole.utils.Lag;
import de.tomino.discordconsole.utils.Logger;
import net.dv8tion.jda.api.JDA;
import org.apache.logging.log4j.LogManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class DiscordConsole extends JavaPlugin {

    private static Plugin plugin;
    Gson gson = new Gson();
    private JDA jda;
    private Logger appender;



    @Override
    public void onEnable() {


        plugin = this;
        Bukkit.getPluginManager().registerEvents(new Logger.CLogger(), this);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);


        try {
            DiscordBot.main();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        // Plugin startup logic

        appender = new Logger(this, this.jda);
        try {
            final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
            logger.addAppender(appender);
        } catch (Exception e) {

        }


    }


    @Override
    public void onDisable() {
        plugin = null;
        final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
        appender.stop();
        logger.removeAppender(appender);
        // Plugin shutdown logic
    }
    public static Plugin getPlugin() {
        return plugin;
    }
}
