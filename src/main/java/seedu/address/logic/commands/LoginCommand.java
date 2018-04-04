package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.model.account.Account;

/**
 * Logs into a social media platform using the user's account information.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs into a social media platform using "
            + "the user's name and password. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "johndoe "
            + PREFIX_PASSWORD + "jd9876";

    public static final String MESSAGE_SUCCESS = "Logged in account: %1$s";

    private final Account accountToLogin;

    public LoginCommand(Account account) {
        requireNonNull(account);
        accountToLogin = account;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.loginAccount(accountToLogin);
        return new CommandResult(String.format(MESSAGE_SUCCESS, accountToLogin));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && this.accountToLogin.equals(((LoginCommand) other).accountToLogin)); // state check
    }
}
