package Util;

import Commands.Command;
import Data.GuildData;
import Data.Database.GuildLoader;
import Util.Exceptions.DushanbeException;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;


public class MessageProcessor {
    static Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    /**
     * Entry-point when processing any incoming message.
     * This method has to check whether a message from a bot or user is received.
     *
     * @param event The event that has to be handled.
     * @return A void Mono
     */
    public static Mono<Void> processEvent(MessageCreateEvent event) throws DushanbeException {
        if (event.getMessage().getAuthor().map(User::isBot).orElse(false)) {
            return Mono.empty();
        }

        GuildData guildData = retrieveOrCreateGuildData(event);

        Command command = CommandUtil.getCommand(event.getMessage().getContent(), guildData);

        if (command == null) {
            return Mono.empty();
        }

        if (!command.isExecutable(event, guildData)) {
            return Mono.empty();
        }

        if (DushanbeConfig.isDebugEnabled()) {
            logger.debug("Command: {}, Guild: {}, Input: {} ", command.getName(), guildData.getId(), event.getMessage());
        } else {
            logger.info("Command: {}, Guild: {}, Input: {} ", command.getName(), guildData.getId(), event.getMessage().getContent());
        }

        List<String> arguments = CommandUtil.getArguments(event.getMessage().getContent(), command);

        return command.execute(event, guildData, arguments);
    }

    public static GuildData retrieveOrCreateGuildData(MessageCreateEvent event) throws DushanbeException {
        String guildId = event.getGuildId()
                .map(Snowflake::asString)
                .orElseThrow(() -> new DushanbeException("Message did not contain guild ID"));

        GuildLoader loader = new GuildLoader();
        GuildData guildData = loader.retrieve(guildId);

        // TODO: Make this fail-proof, what if the database simply failed to retrieve the data due to an error but it does exist?
        if (guildData == null) {
            guildData = new GuildData(guildId);
            loader.insert(guildData);
        }

        return guildData;
    }

}

