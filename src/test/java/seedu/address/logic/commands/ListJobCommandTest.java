package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showJobAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_JOB;
import static seedu.address.testutil.TypicalJobs.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author ChengSashankh
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListJobsCommand.
 */
public class ListJobCommandTest {

    private Model model;
    private Model expectedModel;
    private ListJobsCommand listJobsCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listJobsCommand = new ListJobsCommand();
        listJobsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_jobListIsNotFiltered_showsSameList() {
        assertCommandSuccess(listJobsCommand, model, ListJobsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_jobListIsFiltered_showsEverything() {
        showJobAtIndex(model, INDEX_FIRST_JOB);
        assertCommandSuccess(listJobsCommand, model, ListJobsCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
//@@author
