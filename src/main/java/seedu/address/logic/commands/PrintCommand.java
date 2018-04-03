package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.PrintFormatter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;

/**
 * Selects a patient identified using it's last displayed index from the address book.
 * Formats and prints that patient's medical records.
 */
public class PrintCommand extends Command {
    public static final String COMMAND_WORD = "print";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Formats and prints out a patient's "
            + "medical records.\n"
            + "Example: " + COMMAND_WORD + " INDEX";

    private static PrintFormatter printFormatter;

    private final Index targetIndex;

    private Patient patient;

    public PrintCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        this.patient = lastShownList.get(targetIndex.getZeroBased());
        this.printFormatter = new PrintFormatter(this.patient);

        return new CommandResult("Printed records of patient " + patient.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PrintCommand // instanceof handles nulls
                && this.targetIndex.equals(((PrintCommand) other).targetIndex)); // state check
    }
}
