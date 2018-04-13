package seedu.address.logic.commands;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import seedu.address.external.exceptions.CredentialsException;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author demitycho
/**
 * Logs in to user's Google Account.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": login to your Google account";

    public static final String MESSAGE_SUCCESS = "Google account logged in";

    private static final String MESSAGE_ALREADY_LOGGED_IN = "You are already logged in!";
    private static final String MESSAGE_AUTH_DENIED = "Google account's authorisation denied!";
    private static final String MESSAGE_LOGIN_SUCCESS = "Successfully logged in to Google accounts";
    private static final String MESSAGE_TIME_OUT = "Login timeout!";
    private static final String MESSAGE_UNKNOWN_FAILURE = "Unable to login(reason unknown)!";

    private static final Integer INTEGER_TIME_ALLOWED = 45;

    public LoginCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        final Duration timeout = Duration.ofSeconds(INTEGER_TIME_ALLOWED);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future<String> handler = executor.submit(new Callable() {
            @Override
            public String call() throws Exception {
                try {
                    model.loginGoogleAccount();
                    return MESSAGE_LOGIN_SUCCESS;
                } catch (IOException ioe) {
                    return MESSAGE_AUTH_DENIED;
                } catch (CredentialsException ce) {
                    return MESSAGE_ALREADY_LOGGED_IN;
                }
            }
        });

        try {
            String result = handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
            return new CommandResult(result);
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
