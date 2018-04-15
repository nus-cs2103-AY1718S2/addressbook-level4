package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.SortedClients.getSortedBySubjectAddressBook;
import static seedu.address.testutil.UnsortedClients.getUnsortedAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Category;

//@@author olimhc
/**
 * Contains integration tests (interaction with the Model) for {@code SortBySubjectCommand}.
 */
public class SortBySubjectCommandTest {
    private Model model;
    private Model expectedModel;
    private SortBySubjectCommand sortBySubjectCommandForStudentList;
    private SortBySubjectCommand sortBySubjectCommandForTutorList;

    private final Category studentCategory = new Category("s");
    private final Category tutorCategory = new Category("t");

    @Before
    public void setup() {
        model = new ModelManager(getUnsortedAddressBook(), new UserPrefs());
        expectedModel =  new ModelManager(getSortedBySubjectAddressBook(), new UserPrefs());

        sortBySubjectCommandForStudentList = new SortBySubjectCommand(studentCategory);
        sortBySubjectCommandForStudentList.setData(model, new CommandHistory(), new UndoRedoStack());
        sortBySubjectCommandForTutorList = new SortBySubjectCommand(tutorCategory);
        sortBySubjectCommandForTutorList.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeSortUnsortedToSortedBySubject() {
        assertCommandSuccessForStudentList(sortBySubjectCommandForStudentList, (
                SortBySubjectCommand.MESSAGE_SUCCESS_STUDENT + SortBySubjectCommand.MESSAGE_SORT_DESC));
        assertCommandSuccessForTutorList(sortBySubjectCommandForTutorList, (SortBySubjectCommand.MESSAGE_SUCCESS_TUTOR
                + SortBySubjectCommand.MESSAGE_SORT_DESC));
    }

    /**
     * Asserts that the code is successfully executed.
     * Asserts the command feedback is equal to expectedMessage.
     * Asserts actual FilteredList is equal to the expected FilteredList
     * Asserts the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccessForStudentList(SortBySubjectCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedModel.getFilteredStudentList(), model.getFilteredStudentList());
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
    private void assertCommandSuccessForTutorList(SortBySubjectCommand command, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedModel.getFilteredTutorList(), model.getFilteredTutorList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
