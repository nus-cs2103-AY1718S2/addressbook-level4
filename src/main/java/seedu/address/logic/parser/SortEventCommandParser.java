package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author x3tsunayh

/**
 * Parses input arguments and creates a new SortEventCommand object
 */
public class SortEventCommandParser implements Parser<SortEventCommand> {

    /**
     * Parses the given {@code String} of arguments
     * and returns a SortEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortEventCommand.MESSAGE_USAGE));
        }
        String upperCaseParameter = trimmedArgs.toUpperCase();
        return new SortEventCommand(upperCaseParameter);
    }
}
