package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowStudentDashboardEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.CheckIndexesUtil;
import seedu.address.model.student.Student;

//@@author yapni
/**
 * Shows the dashboard of a student who is identified using it's last displayed index from the address book to the user
 */
public class ShowDashboardCommand extends Command {

    public static final String COMMAND_WORD = "showDB";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the student's dashboard.\n"
            + "Parameters: " + "STUDENT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_STUDENT_DASHBOARD_SUCCESS = "Selected Dashboard of Student: %1$s";

    private final Index targetStudentIndex;

    public ShowDashboardCommand(Index targetStudentIndex) {
        requireNonNull(targetStudentIndex);
        this.targetStudentIndex = targetStudentIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (!CheckIndexesUtil.isStudentIndexValid(lastShownList, targetStudentIndex)) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new ShowStudentDashboardEvent(
                lastShownList.get(targetStudentIndex.getZeroBased())));
        return new CommandResult(String.format(
                MESSAGE_SELECT_STUDENT_DASHBOARD_SUCCESS, targetStudentIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowDashboardCommand // instanceof handles null
                && ((ShowDashboardCommand) other).targetStudentIndex == this.targetStudentIndex);
    }
}
