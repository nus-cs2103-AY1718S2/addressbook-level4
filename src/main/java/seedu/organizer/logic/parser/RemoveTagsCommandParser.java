package seedu.organizer.logic.parser;

import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.RemoveTagsCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.tag.Tag;

//@@author natania
/**
 * Parses input arguments and creates a new RemoveTagsCommand object
 */
public class RemoveTagsCommandParser implements Parser<RemoveTagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagsCommand
     * and returns an RemoveTagsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        try {
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            return new RemoveTagsCommand(tagList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
