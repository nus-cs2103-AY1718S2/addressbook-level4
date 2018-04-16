package seedu.address.logic.commands;

import static seedu.address.logic.commands.Command.getMessageForEventListShownSummary;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.TitleContainsKeywordsPredicate;

//@@author x3tsunayh

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAllEventsCommand.
 */
public class ListAllEventsCommandTest {

    private Model model;
    private Model expectedModel;
    private ListAllEventsCommand listEventCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        expectedModel =
                new ModelManager(model.getAddressBook(), model.getEventBook(), model.getTaskBook(), new UserPrefs());

        listEventCommand = new ListAllEventsCommand();
        listEventCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listNotFiltered_showsSameList() {
        String expectedMessage = String.format("All "
                + getMessageForEventListShownSummary(expectedModel.getFilteredEventList().size()));
        assertCommandSuccess(listEventCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        ReadOnlyEvent event = model.getEventBook().getEventList().get(0);
        model.updateFilteredEventList(new TitleContainsKeywordsPredicate(Arrays.asList(event.getTitle())));
        String expectedMessage = String.format("All "
                + getMessageForEventListShownSummary(expectedModel.getFilteredEventList().size()));

        // ensures only one event in the filtered event list
        assert model.getFilteredEventList().size() == 1;

        assertCommandSuccess(listEventCommand, model, expectedMessage, expectedModel);
    }
}
