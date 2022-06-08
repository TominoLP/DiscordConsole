package de.tomino.discordconsole.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

public class DiscordBot extends ListenerAdapter {

    public static ArrayList<String> QueueMes = new ArrayList<>();
    public static ArrayList<EmbedBuilder> QueueEmb = new ArrayList<>();
    private static JDA api;
    public static void main() throws LoginException {


        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.

        api = JDABuilder.createLight("OTY1NzA5NjYxNjYxNzEyMzk0.Yl3JNQ.CGXCt3yzRRjYySpYXsLaojXFy70", GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .setActivity(Activity.playing("with the console"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .build();

        api.addEventListener(new DiscordEvents());


    }


    public static void sendMessage(String message) {
        if (!(DiscordEvents.ready)) {
            QueueMes.add(message);
        } else {
            api.getGuildById(DiscordEvents.GuildId).getTextChannelById(DiscordEvents.channelId).sendMessage(message).queue();

        }

    }

    public static void sendEmbed(EmbedBuilder embed) {
        if (!(DiscordEvents.ready)) {
            QueueEmb.add(embed);
        } else {
            api.getGuildById(DiscordEvents.GuildId).getTextChannelById(DiscordEvents.channelId).sendMessageEmbeds(embed.build()).queue();
            embed.clear();
        }


    }


}
