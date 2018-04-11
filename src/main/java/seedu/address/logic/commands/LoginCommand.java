package seedu.address.logic.commands;

import seedu.address.external.exceptions.CredentialsException;
import seedu.address.logic.commands.exceptions.CommandException;

import java.time.Duration;
import java.util.concurrent.*;

/**
 * Displays the user's schedule.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": login to your Google account";

    public static final String MESSAGE_SUCCESS = "Google account logged in";

    public LoginCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        final Duration timeout = Duration.ofSeconds(10);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future<String> handler = executor.submit(new Callable() {
            @Override
            public String call() throws Exception {
                model.loginGoogleAccount();
                return "lol";
            }
        });

        try {
            handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (TimeoutException e) {
            handler.cancel(true);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        executor.shutdownNow();
//        try {
//            model.loginGoogleAccount();
//            return new CommandResult(MESSAGE_SUCCESS);
//        } catch (CredentialsException ce) {
//            throw new CommandException(ce.getMessage());
//        }
        return new CommandResult("FAIL");
    }
}
