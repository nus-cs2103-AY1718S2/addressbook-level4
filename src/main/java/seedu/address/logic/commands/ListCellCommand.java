//@@author sarahgoh97
package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cell.exceptions.NonExistentCellException;
import seedu.address.model.person.Person;

/**
 * Lists all person in specified cell
 */
public class ListCellCommand extends Command {
    public static final String COMMAND_WORD = "listcell";
    public static final String COMMAND_ALIAS = "lc";
    public static final int MIN_SECURITY_LEVEL = 1;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all people in the specified cell."
            + " Parameters: CELLADDRESS\n"
            + "Example: " + COMMAND_WORD + " 1-1";

    public static final String MESSAGE_LISTCELL_SUCCESS = "Listed persons in cell %s";
    public static final String MESSAGE_NON_EXISTENT_CELL = "This cell %s does not exist";
    private final Predicate<Person> predicate;
    private final String cellAddress;

    public ListCellCommand(Predicate<Person> predicate, String cellAddress) {
        this.predicate = predicate;
        this.cellAddress = cellAddress;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.updateFilteredPersonListForCell(predicate, cellAddress);
        } catch (NonExistentCellException nece) {
            throw new CommandException(String.format(MESSAGE_NON_EXISTENT_CELL, cellAddress));
        }
        return new CommandResult(String.format(MESSAGE_LISTCELL_SUCCESS, cellAddress));
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCellCommand // instanceof handles nulls
                && this.predicate.equals(((ListCellCommand) other).predicate)); // state check
    }
}
