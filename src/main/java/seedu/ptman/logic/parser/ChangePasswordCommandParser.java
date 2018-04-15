package seedu.ptman.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ptman.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import seedu.ptman.commons.core.index.Index;
import seedu.ptman.commons.exceptions.IllegalValueException;
import seedu.ptman.logic.commands.ChangePasswordCommand;
import seedu.ptman.logic.parser.exceptions.ParseException;
import seedu.ptman.model.Password;

//@@author koo1993
/**
 * Parses input arguments and creates a new ChangePasswordCommand object
 */
public class ChangePasswordCommandParser implements Parser<ChangePasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangePasswordCommand
     * and returns an ChangePasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangePasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_PASSWORD)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangePasswordCommand.MESSAGE_USAGE));
        }

        Index index;
        ArrayList<String> passwords;

        try {
            passwords = parsePasswords(argMultimap.getAllValues(PREFIX_PASSWORD));
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangePasswordCommand.MESSAGE_USAGE));
        }

        checkPasswordValidity(passwords.get(1));

        return new ChangePasswordCommand(index, passwords);
    }

    /**
     * Check validity of the password string given
     * @param passwords
     * @throws ParseException if it does not satisfy the password 8 length restriction.
     */
    private void checkPasswordValidity(String passwords) throws ParseException {
        if (!Password.isValidPassword(passwords)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code Collection<String> passwords} into a {@code Set<Password>}.
     */
    public static ArrayList<String> parsePasswords(Collection<String> passwords) throws ParseException {
        requireNonNull(passwords);
        final ArrayList<String> passwordSet = new ArrayList<>();
        for (String password : passwords) {
            passwordSet.add(password);
        }

        if (passwordSet.size() != 3) {
            throw new ParseException("Incorrect number of passwords provided");
        }

        return passwordSet;
    }

}
