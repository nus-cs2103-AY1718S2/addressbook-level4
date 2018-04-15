package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CloseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Category;

//@@author olimhc
/**
 * Parses an input and create a new CloseCommand object
 */
public class CloseCommandParser implements Parser<CloseCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CloseCommand
     * and returns a Close Command object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CloseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_CATEGORY)
                || argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseCommand.MESSAGE_USAGE));
        }

        Index index;
        Category category;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
            category = ParserUtil.parseCategory(argumentMultimap.getValue(PREFIX_CATEGORY)).get();
            return new CloseCommand(index, category);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseCommand.MESSAGE_USAGE));
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
