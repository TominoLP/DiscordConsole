package de.tomino.discordconsole.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.channel.ChannelManager;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {

    private static JDA api;


    public static void main() throws LoginException
    {


        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.

        api = JDABuilder.createLight("OTU1MDkxNDczODUyOTIzOTk0.YjcoQA.kdpU7U051li075J8cJZxx7JMFLk", GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .setActivity(Activity.playing("with the console"))
                .build();
    }



    public static void sendMessage(String message)
    {
        api.getTextChannelById("955091724689088515").sendMessage(message).queue();
    }
}
