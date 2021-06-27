package Commands;

import Constants.ColorConstants;
import Data.GuildData;
import Util.MessageSender;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public class PingCommand extends Command {

  public PingCommand() {
    super("Ping", "Shows the ping of DushanbeBot to Discord.", "!ping", 0, Set.of("ping"));
  }

  public Mono<Void> execute(MessageCreateEvent event, GuildData data, List<String> arguments) {
    final long start = System.currentTimeMillis();
    return Mono.just(event.getMessage())
            .flatMap(Message::getChannel)

            // Send initial ping message
            .flatMap(channel -> MessageSender.sendMessage(channel, embed ->
                    embed.setDescription("\uD83C\uDFD3 **Pong**")
                         .addField("Bot", "pinging...", true)
                         .setColor(ColorConstants.INFO)
                    ))

            // Update the ping message to contain the actual ping in ms
            .flatMap(message -> message.edit(msg -> msg.setEmbed(embed ->
                    embed.setDescription("\uD83C\uDFD3 **Pong**")
                         .addField("Bot", (System.currentTimeMillis() - start) + "ms", true)
                         .setColor(ColorConstants.INFO))))
            .then();
  }
}
