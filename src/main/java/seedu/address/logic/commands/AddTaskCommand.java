package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEXES;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

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
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;

//@@author yapni
/**
 * Adds a Task to a Milestone
 */
public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to a Milestone in a Student's Dashboard.\n"
            + "Parameters: "
            + PREFIX_INDEX + "STUDENT'S INDEX "
            + PREFIX_MILESTONE_INDEX + "MILESTONE'S INDEX "
            + PREFIX_NAME + "NAME OF TASK "
            + PREFIX_DESCRIPTION + "DESCRIPTION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_MILESTONE_INDEX + "2 "
            + PREFIX_NAME + "Learn syntax of arrays "
            + PREFIX_DESCRIPTION + "Learn declaration and initialisation of arrays";

    public static final String MESSAGE_DUPLICATE_TASK = "Task already exists in the milestone";
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private Student targetStudent;
    private Student editedStudent;
    private Milestone targetMilestone;

    private final Task newTask;
    private final Index targetStudentIndex;
    private final Index targetMilestoneIndex;

    public AddTaskCommand(Index targetStudentIndex, Index targetMilestoneIndex, Task newTask) {
        requireAllNonNull(targetStudentIndex, targetMilestoneIndex, newTask);

        this.targetStudentIndex = targetStudentIndex;
        this.targetMilestoneIndex = targetMilestoneIndex;
        this.newTask = newTask;
    }

    @Override
    protected CommandResult executeUndoableCommand() {
        requireAllNonNull(targetStudent, editedStudent);

        try {
            model.updateStudent(targetStudent, editedStudent);
        } catch (DuplicateStudentException e) {
            /* DuplicateStudentException caught will mean that the task list is the same as before */
            throw new AssertionError("New task cannot be missing");
        } catch (StudentNotFoundException e) {
            throw new AssertionError("The target student cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, newTask));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        try {
            setTargetObjects();
            createEditedStudent();
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (DuplicateMilestoneException e) {
            throw new AssertionError("Milestone cannot be duplicated");
        } catch (MilestoneNotFoundException e) {
            throw new AssertionError("Milestone cannot be missing");
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Creates {@code editedStudent} which is a copy of {@code targetStudent}, but with the {@code newTask} added
     * to the {@code targetMilestone}.
     */
    private void createEditedStudent()
            throws DuplicateTaskException, DuplicateMilestoneException, MilestoneNotFoundException {
        editedStudent = new StudentBuilder(targetStudent).withNewTask(targetMilestoneIndex, newTask).build();
    }

    /**
     * Sets the {@code targetStudent} and {@code targetMilestone} of this command object.
     * @throws IllegalValueException if any of the target indexes are invalid
     */
    private void setTargetObjects() throws IllegalValueException {
        List<Student> lastShownList = model.getFilteredStudentList();

        if (!CheckIndexesUtil.areIndexesValid(lastShownList, targetStudentIndex, targetMilestoneIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEXES);
        }

        targetStudent = lastShownList.get(targetStudentIndex.getZeroBased());
        targetMilestone = targetStudent.getDashboard().getMilestoneList().get(targetMilestoneIndex);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles null
                && ((AddTaskCommand) other).targetStudentIndex == this.targetStudentIndex
                && ((AddTaskCommand) other).targetMilestoneIndex == this.targetMilestoneIndex
                && ((AddTaskCommand) other).newTask == this.newTask);
    }
}
