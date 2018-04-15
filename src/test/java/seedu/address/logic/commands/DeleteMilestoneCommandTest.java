package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEXES;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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

//@@author yapni
/**
 * Contains integration tests (interaction with the Model) for {@code DeleteMilestoneCommand}.
 */
public class DeleteMilestoneCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Schedule());
    }

    @Test
    public void execute_validMilestoneIndex_success() throws Exception {
        Milestone validMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().withNewMilestone(validMilestone).build();
        Student updatedTargetStudent = new StudentBuilder().build();

        model.addStudent(targetStudent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new Schedule());
        expectedModel.updateStudent(targetStudent, updatedTargetStudent);

        assertCommandSuccess(prepareCommand(INDEX_EIGHTH, INDEX_FIRST, model), model,
                String.format(DeleteMilestoneCommand.MESSAGE_DELETE_MILESTONE_SUCCESS, validMilestone), expectedModel);
    }

    @Test
    public void execute_invalidMilestoneIndex_failure() throws Exception {
        Milestone validMilestone = new MilestoneBuilder().build();
        Student targetStudent = new StudentBuilder().withNewMilestone(validMilestone).build();

        model.addStudent(targetStudent);

        // targetStudent has only 1 milestone
        assertCommandFailure(prepareCommand(INDEX_EIGHTH, INDEX_SECOND, model), model, MESSAGE_INVALID_INDEXES);
    }

    /**
     * Generates a new {@code DeleteMilestoneCommand} which upon execution, deletes {@code milestone} from the
     * {@code dashboard} of the {@code student} in the {@code model}.
     */
    private DeleteMilestoneCommand prepareCommand(Index studentIndex, Index milestoneIndex, Model model) {
        DeleteMilestoneCommand command = new DeleteMilestoneCommand(studentIndex, milestoneIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


}
