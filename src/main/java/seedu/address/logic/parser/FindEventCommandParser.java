package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.TitleContainsKeywordsPredicate;

//@@author x3tsunayh

/**
 * Parses input arguments and creates a new FindEventCommand object
 */
public class FindEventCommandParser implements Parser<FindEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || trimmedArgs.length() < 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }
        if (trimmedArgs.substring(0, 3).equals("et/")) {
            TitleContainsKeywordsPredicate.setPredicateType("et");
        } else if (trimmedArgs.substring(0, 3).equals("ed/")) {
            TitleContainsKeywordsPredicate.setPredicateType("ed");
        } else if (trimmedArgs.length() > 3 && trimmedArgs.substring(0, 4).equals("edt/")) {
            TitleContainsKeywordsPredicate.setPredicateType("edt");
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.substring(0, 3).equals("edt")) {
            trimmedArgs = trimmedArgs.substring(4).trim();
        } else {
            trimmedArgs = trimmedArgs.substring(3).trim();
        }

        if (trimmedArgs.equals("")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        String[] titleKeywords = trimmedArgs.split("\\s+");

        return new FindEventCommand(new TitleContainsKeywordsPredicate(Arrays.asList(titleKeywords)));
    }
}
