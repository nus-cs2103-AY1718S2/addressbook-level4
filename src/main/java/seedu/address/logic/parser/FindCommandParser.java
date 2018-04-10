package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.activity.EventOnlyPredicate;
import seedu.address.model.activity.NameContainsKeywordsPredicate;
import seedu.address.model.activity.TaskOnlyPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final String TYPE_TASK = "task";
    private static final String TYPE_EVENT = "event";
    private static final int FIRST_INDEX = 0;
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        if (nameKeywords[FIRST_INDEX].equalsIgnoreCase(TYPE_TASK) && nameKeywords.length >= 2) {
            List<String> keywords = new ArrayList<String>(Arrays.asList(nameKeywords));
            keywords.remove(FIRST_INDEX);
            return new FindCommand(
                    new NameContainsKeywordsPredicate(keywords).or(new EventOnlyPredicate()));
        }
        if (nameKeywords[FIRST_INDEX].equalsIgnoreCase(TYPE_EVENT) && nameKeywords.length >= 2) {
            List<String> keywords = new ArrayList<String>(Arrays.asList(nameKeywords));
            keywords.remove(FIRST_INDEX);
            return new FindCommand(
                    new NameContainsKeywordsPredicate(keywords).or(new TaskOnlyPredicate()));
        }
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
