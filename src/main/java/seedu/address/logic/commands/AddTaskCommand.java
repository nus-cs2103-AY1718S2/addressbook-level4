package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.Task;
import seedu.address.model.student.dashboard.UniqueMilestoneList;
import seedu.address.model.student.dashboard.exceptions.DuplicateMilestoneException;
import seedu.address.model.student.dashboard.exceptions.DuplicateTaskException;
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;

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
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_MILESTONE_INDEX + "2 "
            + PREFIX_NAME + "Learn syntax of arrays "
            + PREFIX_DESCRIPTION + "Learn declaration and initialisation of arrays";

    public static final String MESSAGE_DUPLICATE_TASK = "Task already exists in the milestone";
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Index studentIndex;
    private final Index milestoneIndex;
    private final Task newTask;

    private Student targetStudent;
    private Student editedStudent;

    public AddTaskCommand(Index studentIndex, Index milestoneIndex, Task newTask) {
        requireAllNonNull(studentIndex, milestoneIndex, newTask);

        this.studentIndex = studentIndex;
        this.milestoneIndex = milestoneIndex;
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
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size() || studentIndex.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        targetStudent = lastShownList.get(studentIndex.getZeroBased());
        UniqueMilestoneList milestoneList = targetStudent.getDashboard().getMilestoneList();

        if (milestoneIndex.getZeroBased() >= milestoneList.size() || milestoneIndex.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_MILESTONE_DISPLAYED_INDEX);
        }

        try {
            editedStudent = createEditedStudent(targetStudent, newTask, milestoneIndex);
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (DuplicateMilestoneException e) {
            throw new AssertionError("Milestone cannot be duplicated");
        } catch (MilestoneNotFoundException e) {
            throw new AssertionError("Milestone cannot be missing");
        }
    }

    /**
     * Creates and return a copy of {@code Student} with the new task added to its targeted milestone in the Dashboard.
     */
    private Student createEditedStudent(Student studentToEdit, Task newTask, Index targetMilestoneIndex)
            throws DuplicateTaskException, DuplicateMilestoneException, MilestoneNotFoundException {
        requireAllNonNull(studentToEdit, newTask);

        return new StudentBuilder(studentToEdit).withNewTask(targetMilestoneIndex, newTask).build();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles null
                && ((AddTaskCommand) other).studentIndex == this.studentIndex
                && ((AddTaskCommand) other).milestoneIndex  == this.milestoneIndex
                && ((AddTaskCommand) other).newTask == this.newTask);
    }
}
