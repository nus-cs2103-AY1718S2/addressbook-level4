package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddPatientQueueCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new AddPatientQueueCommand object
 */
public class AddPatientQueueCommandParser implements Parser<AddPatientQueueCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPatientQueueCommandParser
     * and returns an AddPatientQueueCommandParser object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddPatientQueueCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
           throw new ParseException(
                   String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientQueueCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s");

        return new AddPatientQueueCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
