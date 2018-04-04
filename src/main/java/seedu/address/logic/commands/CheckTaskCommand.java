package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.UniqueMilestoneList;
import seedu.address.model.student.dashboard.UniqueTaskList;
import seedu.address.model.student.dashboard.exceptions.DuplicateMilestoneException;
import seedu.address.model.student.dashboard.exceptions.DuplicateTaskException;
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;
import seedu.address.model.student.dashboard.exceptions.TaskNotFoundException;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;


//@@author yapni
/**
 * Mark a task as completed
 */
public class CheckTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "checkTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a Task in a Milestone as completed.\n"
            + "Parameters: "
            + PREFIX_INDEX + "STUDENT'S INDEX "
            + PREFIX_MILESTONE_INDEX + "MILESTONE'S INDEX "
            + PREFIX_TASK_INDEX + "TASK'S INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_MILESTONE_INDEX + "2"
            + PREFIX_TASK_INDEX + "3";

    public static final String MESSAGE_SUCCESS = "Task %1$s marked as completed in milestone %2$s";
    public static final String MESSAGE_TASK_ALREADY_COMPLETED = "Task is already marked as completed";

    private Student targetStudent;
    private Student editedStudent;

    private final Index targetStudentIndex;
    private final Index targetMilestoneIndex;
    private final Index targetTaskIndex;

    public CheckTaskCommand(Index targetStudentIndex, Index targetMilestoneIndex, Index targetTaskIndex) {
        requireAllNonNull(targetStudentIndex, targetMilestoneIndex, targetTaskIndex);

        this.targetStudentIndex = targetStudentIndex;
        this.targetMilestoneIndex = targetMilestoneIndex;
        this.targetTaskIndex = targetTaskIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(targetStudent, editedStudent);

        if (targetStudent != editedStudent) {
            try {
                model.updateStudent(targetStudent, editedStudent);
            } catch (DuplicateStudentException e) {
                /* DuplicateStudentException caught will mean that the task list is the same as before */
                throw new AssertionError("New task cannot be missing");
            } catch (StudentNotFoundException e) {
                throw new AssertionError("The target student cannot be missing");
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS,
                    targetTaskIndex.getOneBased(), targetMilestoneIndex.getOneBased()));
        } else {
            return new CommandResult(MESSAGE_TASK_ALREADY_COMPLETED);
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            checkIfIndexesAreValid();
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }

        List<Student> lastShownList = model.getFilteredStudentList();
        targetStudent = lastShownList.get(targetStudentIndex.getZeroBased());

        /* Only create new edited student if the task has not been marked as completed */
        if (!targetStudent.getDashboard().getMilestoneList().get(targetMilestoneIndex).getTaskList()
                .get(targetTaskIndex).isCompleted()) {
            try {
                editedStudent = createEditedStudent(targetStudent, targetMilestoneIndex, targetTaskIndex);
            } catch (DuplicateTaskException e) {
                throw new AssertionError("The task cannot be duplicated");
            } catch (TaskNotFoundException e) {
                throw new AssertionError("The target task cannot be missing");
            } catch (DuplicateMilestoneException e) {
                throw new AssertionError("The milestone cannot be duplicated");
            } catch (MilestoneNotFoundException e) {
                throw new AssertionError("The milestone cannot be missing");
            }
        } else {
            editedStudent = targetStudent;
        }
    }

    /**
     * Creates and return a copy of {@code Student} with the task in the specified milestone marked as completed.
     */
    private Student createEditedStudent(Student studentToEdit, Index milestoneIndex, Index taskIndex)
            throws DuplicateTaskException, TaskNotFoundException,
            DuplicateMilestoneException, MilestoneNotFoundException {
        requireAllNonNull(studentToEdit, milestoneIndex, taskIndex);


        return new StudentBuilder(studentToEdit).withTaskCompleted(milestoneIndex, taskIndex).build();
    }

    /**
     * Checks if the student index, milestone index and task index are valid
     * @throws IllegalValueException if any of the indexes are invalid
     */
    private void checkIfIndexesAreValid() throws IllegalValueException {
        requireAllNonNull(targetStudentIndex, targetMilestoneIndex, targetTaskIndex);

        /* Check if student index is valid */
        List<Student> lastShownList = model.getFilteredStudentList();
        if (targetStudentIndex.getZeroBased() < 0 || targetStudentIndex.getZeroBased() >= lastShownList.size()) {
            throw new IllegalValueException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        /* Check if milestone index is valid */
        Student student = lastShownList.get(targetStudentIndex.getZeroBased());
        UniqueMilestoneList milestoneList = student.getDashboard().getMilestoneList();
        if (targetMilestoneIndex.getZeroBased() < 0 || targetMilestoneIndex.getZeroBased() >= milestoneList.size()) {
            throw new IllegalValueException(MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }

        /*  Check if task index is valid */
        UniqueTaskList taskList = milestoneList.get(targetMilestoneIndex).getTaskList();
        if (targetTaskIndex.getZeroBased() < 0 || targetTaskIndex.getZeroBased() >= taskList.size()) {
            throw new IllegalValueException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckTaskCommand // instanceof handles null
                && ((CheckTaskCommand) other).targetStudentIndex == this.targetStudentIndex
                && ((CheckTaskCommand) other).targetMilestoneIndex == this.targetMilestoneIndex
                && ((CheckTaskCommand) other).targetTaskIndex == this.targetTaskIndex);
    }
}
