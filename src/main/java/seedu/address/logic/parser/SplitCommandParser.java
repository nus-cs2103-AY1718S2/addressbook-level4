//@@author chenchongsong
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;

import java.util.ArrayList;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SplitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SplitCommand object
 */
public class SplitCommandParser implements Parser<SplitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SplitCommand
     * and returns a SplitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SplitCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MONEY);

        ArrayList<Index> indices;
        try {
            indices = ParserUtil.parseIndices(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));
        }

        double bill;
        try {
            Optional<String> money = argMultimap.getValue(PREFIX_MONEY);
            bill = Double.parseDouble(money.get());
        } catch (Exception e) {
            throw new ParseException("A correct number(money) needs to be provided for the Bill!");
        }

        return new SplitCommand(indices, bill);
    }

}
