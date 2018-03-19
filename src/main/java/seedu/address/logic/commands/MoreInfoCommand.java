package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.StudentNotFoundException;

/**
 * Displays the full information of a student on the browser panel
 */
public class MoreInfoCommand extends Command {

    public static final String COMMAND_WORD = "moreInfo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the full information and particulars of "
            + "a student identified by the index number used"
            + " in the last student listing. Also includes his/her profile picture(if one exists).\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_MOREINFO_STUDENT_SUCCESS = "Displayed full information for student: %1$s";

    private final Index targetIndex;

    private Student studentToGetInfoFrom;

    public MoreInfoCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            preProcessStudent();

            model.displayStudentDetailsOnBrowserPanel(studentToGetInfoFrom);

        } catch (StudentNotFoundException e) {
            throw new AssertionError("The target student cannot be missing");
        }

        return new CommandResult((String.format(MESSAGE_MOREINFO_STUDENT_SUCCESS, studentToGetInfoFrom.getName())));
    }

    public void preProcessStudent() throws CommandException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        studentToGetInfoFrom = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MoreInfoCommand // instanceof handles nulls
                && this.targetIndex.equals(((MoreInfoCommand) other).targetIndex) // state check
                && Objects.equals(this.studentToGetInfoFrom, ((MoreInfoCommand) other).studentToGetInfoFrom));
    }
}

