package Util;

import Commands.Command;
import Commands.CommandLoader;
import Data.GuildData;
import Util.Exceptions.CommandException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandUtil {

    // Command should be at least of length 2, prefix + a char
    private static final int MIN_LENGTH = 2;

    public static Command getCommand(String message, GuildData data) {
        if (message == null || message.length() < MIN_LENGTH) {
            return null;
        }

        String commandName = message.split(" ")[0];
        boolean hasPrefix = false;

        if (data.getKey().equals(message.substring(0, data.getKey().length()))) {
            commandName = commandName.substring(1);
            hasPrefix = true;
        }

        Command command = CommandLoader.getInstance().getCommand(commandName);

        // Ensure that a prefix was present if command requires it or vice versa
        if (command == null || command.requiresKey() != hasPrefix) {
            return null;
        }

        return command;
    }

    public static List<String> getArguments(String message, Command command) throws CommandException {
        if (message == null || message.length() < MIN_LENGTH) {
            return null;
        }

        if (command.requiresKey()) {
            message = message.substring(1);
        }

        List<String> messageSplit = new ArrayList<String>(Arrays.asList(message.split(" ")));
        String originalName = messageSplit.remove(0); // Remove command name from arguments;

        // TODO: Perhaps if no arguments are given, return usage info embed?
        if (messageSplit.size() < command.getMinArgumentSize()) {
            throw new CommandException("%s requires %d arguments, and only %d provided", originalName, command.getMinArgumentSize(), messageSplit.size());
        }

        return messageSplit;

    }
}