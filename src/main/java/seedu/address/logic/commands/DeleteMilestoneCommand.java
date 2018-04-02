package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.UniqueMilestoneList;
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;

/**
 * Deletes a milestone from a student's dashboard
 */
public class DeleteMilestoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteMS";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a milestone from a student's dashboard.\n"
            + "Parameters: "
            + PREFIX_INDEX + "STUDENT'S INDEX "
            + PREFIX_MILESTONE_INDEX + "MILESTONE'S INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1"
            + PREFIX_MILESTONE_INDEX + "2";

    public static final String MESSAGE_DELETE_MILESTONE_SUCCESS = "Deleted milestone: %1$s";

    private final Index studentIndex;
    private final Index milestoneIndex;

    private Student targetStudent;
    private Student editedStudent;
    private Milestone targetMilestone;

    public DeleteMilestoneCommand(Index studentIndex, Index milestoneIndex) {
        requireAllNonNull(studentIndex, milestoneIndex);
        this.studentIndex = studentIndex;
        this.milestoneIndex = milestoneIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(targetStudent, editedStudent);

        try {
            model.updateStudent(targetStudent, editedStudent);
        } catch (DuplicateStudentException e) {
            /* DuplicateStudentException caught will mean that the milestone list is the same as before */
            throw new AssertionError("Milestone list cannot be the same");
        } catch (StudentNotFoundException e) {
            throw new AssertionError("Student cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_MILESTONE_SUCCESS, targetMilestone));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            setTargetObjects();
            editedStudent = createEditedStudent(targetStudent, targetMilestone);
        } catch (MilestoneNotFoundException e) {
            throw new AssertionError("Milestone cannot be missing");
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Creates and return a copy of {@code Student} with the specified {@code milestone} removed from the dashboard
     */
    private Student createEditedStudent(Student targetStudent, Milestone milestoneToDelete)
            throws MilestoneNotFoundException {
        requireAllNonNull(targetStudent, milestoneToDelete);

        return new StudentBuilder(targetStudent).withoutMilestone(milestoneToDelete).build();
    }

    /**
     * Sets the {@code targetStudent} and {@code targetMilestone} objects
     * @throws IllegalValueException if any of the studentIndex or milestoneIndex are invalid
     */
    private void setTargetObjects() throws IllegalValueException {
        requireAllNonNull(studentIndex, milestoneIndex);

        List<Student> lastShownList  = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >=  lastShownList.size() || studentIndex.getZeroBased() < 0) {
            throw new IllegalValueException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        targetStudent = lastShownList.get(studentIndex.getZeroBased());
        UniqueMilestoneList milestoneList = targetStudent.getDashboard().getMilestoneList();

        if (milestoneIndex.getZeroBased() >= milestoneList.size() || milestoneIndex.getZeroBased() < 0) {
            throw new IllegalValueException(MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }

        targetMilestone = milestoneList.get(milestoneIndex);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMilestoneCommand // instanceof handles null
                && ((DeleteMilestoneCommand) other).studentIndex == this.studentIndex
                && ((DeleteMilestoneCommand) other).milestoneIndex == this.milestoneIndex);
    }
}
