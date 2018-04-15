//author@@ deeheenguyen
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailFilter;
import seedu.address.testutil.Assert;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommand}.
 */
public class ViewCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        EmailFilter firstPredicate =
                new EmailFilter(new Email("abc@example.com"));
        EmailFilter secondPredicate =
                new EmailFilter(new Email("def@example.com"));

        ViewCommand findFirstCommand = new ViewCommand(firstPredicate);
        ViewCommand findSecondCommand = new ViewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
    @Test
    public void executeTest() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        ViewCommand command = prepareCommand("no@example.com");
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private ViewCommand prepareCommand(String userInput) {
        ViewCommand command =
                    new ViewCommand(new EmailFilter(new Email(userInput)));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        Assert.assertThrows(IllegalArgumentException.class, () -> new Email(" "));
        return command;
    }

}
//author@@ deeheenguyen
