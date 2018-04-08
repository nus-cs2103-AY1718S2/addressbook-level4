package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
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
import seedu.address.testutil.MilestoneBuilder;
import seedu.address.testutil.StudentBuilder;

//@@author yapni
/**
 * Contains integration tests (interaction with the Model) for {@code AddMilestoneCommand}.
 */
public class AddMilestoneCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void execute_newMilestoneUnfilteredList_success() throws Exception {
        Milestone validMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validMilestone).build();

        model.addStudent(targetStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        expectedModel.updateStudent(targetStudent, updatedTargetStudent);

        assertCommandSuccess(prepareCommand(Index.fromOneBased(expectedModel.getFilteredStudentList().size()),
                validMilestone, model), model, String.format(AddMilestoneCommand.MESSAGE_SUCCESS, validMilestone),
                expectedModel);
    }

    @Test
    public void execute_newMilestoneFilteredList_success() throws Exception {
        Milestone validMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().build();
        Student updatedTargetStudent = new StudentBuilder().withNewMilestone(validMilestone).build();

        Index unfilteredTargetStudentIndex = INDEX_EIGHTH;

        model.addStudent(targetStudent);
        showStudentAtIndex(model, unfilteredTargetStudentIndex);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        showStudentAtIndex(expectedModel, unfilteredTargetStudentIndex);
        expectedModel.updateStudent(expectedModel.getFilteredStudentList().get(0), updatedTargetStudent);

        assertCommandSuccess(prepareCommand(INDEX_FIRST, validMilestone, model), model,
                String.format(AddMilestoneCommand.MESSAGE_SUCCESS, validMilestone), expectedModel);
    }

    @Test
    public void execute_duplicate_milestone_throwsCommandException() throws Exception {
        Milestone duplicateMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().withNewMilestone(duplicateMilestone).build();
        model.addStudent(targetStudent);

        assertCommandFailure(prepareCommand(Index.fromOneBased(model.getFilteredStudentList().size()),
                duplicateMilestone, model), model, AddMilestoneCommand.MESSAGE_DUPLICATE_MILESTONE);
    }

    @Test
    public void execute_invalidStudentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudentList().size() + 1);
        Milestone validMilestone = new MilestoneBuilder().build();

        assertCommandFailure(prepareCommand(outOfBoundIndex, validMilestone, model), model,
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStudentIndexFilteredList_failure() {
        showStudentAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getStudentList().size());

        Milestone validMilestone = new MilestoneBuilder().build();

        assertCommandFailure(prepareCommand(outOfBoundIndex, validMilestone, model), model,
                MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
    }

    /**
     * Generates a new {@code AddMilestoneCommand} which upon execution, adds {@code milestone} in the {@code dashboard}
     * of the {@code student} at {@code studentIndex} in the {@code model}.
     */
    private AddMilestoneCommand prepareCommand(Index studentIndex, Milestone milestone, Model model) {
        AddMilestoneCommand command = new AddMilestoneCommand(studentIndex, milestone);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
