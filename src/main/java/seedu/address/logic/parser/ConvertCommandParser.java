package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ConvertCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Currency;
//@@author jill858
/**
 * Parses input arguments and creates a new ConvertCommand object
 */
public class ConvertCommandParser implements Parser<ConvertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ConvertCommand
     * and returns an ConvertCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ConvertCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        double value;
        String fromCurrencyCode;
        String toCurrencyCode;

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        }

        String[] currencyKeywords = trimmedArgs.split("\\s+");

        try {
            if (currencyKeywords.length == 2) {
                //converting base rate
                value = 1;
                fromCurrencyCode = currencyKeywords[0].toUpperCase();
                toCurrencyCode = currencyKeywords[1].toUpperCase();
            } else {
                //converting some value
                value = Double.parseDouble(currencyKeywords[0]);
                fromCurrencyCode = currencyKeywords[1].toUpperCase();
                toCurrencyCode = currencyKeywords[2].toUpperCase();
            }
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        } catch (ArrayIndexOutOfBoundsException aie) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        }

        boolean isValidFromCurrencyCode = new Currency().containsCurrencyCode(fromCurrencyCode);
        boolean isValidToCurrencyCode = new Currency().containsCurrencyCode(toCurrencyCode);

        if (isValidFromCurrencyCode && isValidToCurrencyCode) {
            return new ConvertCommand(fromCurrencyCode, toCurrencyCode, value);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE));
        }

    }
}
