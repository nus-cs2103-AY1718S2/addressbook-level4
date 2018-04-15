package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.MatchedClients.getMatchedStudentAddressBook;
import static seedu.address.testutil.MatchedClients.getMatchedTutorAddressBook;
import static seedu.address.testutil.TypicalCategories.CATEGORY_FIRST_PERSON;
import static seedu.address.testutil.TypicalCategories.CATEGORY_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.UnmatchedClients.getUnmatchedAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Category;


//@@author Zhu-Jiahui
/**
 * Contains integration tests (interaction with the Model) for {@code MatchCommand}.
 */
public class MatchCommandTest {
    private Model model;
    private Model expectedStudentModel;
    private Model expectedTutorModel;
    private MatchCommand matchCommandForStudentList;
    private MatchCommand matchCommandForTutorList;

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");

    @Before
    public void setup() {
        model = new ModelManager(getUnmatchedAddressBook(), new UserPrefs());
        expectedTutorModel = new ModelManager(getMatchedTutorAddressBook(), new UserPrefs());
        expectedStudentModel = new ModelManager(getMatchedStudentAddressBook(), new UserPrefs());

        matchCommandForStudentList = new MatchCommand(INDEX_FIRST_PERSON, studentCategory);
        matchCommandForStudentList.setData(model, new CommandHistory(), new UndoRedoStack());
        matchCommandForTutorList = new MatchCommand(INDEX_FIRST_PERSON, tutorCategory);
        matchCommandForTutorList.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeMatchCommand() {
        assertCommandSuccessForStudentList(matchCommandForStudentList,
                MatchCommand.getMessageForClientListShownSummary(1, 2));
        assertCommandSuccessForTutorList(matchCommandForTutorList,
                MatchCommand.getMessageForClientListShownSummary(3, 1));
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForStudentList(MatchCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedStudentModel.getFilteredStudentList(), model.getFilteredStudentList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForTutorList(MatchCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedTutorModel.getFilteredTutorList(), model.getFilteredTutorList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
    @Test
    public void equals() {

        MatchCommand matchFirstCommand = new MatchCommand(INDEX_FIRST_PERSON, CATEGORY_FIRST_PERSON);
        MatchCommand matchSecondCommand = new MatchCommand(INDEX_SECOND_PERSON, CATEGORY_SECOND_PERSON);

        // same object -> returns true
        assertTrue(matchFirstCommand.equals(matchFirstCommand));

        // same values -> returns true
        MatchCommand matchFirstCommandCopy = new MatchCommand(INDEX_FIRST_PERSON, CATEGORY_SECOND_PERSON);
        assertTrue(matchFirstCommand.equals(matchFirstCommandCopy));

        // different types -> returns false
        assertFalse(matchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(matchFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(matchFirstCommand.equals(matchSecondCommand));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private MatchCommand prepareCommand(Index index, Category category) {
        MatchCommand command =
                new MatchCommand(index, category);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
