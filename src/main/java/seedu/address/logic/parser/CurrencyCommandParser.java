package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.CurrencyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CurrencyCommandParser implements Parser<CurrencyCommand> {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    /**
     * Parses the given {@code String} of arguments in the context of the CurrencyCommand
     * and returns an CurrencyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CurrencyCommand parse(String args) throws ParseException {

        String fromCurrency;
        String toCurrency;

        try {
            String trimmedArgs = args.trim();
            String[] currencyKeywords = trimmedArgs.split(" ");
            Index currencyIndex = Index.fromOneBased(Integer.parseInt(currencyKeywords[0]));

            if (!StringUtil.isNonZeroUnsignedInteger(currencyKeywords[0])) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }

            fromCurrency = currencyKeywords[1].toUpperCase();
            toCurrency = currencyKeywords[2].toUpperCase();
            return new CurrencyCommand(currencyIndex, fromCurrency, toCurrency);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CurrencyCommand.MESSAGE_USAGE));
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CurrencyCommand.MESSAGE_USAGE));
        }

    }

}


