package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.Command.getMessageForPersonListShownSummary;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.util.Collections;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DatePredicate;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author meerakanani10
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        DatePredicate firstPredicate =
                new DatePredicate(Collections.singletonList("2018-03-23"));
        DatePredicate secondPredicate =
                new DatePredicate(Collections.singletonList("2018-03-24"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_noPersonMatchesDate() {
        DatePredicate datePredicate =
                new DatePredicate(Collections.singletonList("2019-03-23"));
        FilterCommand filterCommand = prepareFilterCommand(datePredicate);

        try {
            CommandResult actualCommandResult = filterCommand.execute();
            CommandResult expectedCommandResult =
                    new CommandResult(getMessageForPersonListShownSummary(0));
            assertEquals(actualCommandResult.feedbackToUser, expectedCommandResult.feedbackToUser);

        } catch (CommandException | IOException ce) {
            ce.printStackTrace();
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_allAddressesCannotBeFound() {
        DatePredicate datePredicate =
                new DatePredicate(Collections.singletonList("2018-03-25"));
        FilterCommand filterCommand = prepareFilterCommand(datePredicate);
        model.updateFilteredPersonList(datePredicate);
        int numberOfPersonsListed = model.getFilteredPersonList().size();

        try {
            CommandResult actualCommandResult = filterCommand.execute();
            String shown = getMessageForPersonListShownSummary(numberOfPersonsListed) + "\n" +
                    "All the addresses on " +
                    model.getFilteredPersonList().get(0).getDate().toString() +
                    " cannot be found.";
            CommandResult expectedCommandResult =
                    new CommandResult(shown);
            assertEquals(actualCommandResult.feedbackToUser, expectedCommandResult.feedbackToUser);

        } catch (CommandException | IOException ce) {
            ce.printStackTrace();
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_someAddressesCannotBeFound() {
        DatePredicate datePredicate =
                new DatePredicate(Collections.singletonList("2018-03-23"));
        FilterCommand filterCommand = prepareFilterCommand(datePredicate);
        model.updateFilteredPersonList(datePredicate);
        int numberOfPersonsListed = model.getFilteredPersonList().size();

        try {
            CommandResult actualCommandResult = filterCommand.execute();
            String shown = getMessageForPersonListShownSummary(numberOfPersonsListed) + "\n" +
                    "At least one address on " +
                    model.getFilteredPersonList().get(0).getDate().toString() +
                    " cannot be found.";
            CommandResult expectedCommandResult =
                    new CommandResult(shown);
            assertEquals(actualCommandResult.feedbackToUser, expectedCommandResult.feedbackToUser);

        } catch (CommandException | IOException ce) {
            ce.printStackTrace();
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_allAddressesCanBeFound() {
        DatePredicate datePredicate =
                new DatePredicate(Collections.singletonList("2018-03-28"));
        FilterCommand filterCommand = prepareFilterCommand(datePredicate);
        model.updateFilteredPersonList(datePredicate);
        int numberOfPersonsListed = model.getFilteredPersonList().size();

        try {
            CommandResult actualCommandResult = filterCommand.execute();
            CommandResult expectedCommandResult =
                    new CommandResult(getMessageForPersonListShownSummary(numberOfPersonsListed));
            assertEquals(actualCommandResult.feedbackToUser, expectedCommandResult.feedbackToUser);

        } catch (CommandException | IOException ce) {
            ce.printStackTrace();
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    private FilterCommand prepareFilterCommand(DatePredicate datePredicate) {
        FilterCommand filterCommand = new FilterCommand(datePredicate);
        filterCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return filterCommand;
    }
}
