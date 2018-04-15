//@@author nicholasangcx
package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.recipe.logic.commands.TagCommand;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.tag.TagContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    private static final String REGEX = "\\s+";

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split(REGEX);

        return new TagCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)), tagKeywords);
    }
}
//@@author
