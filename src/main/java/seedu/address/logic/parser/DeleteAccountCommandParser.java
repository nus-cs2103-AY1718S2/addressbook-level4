package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.exceptions.DeleteAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.Account;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.Username;
import seedu.address.model.account.exceptions.AccountNotFoundException;
/**
 * Parses input arguments and creates a new DeleteAccountCommand object
 */
public class DeleteAccountCommandParser implements Parser<DeleteAccountCommand> {
    /**
     * Parses input arguments and creates a new DeleteAccountCommand object
     * <p>
     * /**
     * Parses the given {@code String} of arguments in the context of the DeleteAccountCommand
     * and returns an DeleteAccountCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public DeleteAccountCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAccountCommand.MESSAGE_USAGE));
        } else {
            return new DeleteAccountCommand(trimmedArgs);
        }
    }
}