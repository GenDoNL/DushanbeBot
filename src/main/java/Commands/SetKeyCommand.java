package Commands;

import Data.GuildData;
import Data.Loaders.GuildLoader;
import Util.Exceptions.CommandException;
import Util.MessageSender;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public class SetKeyCommand extends Command {
    private static final String NAME = "Setkey";
    private static final String DESCRIPTION = "Update the key, the start of a command, for this server. (Default: !).";
    private static final String USAGE = "!setkey";
    private static final int PERMISSION = 0;
    private static final int MIN_ARGUMENT_SIZE = 1;
    private static final Set<String> ALIAS = Set.of("setkey", "setprefix");

    public SetKeyCommand() {
        super(NAME, DESCRIPTION, USAGE, PERMISSION, ALIAS, MIN_ARGUMENT_SIZE);
    }

    public Mono<Void> execute(MessageCreateEvent event, GuildData data, List<String> arguments) throws CommandException {
        String keyCandidate = arguments.get(0);

        if (keyCandidate.length() != 1) {
            throw new CommandException("Prefix should be of length 1.");
        }

        GuildLoader loader = new GuildLoader();
        loader.updateKey(data, keyCandidate);

        String message = String.format("Prefix has been updated to `%s` for this server.", data.getKey());

        return Mono.just(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> MessageSender.sendMessage(channel, message))
                .then();
    }
}
