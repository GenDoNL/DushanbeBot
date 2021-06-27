package Commands;

import Data.GuildData;
import Util.Exceptions.DushanbeException;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public abstract class Command {
    private final String name;
    private final String description;
    private final String usage;
    private final int permission; // TODO: change this to actual permission used in discord4j
    private final Set<String> alias;

    private final int minArgumentSize; // Default: 0
    private final boolean requiresKey; // Default: true

    public Command(String name, String description, String usage, int permission, Set<String> alias) {
        this(name, description, usage, permission, alias, 0, true);
    }

    public Command(String name, String description, String usage, int permission, Set<String> alias, boolean requiresKey) {
        this(name, description, usage, permission, alias, 0, requiresKey);

    }

    public Command(String name, String description, String usage, int permission, Set<String> alias, int minArguments) {
        this(name, description, usage, permission, alias, minArguments, true);
    }

    public Command(String name, String description, String usage, int permission, Set<String> alias, int minArgumentSize, boolean requiresKey) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.alias = alias;
        this.minArgumentSize = minArgumentSize;
        this.requiresKey = requiresKey;
    }

    public abstract Mono<Void> execute(MessageCreateEvent event, GuildData data, List<String> arguments) throws DushanbeException;

    public boolean isExecutable(MessageCreateEvent event, GuildData data) {
        // This is now handled in CommandUtil.getCommand, but we might keep this for now to be defensive.
        if (requiresKey) {
            if (!event.getMessage().getContent().startsWith(data.getKey())) {
                return false;
            }
        }

        // TODO: Add permission checking here.

        return true;
    }

    public String getUsage() {
        return usage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPermission() {
        return permission;
    }

    public Set<String> getAlias() {
        return alias;
    }

    public boolean requiresKey() {
        return requiresKey;
    }

    public int getMinArgumentSize() {
        return minArgumentSize;
    }
}
