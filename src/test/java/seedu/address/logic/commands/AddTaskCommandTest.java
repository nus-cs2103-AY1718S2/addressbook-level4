package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEXES;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_EIGHTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
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
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TypicalTasks;

//@@author yapni
/**
 * Contains integration tests (interaction with the Model) for {@code AddTaskCommand}.
 */
public class AddTaskCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void execute_newTaskUnfilteredList_success() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().build();
        Milestone validUpdatedTargetMilestone = new MilestoneBuilder().withNewTask(validTask).build();
        Index validTargetMilestoneIndex = Index.fromOneBased(1);

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validUpdatedTargetMilestone).build();

        model.addStudent(targetStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        expectedModel.updateStudent(targetStudent, updatedTargetStudent);

        assertCommandSuccess(prepareCommand(Index.fromOneBased(expectedModel.getFilteredStudentList().size()),
                validTargetMilestoneIndex, validTask, model), model,
                String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_newTaskFilteredList_success() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().build();
        Milestone validUpdatedTargetMilestone = new MilestoneBuilder().withNewTask(validTask).build();
        Index validTargetMilestoneIndex = Index.fromOneBased(1);

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validUpdatedTargetMilestone).build();

        Index unfilteredTargetStudentIndex = INDEX_EIGHTH;

        model.addStudent(targetStudent);
        showStudentAtIndex(model, unfilteredTargetStudentIndex);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        showStudentAtIndex(expectedModel, unfilteredTargetStudentIndex);
        expectedModel.updateStudent(expectedModel.getFilteredStudentList().get(0), updatedTargetStudent);

        assertCommandSuccess(prepareCommand(INDEX_FIRST, validTargetMilestoneIndex, validTask, model), model,
                String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_duplicateTask_failure() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().withNewTask(validTask).build();
        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();

        model.addStudent(targetStudent);

        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, validTask, model), model,
                AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().build();
        Index validTargetMilestoneIndex = Index.fromOneBased(1);

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        model.addStudent(targetStudent);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);

        assertCommandFailure(prepareCommand(outOfBoundIndex, validTargetMilestoneIndex, validTask, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void  execute_invalidStudentIndexFilteredList_failure() throws Exception {
        Task validTask = TypicalTasks.TASK_4;
        Milestone validTargetMilestone = new MilestoneBuilder().build();
        Index validTargetMilestoneIndex = Index.fromOneBased(1);

        Student targetStudent = new StudentBuilder().withNewMilestone(validTargetMilestone).build();
        model.addStudent(targetStudent);

        showStudentAtIndex(model, INDEX_EIGHTH);
        Index outOfBoundStudentIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundStudentIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        assertCommandFailure(prepareCommand(outOfBoundStudentIndex, validTargetMilestoneIndex, validTask, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void execute_invalidMilestoneIndex_failure() {
        Task validTask = TypicalTasks.TASK_4;

        assertCommandFailure(prepareCommand(INDEX_FIRST, INDEX_FIRST, validTask, model), model,
                MESSAGE_INVALID_INDEXES);
    }

    /**
     * Generates a new {@code AddTaskCommand} which upon execution, adds {@code task} to the {@code milestone}
     * in the {@code dashboard} of the {@code student} in the {@code model}.
     */
    private AddTaskCommand prepareCommand(Index studentIndex, Index milestoneIndex, Task newTask, Model model) {
        AddTaskCommand command = new AddTaskCommand(studentIndex, milestoneIndex, newTask);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
