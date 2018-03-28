package seedu.address.logic.commands;

import java.util.Objects;

/**
 * Converts any amount in terms of SGD to USD
 */
public class ConvertCommand extends Command {

    public static final String COMMAND_WORD = "convert";
    public static final String COMMAND_ALIAS = "cv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Converts an amount in terms of SGD to USD\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE = "Converted SGD %,.2f to USD %,.2f";

    public static final double RATE_USD = 0.76;

    private final double value;

    public ConvertCommand(double value) {
        this.value = value;
    }

    @Override
    public CommandResult execute() {
        double convertedValue = value * RATE_USD;
        return new CommandResult(String.format(MESSAGE_COMPLETE, value, convertedValue));
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

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
