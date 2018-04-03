package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.BirthdaysCommand.SHOWING_BIRTHDAY_MESSAGE;
import static seedu.address.logic.commands.BirthdaysCommand.SHOWING_BIRTHDAY_NOTIFICATION;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.BirthdayListEvent;
import seedu.address.commons.events.ui.BirthdayNotificationEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author AzuraAiR
public class BirthdaysCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_emptyBirthdays_birthdaysFailure() {
        thrown.expect(NullPointerException.class);
        new BirthdaysCommand(false).execute();
    }


    @Test
    public void execute_birthdaysWithoutToday_birthdaysSuccess() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult result = prepareCommand(false, model).execute();

        assertEquals(SHOWING_BIRTHDAY_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BirthdayListEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_birthdaysWithToday_birthdaysSuccess() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult result = prepareCommand(true, model).execute();

        assertEquals(SHOWING_BIRTHDAY_NOTIFICATION, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BirthdayNotificationEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * Generates a new {@code ClearCommand} which upon execution, clears the contents in {@code model}.
     */
    private BirthdaysCommand prepareCommand(boolean today, Model model) {
        BirthdaysCommand command = new BirthdaysCommand(today);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
