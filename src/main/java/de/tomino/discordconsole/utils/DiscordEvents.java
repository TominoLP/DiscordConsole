package de.tomino.discordconsole.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import de.tomino.discordconsole.DiscordConsole;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class DiscordEvents extends ListenerAdapter {

    private DiscordConsole plugin;
    private JDA jda;




    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getAuthor() == e.getJDA().getSelfUser()) {
            return;
        }
        if(e.getMessage().getContentRaw().startsWith("#")) {
            return;
        }
        if (e.getMessage().getContentRaw().startsWith("tps")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), e.getMessage().getContentRaw());
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("TPS");
            eb.setDescription("Current TPS: " + Lag.getTPS());
            e.getChannel().sendMessage((Message) eb.build()).queue();
            return;

        }
        if (e.getMessage().getContentRaw().startsWith("stop")) {
            e.getChannel().sendMessage("enter !Y tp stopp the server").queue();

            if  (e.getMessage().getContentRaw().startsWith("!Y")) {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), e.getMessage().getContentRaw());
            }
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), e.getMessage().getContentRaw());
            }
        }.runTask(DiscordConsole.getPlugin());


    }
}