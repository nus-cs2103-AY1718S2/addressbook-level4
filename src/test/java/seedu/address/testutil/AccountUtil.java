package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.model.account.Account;

//@@author shadow2496
/**
 * A utility class for Account.
 */
public class AccountUtil {

    /**
     * Returns the part of command string for the given {@code account}'s details.
     */
    public static String getAccountDetails(Account account) {
        return PREFIX_USERNAME + account.getUsername().value + " "
                + PREFIX_PASSWORD + account.getPassword().value;
    }
}
