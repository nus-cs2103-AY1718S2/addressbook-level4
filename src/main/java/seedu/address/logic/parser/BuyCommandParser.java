//@@author ewaldhew
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.TokenType.PREFIX_AMOUNT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BuyCommand;
import seedu.address.logic.commands.CommandTarget;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.coin.Amount;

/**
 * Parses input arguments and creates a new BuyCommand object
 */
public class BuyCommandParser implements Parser<BuyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BuyCommand
     * and returns an BuyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public BuyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenizeToArgumentMultimap(args, PREFIX_AMOUNT);
        if (!argMultimap.arePrefixesPresent(PREFIX_AMOUNT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
        }

        try {
            CommandTarget target = ParserUtil.parseTarget(argMultimap.getPreamble());
            Amount amountToAdd = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            return new BuyCommand(target, amountToAdd);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BuyCommand.MESSAGE_USAGE));
        }

    }

}
