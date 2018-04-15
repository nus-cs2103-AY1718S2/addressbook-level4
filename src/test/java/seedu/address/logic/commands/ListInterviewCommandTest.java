//@@author deeheenguyen
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showInterviewAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERVIEW;
import static seedu.address.testutil.TypicalInterviews.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListJobsCommand.
 */
public class ListInterviewCommandTest {

    private Model model;
    private Model expectedModel;
    private ListInterviewCommand listInterviewCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listInterviewCommand = new ListInterviewCommand();
        listInterviewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_interviewListIsNotFiltered_showsSameList() {
        assertCommandSuccess(listInterviewCommand, model, ListInterviewCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_interviewListIsFiltered_showsEverything() {
        showInterviewAtIndex(model, INDEX_FIRST_INTERVIEW);
        assertCommandSuccess(listInterviewCommand, model, ListInterviewCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
