package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.CheckIndexesUtil;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;

//@@author yapni
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

    private final Index targetStudentIndex;
    private final Index targetMilestoneIndex;

    private Student targetStudent;
    private Student editedStudent;
    private Milestone targetMilestone;

    public DeleteMilestoneCommand(Index targetStudentIndex, Index targetMilestoneIndex) {
        requireAllNonNull(targetStudentIndex, targetMilestoneIndex);
        this.targetStudentIndex = targetStudentIndex;
        this.targetMilestoneIndex = targetMilestoneIndex;
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
            createEditedStudent();
        } catch (MilestoneNotFoundException e) {
            throw new AssertionError("Milestone cannot be missing");
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Creates {@code editedStudent} which is a copy of {@code targetStudent}, but with the {@code targetMilestone}
     * removed from the {@code dashboard}.
     */
    private void createEditedStudent() throws MilestoneNotFoundException {
        editedStudent = new StudentBuilder(targetStudent).withoutMilestone(targetMilestone).build();
    }

    /**
     * Sets the {@code targetStudent} and {@code targetMilestone} objects
     * @throws IllegalValueException if any of the targetStudentIndex or targetMilestoneIndex are invalid
     */
    private void setTargetObjects() throws IllegalValueException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (!CheckIndexesUtil.areIndexesValid(lastShownList, targetStudentIndex, targetMilestoneIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        targetStudent = lastShownList.get(targetStudentIndex.getZeroBased());
        targetMilestone = targetStudent.getDashboard().getMilestoneList().get(targetMilestoneIndex);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMilestoneCommand // instanceof handles null
                && ((DeleteMilestoneCommand) other).targetStudentIndex == this.targetStudentIndex
                && ((DeleteMilestoneCommand) other).targetMilestoneIndex == this.targetMilestoneIndex);
    }
}
