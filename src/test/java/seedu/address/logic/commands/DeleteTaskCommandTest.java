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
import seedu.address.model.student.dashboard.Task;
import seedu.address.testutil.MilestoneBuilder;
import seedu.address.testutil.TypicalTasks;

//@@author yapni
/**
 * Contains integration tests (interaction with the Model) for {@code DeleteTaskCommand}.
 */
public class DeleteTaskCommandTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void execute_validTaskAndMilestoneIndex_success() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTask).build();
        Milestone validUpdatedTargetedMilestone = new MilestoneBuilder().build();

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validUpdatedTargetedMilestone).build();

        model.addStudent(targetStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        expectedModel.updateStudent(targetStudent, updatedTargetStudent);

        assertCommandSuccess(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, INDEX_FOURTH, model), model,
                String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_invalidTaskIndex_failure() throws Exception {
        Milestone validTargetMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();

        model.addStudent(targetStudent);

        // targetStudent only have 3 tasks in the INDEX_FIRST milestone
        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, INDEX_FOURTH, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void execute_invalidMilestoneIndex_failure() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTask).build();
        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();

        model.addStudent(targetStudent);

        // targetStudent only has 1 milestone (that contains 4 tasks)
        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_SECOND, INDEX_FIRST, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    /**
     * Generates a new {@code DeleteTaskCommand} which upon execution, deletes {@code task} from the {@code milestone}
     * in the {@code dashboard} of the {@code student} in the {@code model}.
     */
    private DeleteTaskCommand prepareCommand(Index studentIndex, Index milestoneIndex, Index taskIndex, Model model) {
        DeleteTaskCommand command = new DeleteTaskCommand(studentIndex, milestoneIndex, taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
