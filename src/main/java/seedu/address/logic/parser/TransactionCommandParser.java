//@@author pkuhanan
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.money.Money.MONEY_VALIDATION_REGEX;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.TransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TransactionCommand object
 */
public class TransactionCommandParser {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    /**
     * Parses the given {@code String} of arguments in the context of the TransactionCommand
     * and returns a TransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TransactionCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] currencyKeywords = trimmedArgs.split(" ");

            Index index = Index.fromOneBased(Integer.parseInt(currencyKeywords[0]));
            String amount = currencyKeywords[1].toUpperCase();

            if (!StringUtil.isNonZeroUnsignedInteger(currencyKeywords[0]) || !amount.matches(MONEY_VALIDATION_REGEX)) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }

            return new TransactionCommand(index, Double.parseDouble(amount));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TransactionCommand.MESSAGE_USAGE));
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TransactionCommand.MESSAGE_USAGE));
        }
    }
}
