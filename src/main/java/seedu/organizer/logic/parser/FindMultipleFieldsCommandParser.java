package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.organizer.logic.commands.FindMultipleFieldsCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.task.predicates.MultipleFieldsContainsKeywordsPredicate;

//@@author guekling
/**
 * Parses input arguments and creates a new FindMultipleFieldsCommand object
 */
public class FindMultipleFieldsCommandParser implements Parser<FindMultipleFieldsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMultipleFieldsCommand
     * and returns an FindMultipleFieldsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMultipleFieldsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMultipleFieldsCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        return new FindMultipleFieldsCommand(new MultipleFieldsContainsKeywordsPredicate(Arrays.asList(keywords)));
    }
}
