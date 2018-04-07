package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.organizer.logic.commands.FindDeadlineCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.task.predicates.DeadlineContainsKeywordsPredicate;

//@@author guekling
/**
 * Parses input arguments and creates a new FindDeadlineCommand object
 */
public class FindDeadlineCommandParser implements Parser<FindDeadlineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDeadlineCommand
     * and returns an FindDeadlineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDeadlineCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeadlineCommand.MESSAGE_USAGE));
        }

        String[] deadlineKeywords = trimmedArgs.split("\\s+");

        return new FindDeadlineCommand(new DeadlineContainsKeywordsPredicate(Arrays.asList(deadlineKeywords)));
    }
}
