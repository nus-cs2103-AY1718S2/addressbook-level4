//@@author Articho28

package seedu.address.logic.commands;

import java.text.DecimalFormat;
import java.util.List;

import seedu.address.model.person.Person;
/**
 * Handles the balance command.
 */
public class BalanceCommand extends Command {

    public static final String COMMAND_WORD = "balance";
    public static final String COMMAND_SHORTCUT = "b";
    public static final String MESSAGE_SUCCESS = "Shown balance.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Displays your overall balance";
    private static double calculatedBalance;
    private static DecimalFormat twoDecimalPlaces = new DecimalFormat("0.00");

    public static DecimalFormat getFormatTwoDecimalPlaces() {
        return twoDecimalPlaces;
    }

    public static double getCalculatedBalance() {
        return calculatedBalance;
    }

    @Override
    public CommandResult execute() {

        calculatedBalance = getBalanceFromTravelBanker();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + "Your balance is "
                + twoDecimalPlaces.format(calculatedBalance) + ".");
    }

    public double getBalanceFromTravelBanker() {
        double accumulator = 0.00;

        List<Person> lastShownList = model.getFilteredPersonList();

        for (Person person : lastShownList) {
            double currentPersonBalance = person.getMoney().balance;
            accumulator = accumulator + currentPersonBalance;
        }

        return accumulator;
    }
}
