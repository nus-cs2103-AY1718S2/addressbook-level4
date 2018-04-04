package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA_PLATFORM;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemovePlatformCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.HashSet;
import java.util.Set;

//@@author Nethergale
/**
 * Parses input arguments and creates a new RemovePlatformCommand object
 */
public class RemovePlatformCommandParser implements Parser<RemovePlatformCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemovePlatformCommand
     * and returns a RemovePlatformCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemovePlatformCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SOCIAL_MEDIA_PLATFORM);

        Index index;
        Set<String> platformSet = new HashSet<>();

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            platformSet.addAll(argMultimap.getAllValues(PREFIX_SOCIAL_MEDIA_PLATFORM));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePlatformCommand.MESSAGE_USAGE));
        }

        return new RemovePlatformCommand(index, platformSet);
    }

}
