import Util.DushanbeConfig;
import Util.Exceptions.CommandException;
import Util.Exceptions.DushanbeException;
import Util.MessageProcessor;
import Util.MessageSender;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.util.concurrent.TimeUnit;

public class DushanbeBot {

    private static final Logger log = Loggers.getLogger(DushanbeBot.class);

    /**
     * The main function of DushanbeBot.
     *
     * @param args The program argumants:
     *             [0] - The token of the bot to connect to Discord
     *             [..] -
     */
    public static void main(String[] args) throws InterruptedException {
        final String token = args[0];
        final DiscordClient client = DiscordClient.create(token);

        GatewayDiscordClient gateway = getGatewayDiscordClient(client);

        DushanbeConfig.setDebugEnabled(true); // TODO: Make an actual config file

        gateway.on(MessageCreateEvent.class)
                .flatMap(event -> {
                    try {
                        return MessageProcessor.processEvent(event);
                    } catch (DushanbeException e) {
                        return handleErrors(event, e);

                    }
                })
                .subscribe();

        gateway.onDisconnect().block();
    }

    private static GatewayDiscordClient getGatewayDiscordClient(DiscordClient client) throws InterruptedException {
        GatewayDiscordClient gateway = client.login().block();

        while (gateway == null) {
            log.error("Bot is unable to log-in, retrying in 30 seconds.");
            TimeUnit.SECONDS.sleep(30);
            gateway = client.login().block();
        }

        return gateway;
    }

    public static Mono<Void> handleErrors(MessageCreateEvent event, Throwable e) {
        // TODO: Clean this up
        if (e instanceof CommandException) {
            // Attempt to send the error message as output in the discord server.
            return Mono.just(event.getMessage())
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> MessageSender.sendMessage(channel, e.getMessage()))
                    .then();
        } else {
            log.error(e.getMessage(), e);
        }

        return Mono.empty();
    }
}
