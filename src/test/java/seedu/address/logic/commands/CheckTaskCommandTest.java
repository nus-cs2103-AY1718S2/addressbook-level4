package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEXES;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_EIGHTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Schedule;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.Progress;
import seedu.address.model.student.dashboard.Task;
import seedu.address.testutil.MilestoneBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TypicalTasks;

//@@author yapni
/**
 * Contains integration tests (interaction with the Model) for {@code CheckTaskCommand}.
 */
public class CheckTaskCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void execute_checkTaskNotCompleted_success() throws Exception {
        Task validTaskNotCompleted = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTaskNotCompleted).build();
        Milestone validUpdatedTargetMilestone = new MilestoneBuilder().withNewTask(validTaskNotCompleted).
                withTaskCompleted(INDEX_FOURTH).build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validUpdatedTargetMilestone).build();

        model.addStudent(targetStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        expectedModel.updateStudent(targetStudent, updatedTargetStudent);

        assertCommandSuccess(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, INDEX_FOURTH, model), model,
                String.format(CheckTaskCommand.MESSAGE_SUCCESS, INDEX_FOURTH.getOneBased(), INDEX_FIRST.getOneBased()),
                expectedModel);
    }

    @Test
    public void execute_checkTaskAlreadyCompleted_messageTaskAlreadyCompleted() throws Exception {
        Task validTaskAlreadyCompleted = new TaskBuilder(TypicalTasks.TASK_4).asCompleted().build();
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTaskAlreadyCompleted).
                withProgress(new Progress("1/4")).build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();

        model.addStudent(targetStudent);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());

        assertCommandSuccess(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, INDEX_FOURTH, model), model,
                CheckTaskCommand.MESSAGE_TASK_ALREADY_COMPLETED, expectedModel);
    }

    @Test
    public void execute_invalidMilestoneIndex_failure() throws Exception {
        Task validTaskNotCompleted = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTaskNotCompleted).build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        model.addStudent(targetStudent);

        // targetStudent only has 1 milestone (which contains 4 tasks)
        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_SECOND, INDEX_FOURTH, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void execute_invalidTaskIndex_failure() throws Exception {
        Milestone validTargetMilestone = new MilestoneBuilder().build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        model.addStudent(targetStudent);

        // targetStudent only has 3 tasks in the INDEX_FIRST milestone
        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, INDEX_FOURTH, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    /**
     * Generates a new {@code CheckTaskCommand} which upon execution marks {@code task}, which is from the
     * {@code milestone} in the {@code dashboard} of the {@code student} in the {@code model}, as completed.
     */
    private CheckTaskCommand prepareCommand(Index studentIndex, Index milestoneIndex, Index taskIndex, Model model) {
        CheckTaskCommand command = new CheckTaskCommand(studentIndex, milestoneIndex, taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
