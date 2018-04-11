//@@author Articho28

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.parseTags;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SearchTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses a SearchTagCommand. Verifies that tags are properly formatted before
 */
public class SearchTagCommandParser implements Parser<SearchTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ColorCommand
     * and returns an RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public SearchTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        Set<Tag> tagsToFind;
        try {
            tagsToFind = parseTags(argMultimap.getAllValues(PREFIX_TAG));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        return new SearchTagCommand(tagsToFind);
    }
}
