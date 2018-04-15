// @@author anh2111
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POPULATION;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SaveReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
/**
 * Parses input arguments and creates a new SaveReportCommand object
 */
public class SaveReportCommandParser implements Parser<SaveReportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveReportCommand
     * and returns an SaveReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SaveReportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POPULATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_POPULATION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveReportCommand.MESSAGE_USAGE));
        }

        Tag populationTag;
        try {
            populationTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_POPULATION).orElse(""));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new SaveReportCommand(populationTag);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
// @@author
