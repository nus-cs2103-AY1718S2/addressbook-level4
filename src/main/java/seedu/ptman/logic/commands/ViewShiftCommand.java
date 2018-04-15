package seedu.ptman.logic.commands;

import java.util.List;

import seedu.ptman.commons.core.Messages;
import seedu.ptman.commons.core.index.Index;
import seedu.ptman.logic.commands.exceptions.CommandException;
import seedu.ptman.model.shift.Shift;

//@@author hzxcaryn
/**
 * Lists all employees in PTMan that belongs to the input shift to the user.
 */
public class ViewShiftCommand extends Command {

    public static final String COMMAND_WORD = "viewshift";
    public static final String COMMAND_ALIAS = "vs";

    public static final String COMMAND_FORMAT = "SHIFT_INDEX";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the employees that belongs to the input shift index.\n"
            + "Parameters: "
            + COMMAND_FORMAT
            + " (must be a positive integer)"
            + "\nExample: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "Listed all employees in shift %1$s.";

    private final Index targetIndex;
    private Shift targetShift;

    public ViewShiftCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Shift> shiftList = model.getFilteredShiftList();
        if (targetIndex.getZeroBased() >= shiftList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SHIFT_DISPLAYED_INDEX);
        }

        targetShift = shiftList.get(targetIndex.getZeroBased());

        model.updateFilteredEmployeeList(employee -> targetShift.containsEmployee(employee));
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewShiftCommand // instanceof handles nulls
                && this.targetIndex.equals(((ViewShiftCommand) other).targetIndex)); // state check
    }
}
