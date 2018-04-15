// @@author anh2111
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POPULATION;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ViewReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
/**
 * Parses input arguments and creates a new ViewReportCommand object
 */
public class ViewReportCommandParser implements Parser<ViewReportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewReportCommand
     * and returns an ViewReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewReportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_POPULATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_POPULATION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewReportCommand.MESSAGE_USAGE));
        }

        Set<Tag> groups;
        Tag populationTag;
        try {
            populationTag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_POPULATION).orElse(""));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new ViewReportCommand(populationTag);
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
