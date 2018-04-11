package seedu.address.logic.commands;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import seedu.address.logic.commands.exceptions.CommandException;

// @@author demitycho
/**
 * Logs in to user's Google Account.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": login to your Google account";

    public static final String MESSAGE_SUCCESS = "Google account logged in";

    private static final String MESSAGE_LOGIN_ATTEMPT = "Trying to login";
    private static final String MESSAGE_TIME_OUT = "Login timeout!";
    private static final String MESSAGE_UNKNOWN_FAILURE = "Unable to login!";
    private static final Integer INTEGER_TIME_ALLOWED = 45;

    public LoginCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        final Duration timeout = Duration.ofSeconds(INTEGER_TIME_ALLOWED);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future<String> handler = executor.submit(new Callable() {
            @Override
            public String call() throws Exception {
                model.loginGoogleAccount();
                return MESSAGE_LOGIN_ATTEMPT;
            }
        });

        try {
            handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (TimeoutException e) {
            handler.cancel(true);
            executor.shutdownNow();
            return new CommandResult(MESSAGE_TIME_OUT);
        } catch (Exception e) {
            e.printStackTrace();
            executor.shutdownNow();
            return new CommandResult(MESSAGE_UNKNOWN_FAILURE);
        }
    }
}
