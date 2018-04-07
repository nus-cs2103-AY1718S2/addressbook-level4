package seedu.address.logic.parser;

import java.util.Arrays;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.FindAndDeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.NameContainsKeywordsPredicate;

//@@author chweeee
/**
 * Parses input arguments and creates a new FindAndDeleteCommand object
 */
public class FindAndDeleteCommandParser implements Parser<FindAndDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindAndDeleteCommand
     * and returns an FindAndDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAndDeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindAndDeleteCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
//@@author
