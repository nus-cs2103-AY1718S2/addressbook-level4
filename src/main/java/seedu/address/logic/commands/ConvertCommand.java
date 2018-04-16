package seedu.address.logic.commands;

import seedu.address.commons.util.DateUtil;
import seedu.address.model.Currency;


//@@author jill858
/**
 * Converts any amount from one currency to another
 */
public class ConvertCommand extends Command {

    public static final String COMMAND_WORD = "convert";
    public static final String COMMAND_ALIAS = "cv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Converts an amount from one currency to another\n"
            + "Example: " + COMMAND_WORD + " 1 SGD USD\n"
            + "Sample currency code: \n"
            + "AUD - Australian dollar\n"
            + "CAD - Canadian dollar\n"
            + "CNY - Renminbi (Chinese) yuan\n"
            + "HKD - Hong Kong dollar\n"
            + "JPY - Japanese yen\n"
            + "MYR - Malaysian ringgit\n"
            + "SGD - Singapore dollar\n"
            + "TWD - New Taiwan dollar\n"
            + "USD - United States dollar\n";

    public static final String MESSAGE_COMPLETE = "Converted %s %,.2f to %s %,.2f as of %s";

    private Currency currency = new Currency();

    private String fromCurrencyCode;
    private String toCurrencyCode;
    private final double value;

    public ConvertCommand(String fromCurrencyCode, String toCurrencyCode, double value) {
        this.fromCurrencyCode = fromCurrencyCode;
        this.toCurrencyCode = toCurrencyCode;
        this.value = value;
    }

    @Override
    public CommandResult execute() {

        double convertedValue = currency.convert(fromCurrencyCode, toCurrencyCode, value);

        String dateString = currency.getDate();
        String dateFormat = DateUtil.dateFormatter(dateString);

        return new CommandResult(String.format(MESSAGE_COMPLETE,
                fromCurrencyCode, value, toCurrencyCode, convertedValue, dateFormat));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConvertCommand that = (ConvertCommand) o;
        return Double.compare(that.value, value) == 0;
    }
}
