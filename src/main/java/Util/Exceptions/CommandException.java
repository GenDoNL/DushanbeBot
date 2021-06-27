package Util.Exceptions;

/**
 * Exception used to provide output to the users of DushanbeBot
 */
public class CommandException extends DushanbeException {

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Object... args) {
        super(message, args);
    }
}
