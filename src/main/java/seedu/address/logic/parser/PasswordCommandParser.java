package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SET;

import java.util.stream.Stream;

import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author limzk1994

/**
 * Parses the inputs and create a PasswordCommand object
 */
public class PasswordCommandParser implements Parser<PasswordCommand> {


    @Override
    public PasswordCommand parse(String args) throws ParseException {

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SET, PREFIX_CHANGE, PREFIX_REMOVE);

        if (arePrefixesPresent(argumentMultimap, PREFIX_SET)) {
            return new PasswordCommand(new PasswordCommand.setPassword(argumentMultimap.getValue(PREFIX_SET).get()));
        } else if (arePrefixesPresent(argumentMultimap, PREFIX_REMOVE)) {
            return new PasswordCommand(new PasswordCommand.clearPassword(
                    argumentMultimap.getValue(PREFIX_REMOVE).get()));
        } else if (arePrefixesPresent(argumentMultimap, PREFIX_CHANGE)) {
            final String newPassword = argumentMultimap.getValue(PREFIX_CHANGE).get();
            requireNonNull(newPassword);
            if (newPassword.length() == 0) {
                throw new ParseException("Password cannot be blank!");
            }
            return new PasswordCommand(new PasswordCommand.changePassword(newPassword));

        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.MESSAGE_USAGE));
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
