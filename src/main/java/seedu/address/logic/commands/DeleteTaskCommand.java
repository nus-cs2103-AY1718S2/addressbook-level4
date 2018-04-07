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
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;
import seedu.address.model.student.dashboard.exceptions.TaskNotFoundException;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;

//@@author yapni
/**
 * Deletes a task from a milestone
 */
public class DeleteTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a task from a milestone in a student's dashboard.\n"
            + "Parameters: "
            + PREFIX_INDEX + "STUDENT'S INDEX "
            + PREFIX_MILESTONE_INDEX + "MILESTONE'S INDEX "
            + PREFIX_TASK_INDEX + "TASK'S INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1"
            + PREFIX_MILESTONE_INDEX + "2"
            + PREFIX_TASK_INDEX + "3";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    private Student targetStudent;
    private Student editedStudent;
    private Milestone targetMilestone;
    private Task targetTask;

    private final Index targetStudentIndex;
    private final Index targetMilestoneIndex;
    private final Index targetTaskIndex;

    public DeleteTaskCommand(Index targetStudentIndex, Index targetMilestoneIndex, Index targetTaskIndex) {
        requireAllNonNull(targetStudentIndex, targetMilestoneIndex, targetTaskIndex);

        this.targetStudentIndex = targetStudentIndex;
        this.targetMilestoneIndex = targetMilestoneIndex;
        this.targetTaskIndex = targetTaskIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireAllNonNull(targetStudent, editedStudent);

        try {
            model.updateStudent(targetStudent, editedStudent);
        } catch (DuplicateStudentException e) {
            /* DuplicateStudentException caught will mean that the task list is the same as before */
            throw new AssertionError("Task should be already removed from task list");
        } catch (StudentNotFoundException e) {
            throw new AssertionError("The target student cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, targetTask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            setTargetObjects();
            createEditedStudent();
        } catch (DuplicateMilestoneException e) {
            throw new AssertionError("Milestone cannot be duplicated");
        } catch (TaskNotFoundException e) {
            throw new AssertionError("Task cannot be missing");
        } catch (MilestoneNotFoundException e) {
            throw new AssertionError("Milestone cannot be missing");
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Creates {@code editedStudent} which is a copy of {@code targetStudent} but with the {@code targetTask} removed
     * from the {@code targetMilestone}
     */
    private void createEditedStudent()
            throws DuplicateMilestoneException, MilestoneNotFoundException, TaskNotFoundException {
        editedStudent = new StudentBuilder(targetStudent).withoutTask(targetMilestoneIndex, targetTask).build();
    }

    /**
     * Sete the {@code targetStudent}, {@code targetMilestone}, {@code targetTask} of this command object.
     * @throws IllegalValueException if any of the target indexes are invalid
     */
    private void setTargetObjects() throws IllegalValueException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (!CheckIndexesUtil.areIndexesValid(lastShownList, targetStudentIndex, targetMilestoneIndex,
                targetTaskIndex)) {
            throw new IllegalValueException("One or more of the provided indexes are invalid");
        }

        targetStudent = lastShownList.get(targetStudentIndex.getZeroBased());
        targetMilestone = targetStudent.getDashboard().getMilestoneList().get(targetMilestoneIndex);
        targetTask = targetMilestone.getTaskList().get(targetTaskIndex);
    }
}
