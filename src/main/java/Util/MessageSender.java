package Util;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public class MessageSender {
  public static Mono<Message> sendMessage(MessageChannel channel, String content) {
      return sendMessageSpec(channel, cons -> cons.setContent(content));
  }

  public static Mono<Message> sendMessage(MessageChannel channel, Consumer<EmbedCreateSpec> embed) {
    return sendMessageSpec(channel, cons -> cons.setEmbed(embed));
  }

  public static Mono<Message> sendMessage(MessageChannel channel, String content, Consumer<EmbedCreateSpec> embed) {
    return sendMessageSpec(channel, cons -> cons.setContent(content).setEmbed(embed));
  }

  public static Mono<Message> sendMessageSpec(MessageChannel channel, Consumer<MessageCreateSpec> spec) {
    return channel.createMessage(spec);
  }
}
