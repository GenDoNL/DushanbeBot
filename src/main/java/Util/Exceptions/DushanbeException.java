package Util.Exceptions;

/**
 * Exception used for internal errors which should not be exposed to the users of the bot
 */
public class DushanbeException extends Exception {
    public DushanbeException(String message) {
      super(message);
    }

    public DushanbeException(String message, Object... input) {
        this(String.format(message, input));
    }

    public DushanbeException(Exception e) {
      super(e);
    }
}
