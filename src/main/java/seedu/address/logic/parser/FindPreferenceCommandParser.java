package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindPreferenceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PreferencesContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindPreferenceCommand object
 */
public class FindPreferenceCommandParser implements Parser<FindPreferenceCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindPreferenceCommand
     * and returns an FindPreferenceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPreferenceCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPreferenceCommand.MESSAGE_USAGE));
        }

        String[] preferenceKeywords = trimmedArgs.split("\\s+");

        return new FindPreferenceCommand(new PreferencesContainKeywordsPredicate(Arrays.asList(preferenceKeywords)));
    }

}
