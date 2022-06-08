package de.tomino.discordconsole.utils;

import de.tomino.discordconsole.DiscordConsole;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class DiscordEvents extends ListenerAdapter {

    public static boolean ready = false;
    public static String channelId = "955091724689088515";
    public static String GuildId = "955091724689088512";
    public static boolean enabled = true;

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor() == e.getJDA().getSelfUser()) {
            return;
        }
        if (e.getMessage().getContentRaw().startsWith("!")) {
            channelId = e.getChannel().getId();
            GuildId = e.getGuild().getId();

        }
        if (e.getMessage().getContentRaw().startsWith("#")) {
            return;
        }

        if (enabled) {
                new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), e.getMessage().getContentRaw());
                }
            }.runTask(DiscordConsole.getPlugin());
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent e) {

        DiscordConsole plugin = DiscordConsole.getPlugin(DiscordConsole.class);
        plugin.getLogger().info("Discord ready!");
        ready = true;

        for (EmbedBuilder em : DiscordBot.QueueEmb) {
            DiscordBot.sendEmbed(em);
        }
        DiscordBot.QueueEmb.clear();

        for (String s : DiscordBot.QueueMes) {
            DiscordBot.sendMessage(s);
        }
        DiscordBot.QueueMes.clear();


    }

}