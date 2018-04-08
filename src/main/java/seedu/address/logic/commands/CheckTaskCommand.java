package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.CheckIndexesUtil;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.Task;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a task from a milestone in a student's dashboard as completed.\n"
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
    private Milestone targetMilestone;
    private Task targetTask;
    private boolean taskWasAlreadyCompleted;

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
        if (!taskWasAlreadyCompleted) {
            try {
                requireAllNonNull(targetStudent, editedStudent);
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
            setTargetObjects();
            if (!targetTask.isCompleted()) {
                taskWasAlreadyCompleted = false;
                createEditedStudent();
            } else {
                taskWasAlreadyCompleted = true;
            }
        } catch (DuplicateTaskException e) {
            throw new AssertionError("The task cannot be duplicated");
        } catch (TaskNotFoundException e) {
            throw new AssertionError("The target task cannot be missing");
        } catch (DuplicateMilestoneException e) {
            throw new AssertionError("The milestone cannot be duplicated");
        } catch (MilestoneNotFoundException e) {
            throw new AssertionError("The milestone cannot be missing");
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Sets the {@code targetStudent}, {@code targetMilestone} and {@code targetTask }of this command object.
     * @throws IllegalValueException if any of the target indexes are invalid
     */
    private void setTargetObjects() throws IllegalValueException {
        assert targetStudentIndex != null && targetMilestoneIndex != null && targetTaskIndex != null;

        List<Student> lastShownList = model.getFilteredStudentList();
        if (!CheckIndexesUtil.areIndexesValid(lastShownList, targetStudentIndex, targetMilestoneIndex,
                targetTaskIndex)) {
            throw new IllegalValueException("One or more of the provided indexes are invalid");
        }

        targetStudent = lastShownList.get(targetStudentIndex.getZeroBased());
        targetMilestone = targetStudent.getDashboard().getMilestoneList().get(targetMilestoneIndex);
        targetTask = targetMilestone.getTaskList().get(targetTaskIndex);
    }

    /**
     * Creates {@code editedStudent} which is a copy of {@code targetStudent}, but with the {@code targetTask} marked
     * as completed in the {@code targetMilestone}.
     */
    private void createEditedStudent() throws DuplicateTaskException, TaskNotFoundException,
            DuplicateMilestoneException, MilestoneNotFoundException {
        assert targetStudent != null && targetMilestoneIndex != null && targetTaskIndex != null;
        editedStudent = new StudentBuilder(targetStudent)
                .withTaskCompleted(targetMilestoneIndex, targetTaskIndex).build();
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
