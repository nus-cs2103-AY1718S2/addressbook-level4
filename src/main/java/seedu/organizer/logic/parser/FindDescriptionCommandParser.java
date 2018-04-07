package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.organizer.logic.commands.FindDescriptionCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.task.predicates.DescriptionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindDescriptionCommand object
 */
public class FindDescriptionCommandParser implements Parser<FindDescriptionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDescriptionCommand
     * and returns an FindDescriptionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDescriptionCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDescriptionCommand.MESSAGE_USAGE));
        }

        String[] descriptionKeywords = trimmedArgs.split("\\s+");

        return new FindDescriptionCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList(descriptionKeywords)));
    }
}
