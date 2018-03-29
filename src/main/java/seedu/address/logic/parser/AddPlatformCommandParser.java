package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPlatformCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.smplatform.Link;

//@@author Nethergale
/**
 * Parses input arguments and creates a new AddPlatformCommand object
 */
public class AddPlatformCommandParser implements Parser<AddPlatformCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPlatformCommand
     * and returns an AddPlatformCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddPlatformCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LINK);

        Index index;
        Map<String, Link> linkMap;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPlatformCommand.MESSAGE_USAGE));
        }

        try {
            linkMap = parseLinksForAddPlatform(argMultimap.getAllValues(PREFIX_LINK));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new AddPlatformCommand(index, linkMap);
    }

    /**
     * Parses {@code Collection<String> links} into a {@code Map<String, Link>} if {@code links} is non-empty.
     * If {@code links} contain only one element which is an empty string, it will be parsed into a
     * {@code Map<String, Link>} containing zero links.
     *
     * @throws IllegalValueException if user does not specify even a single link prefix.
     */
    private Map<String, Link> parseLinksForAddPlatform(Collection<String> links) throws IllegalValueException {
        assert links != null;

        if (links.isEmpty()) {
            throw new IllegalValueException(AddPlatformCommand.MESSAGE_LINK_COLLECTION_EMPTY);
        }
        Collection<String> linkSet = links.size() == 1 && links.contains("") ? Collections.emptySet() : links;
        return ParserUtil.parseLinks(linkSet);
    }
}
