package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TagReplaceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class TagReplaceCommandParser implements Parser<TagReplaceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagReplaceCommand
     * and returns an TagReplaceCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagReplaceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagReplaceCommand.MESSAGE_USAGE));
        }

        try {
            List<Tag> tagSet = ParserUtil.parseTagsForReplacement(argMultimap.getAllValues(PREFIX_TAG));
            return new TagReplaceCommand(tagSet);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagReplaceCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

