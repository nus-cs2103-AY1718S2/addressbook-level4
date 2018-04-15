package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.ShowPanelRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.InfoPanel;
import seedu.address.ui.PdfPanel;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author Ang-YC
public class ShowCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_resume_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.setSelectedPerson(TypicalPersons.ALICE);

        Command command = new ShowCommand(ShowCommand.Panel.RESUME);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(command, model, ShowCommand.MESSAGE_SHOW_SUCCESS, model);

        BaseEvent baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(baseEvent instanceof ShowPanelRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);

        ShowPanelRequestEvent event = (ShowPanelRequestEvent) baseEvent;
        assertTrue(event.getRequestedPanel().equals(PdfPanel.PANEL_NAME));
    }

    @Test
    public void execute_info_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.setSelectedPerson(TypicalPersons.ALICE);

        Command command = new ShowCommand(ShowCommand.Panel.INFO);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(command, model, ShowCommand.MESSAGE_SHOW_SUCCESS, model);

        BaseEvent baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(baseEvent instanceof ShowPanelRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);

        ShowPanelRequestEvent event = (ShowPanelRequestEvent) baseEvent;
        assertTrue(event.getRequestedPanel().equals(InfoPanel.PANEL_NAME));
    }

    @Test
    public void execute_noResume_throws() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person aliceWithoutResume = new PersonBuilder(TypicalPersons.ALICE).withResumeLazy(null).build();
        model.updatePerson(TypicalPersons.ALICE, aliceWithoutResume);
        model.setSelectedPerson(aliceWithoutResume);

        // Remove PersonChangedEvent
        eventsCollectorRule.eventsCollector.reset();

        Command command = new ShowCommand(ShowCommand.Panel.RESUME);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(command, model, ShowCommand.MESSAGE_RESUME_NA);

        BaseEvent baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(baseEvent == null);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 0);
    }

    @Test
    public void execute_noSelected_throws() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.setSelectedPerson(null);
        // No one is selected

        Command command = new ShowCommand(ShowCommand.Panel.INFO);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(command, model, ShowCommand.MESSAGE_NOT_SELECTED);

        BaseEvent baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(baseEvent == null);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 0);

        command = new ShowCommand(ShowCommand.Panel.RESUME);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(command, model, ShowCommand.MESSAGE_NOT_SELECTED);

        baseEvent = eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(baseEvent == null);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 0);
    }

    @Test
    public void equals() {
        ShowCommand command = new ShowCommand(ShowCommand.Panel.RESUME);
        ShowCommand commandWithSameValues = new ShowCommand(ShowCommand.Panel.RESUME);

        // same values -> returns true
        assertTrue(command.equals(commandWithSameValues));

        // Same object -> returns true
        assertTrue(command.equals(command));

        // Null -> returns false
        assertFalse(command.equals(null));

        // Different types -> returns false
        assertFalse(command.equals(new ClearCommand()));

        // Different panel -> returns false
        assertFalse(command.equals(new ShowCommand(ShowCommand.Panel.INFO)));
    }
}
