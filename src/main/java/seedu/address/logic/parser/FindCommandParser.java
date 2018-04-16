package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

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

        //@@author jill858
        if (!isValidPrefixFormat(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String targetPrefix = getPrefix(trimmedArgs);

        if (isEmptyKeywords(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = getKeywords(trimmedArgs);

        if (targetPrefix.equals(PREFIX_NAME.getPrefix())) {
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (targetPrefix.equals(PREFIX_TAG.getPrefix())) {
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (targetPrefix.equals(PREFIX_ADDRESS.getPrefix())) {
            return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else if (targetPrefix.equals(PREFIX_PHONE.getPrefix())) {
            return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    //@@author jill858
    /**
     * Check if valid prefix format is provided by the user
     * @param args command line input by the user
     * @return true if it is a valid prefix provided by the user
     */
    private static boolean isValidPrefixFormat(String args) {
        return args.length() > 2;
    }

    //@@author jill858
    /**
     * Extract the search type
     * @param args command line input
     * @return type of search field
     */
    private static String getPrefix(String args) {
        return args.substring(0, 2);
    }

    //@@author jill858
    /**
     * Check if there is keyword input provided by the user
     * @param keywords keywords input by the user
     * @return true is there is no keywords entered by the user
     */
    private static boolean isEmptyKeywords(String keywords) {
        return (keywords.substring(2)).length() == 0;
    }

    //@@author jill858
    /**
     * Extract keywords out from the command input
     * @param args command line input
     * @return list of keywords
     */
    private static String[] getKeywords(String args) {
        String removePrefixKeywords = args.substring(2);

        String trimKeywords = removePrefixKeywords.trim();

        return trimKeywords.split("\\s+");
    }



}
