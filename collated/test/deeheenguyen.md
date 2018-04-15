# deeheenguyen
###### \java\seedu\address\logic\commands\DeleteInterviewCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showInterviewAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERVIEW;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERVIEW;
import static seedu.address.testutil.TypicalInterviews.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.interview.Interview;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteInterviewCommand}.
 */
public class DeleteInterviewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Interview interviewToDelete = model.getFilteredInterviewList().get(INDEX_FIRST_INTERVIEW.getZeroBased());
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);

        String expectedMessage = String.format(DeleteInterviewCommand.MESSAGE_DELETE_INTERVIEW_SUCCESS,
                interviewToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteInterview(interviewToDelete);

        assertCommandSuccess(deleteInterviewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInterviewList().size() + 1);
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(outOfBoundIndex);

        assertCommandFailure(deleteInterviewCommand, model, Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showInterviewAtIndex(model, INDEX_FIRST_INTERVIEW);

        Interview interviewToDelete = model.getFilteredInterviewList().get(INDEX_FIRST_INTERVIEW.getZeroBased());
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);

        String expectedMessage = String.format(DeleteInterviewCommand.MESSAGE_DELETE_INTERVIEW_SUCCESS,
                interviewToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteInterview(interviewToDelete);
        showNoInterview(expectedModel);

        assertCommandSuccess(deleteInterviewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInterviewAtIndex(model, INDEX_FIRST_INTERVIEW);

        Index outOfBoundIndex = INDEX_SECOND_INTERVIEW;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getInterviewList().size());

        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(outOfBoundIndex);

        assertCommandFailure(deleteInterviewCommand, model, Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Interview interviewToDelete = model.getFilteredInterviewList().get(INDEX_FIRST_INTERVIEW.getZeroBased());
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first person deleted
        deleteInterviewCommand.execute();
        undoRedoStack.push(deleteInterviewCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deleteInterview(interviewToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInterviewList().size() + 1);
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteInterviewCommand, model, Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Interview} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted interview in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the interview object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameInterviewDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showInterviewAtIndex(model, INDEX_SECOND_INTERVIEW);
        Interview interviewToDelete = model.getFilteredInterviewList().get(INDEX_FIRST_INTERVIEW.getZeroBased());
        // delete -> deletes second Interview in unfiltered interview list / first interview in filtered interview list
        deleteInterviewCommand.execute();
        undoRedoStack.push(deleteInterviewCommand);

        // undo -> reverts addressbook back to previous state and filtered interview list to show all interviews
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteInterview(interviewToDelete);
        assertNotEquals(interviewToDelete, expectedModel.getFilteredInterviewList()
                .get(INDEX_FIRST_INTERVIEW.getZeroBased()));
        // redo -> deletes same second interview in unfiltered interview list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteInterviewCommand deleteFirstCommand = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);
        DeleteInterviewCommand deleteSecondCommand = prepareInterviewCommand(INDEX_SECOND_INTERVIEW);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteInterviewCommand deleteFirstCommandCopy = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different interview -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteInterviewCommand} with the parameter {@code index}.
     */
    private DeleteInterviewCommand prepareInterviewCommand(Index index) {
        DeleteInterviewCommand deleteInterviewCommand = new DeleteInterviewCommand(index);
        deleteInterviewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteInterviewCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoInterview(Model model) {
        model.updateFilteredInterviewList(p -> false);

        assertTrue(model.getFilteredInterviewList().isEmpty());
    }
}
```
###### \java\seedu\address\logic\commands\FindInterviewCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INTERVIEWS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalInterviews.SE_INTERVIEW;
import static seedu.address.testutil.TypicalInterviews.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.InterviewMatchInterviewee;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindInterviewCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        InterviewMatchInterviewee firstPredicate =
                new InterviewMatchInterviewee ("first");
        InterviewMatchInterviewee secondPredicate =
                new InterviewMatchInterviewee("second");

        FindInterviewCommand findFirstCommand = new FindInterviewCommand(firstPredicate);
        FindInterviewCommand findSecondCommand = new FindInterviewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindInterviewCommand findFirstCommandCopy = new FindInterviewCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noInterviewFound() {
        String expectedMessage = String.format(MESSAGE_INTERVIEWS_LISTED_OVERVIEW, 0);
        FindInterviewCommand command = prepareCommand(" ");
        thrown.expect(IllegalArgumentException.class);
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_oneKeyword_interviewFound() {
        String expectedMessage = String.format(MESSAGE_INTERVIEWS_LISTED_OVERVIEW, 1);
        FindInterviewCommand command = prepareCommand("Kelvin");
        assertCommandSuccess(command, expectedMessage,
                Arrays.asList(SE_INTERVIEW));
    }

    /**
     * Parses {@code userInput} into a {@code FindInterviewCommand}.
     */
    private FindInterviewCommand prepareCommand(String userInput) {
        FindInterviewCommand command =
                new FindInterviewCommand(new InterviewMatchInterviewee(userInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Interview>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindInterviewCommand command, String expectedMessage,
                                      List<Interview> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredInterviewList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\ListInterviewCommandTest.java
``` java
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
```
###### \java\seedu\address\logic\parser\AddInterviewCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_SE;
import static seedu.address.logic.commands.CommandTestUtil.INTERVIEWEE_DESC_SE;
import static seedu.address.logic.commands.CommandTestUtil.INTERVIEW_LOCATION_DESC_SE;
import static seedu.address.logic.commands.CommandTestUtil.INTERVIEW_TITLE_DESC_SE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEWEE_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_LOCATION_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_TITLE_SE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddInterviewCommand;
import seedu.address.model.interview.Interview;
import seedu.address.testutil.InterviewBuilder;

public class AddInterviewCommandParserTest {
    private AddInterviewCommandParser parser = new AddInterviewCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Interview expectedInterview = new InterviewBuilder()
                .withInterviewTitle(VALID_INTERVIEW_TITLE_SE)
                .withInterviewee(VALID_INTERVIEWEE_SE)
                .withDate(VALID_DATE_SE)
                .withInterviewLocation(VALID_INTERVIEW_LOCATION_SE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INTERVIEW_TITLE_DESC_SE
                + INTERVIEWEE_DESC_SE +  DATE_DESC_SE + INTERVIEW_LOCATION_DESC_SE,
                new AddInterviewCommand(expectedInterview));
    }
}
```
###### \java\seedu\address\logic\parser\DeleteInterviewCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteInterviewCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteInterviewCommandParserTest {

    private DeleteInterviewCommandParser parser = new DeleteInterviewCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteInterviewCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteInterviewCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ViewCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailFilter;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_throwsIllegalException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewCommand() {
        // no leading and trailing whitespaces
        String example = "john@example.com";
        Email email = new Email(example);
        ViewCommand expectedViewCommand =
                new ViewCommand(new EmailFilter(email));
        assertParseSuccess(parser, "john@example.com", expectedViewCommand);
    }
}
```
###### \java\seedu\address\model\interview\DateTest.java
``` java
package seedu.address.model.interview;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidDate = "0.0.0";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date number
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid phone numbers
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("91")); // less than 3 numbers
        assertFalse(Date.isValidDate("phone")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("9312 1534")); // spaces within digits
        assertFalse(Date.isValidDate("31.14.2018")); // invalid month

        // valid phone numbers
        assertTrue(Date.isValidDate("01.01.2018")); // exactly 3 numbers
        assertTrue(Date.isValidDate("31.12.2018"));
        assertTrue(Date.isValidDate("28.02.2018")); // long phone numbers
    }
}
```
###### \java\seedu\address\model\interview\InterviewMatchIntervieweeTest.java
``` java
package seedu.address.model.interview;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.InterviewBuilder;

public class InterviewMatchIntervieweeTest {

    @Test
    public void equals() {
        String firstPredicateKeywordList = "first";
        String secondPredicateKeywordList = "second";

        InterviewMatchInterviewee firstPredicate;
        firstPredicate = new InterviewMatchInterviewee(firstPredicateKeywordList);
        InterviewMatchInterviewee secondPredicate;
        secondPredicate = new InterviewMatchInterviewee(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InterviewMatchInterviewee firstPredicateCopy;
        firstPredicateCopy = new InterviewMatchInterviewee(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchInterview_returnsTrue() {
        // One keyword
        InterviewMatchInterviewee predicate;
        predicate = new InterviewMatchInterviewee("John");
        assertTrue(predicate.test(new InterviewBuilder().withInterviewee("John").build()));
    }

    @Test
    public void test_matchDoesNotContainKeywords_returnsFalse() {

        InterviewMatchInterviewee predicate;
        predicate = new InterviewMatchInterviewee("Alice");
        assertFalse(predicate.test(new InterviewBuilder().withInterviewee("John").build()));
    }
}
```
###### \java\seedu\address\model\interview\InterviewTitleTest.java
``` java
package seedu.address.model.interview;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class InterviewTitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new InterviewTitle(null));
    }

    @Test
    public void constructor_invalidJobTitle_throwsIllegalArgumentException() {
        String invalidInterviewTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new InterviewTitle(invalidInterviewTitle));
    }

    @Test
    public void isValidInterviewTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> InterviewTitle.isValidTitle(null));

        // invalid name
        assertFalse(InterviewTitle.isValidTitle("")); // empty string
        assertFalse(InterviewTitle.isValidTitle(" ")); // spaces only
        assertFalse(InterviewTitle.isValidTitle("^")); // only non-alphanumeric characters
        assertFalse(InterviewTitle.isValidTitle("$$*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(InterviewTitle.isValidTitle("backend interview")); // alphabets only
        assertTrue(InterviewTitle.isValidTitle("11111")); // numbers only
        assertTrue(InterviewTitle.isValidTitle("SE interview")); // alphanumeric characters
        assertTrue(InterviewTitle.isValidTitle("INTERNSHIP INTERNVIEW")); // with capital letters
    }
}

```
###### \java\seedu\address\model\person\EmailFilterTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailFilterTest {

    @Test
    public void equals() {
        Email firstEmail = new Email("abc@example.com");
        Email secondEmail = new Email("def@example.com");

        EmailFilter firstPredicate = new EmailFilter(firstEmail);
        EmailFilter secondPredicate = new EmailFilter(secondEmail);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailFilter firstPredicateCopy = new EmailFilter(firstEmail);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void tests() {
        EmailFilter predicate = new EmailFilter(new Email("abc@example.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("abc@example.com").build()));
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedInterviewTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedInterview.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalInterviews.SE_INTERVIEW;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.interview.Date;
import seedu.address.model.interview.InterviewLocation;
import seedu.address.model.interview.InterviewTitle;
import seedu.address.model.person.Name;
import seedu.address.testutil.Assert;

public class XmlAdaptedInterviewTest {
    private static final String INVALID_INTERTVIEW_TITLE = "R@chel";
    private static final String INVALID_DATE = "+651234";
    private static final String INVALID_INTERVIEW_LOCATION = " ";
    private static final String INVALID_INTERVIEWEE = "@@__";

    private static final String VALID_INTERVIEW_TITLE = SE_INTERVIEW.getInterviewTitle().toString();
    private static final String VALID_DATE = SE_INTERVIEW.getDate().toString();
    private static final String VALID_INTERVIEWEE = SE_INTERVIEW.getInterviewee().toString();
    private static final String VALID_INTERVIEW_LOCATION = SE_INTERVIEW.getInterviewLocation().toString();

    @Test
    public void toModelType_validInterviewDetails_returnsInterview() throws Exception {
        XmlAdaptedInterview interview = new XmlAdaptedInterview(SE_INTERVIEW);
        assertEquals(SE_INTERVIEW, interview.toModelType());
    }

    @Test
    public void toModelType_invalidInterviewTitle_throwsIllegalValueException() {
        XmlAdaptedInterview interview =
                new XmlAdaptedInterview(INVALID_INTERTVIEW_TITLE, VALID_INTERVIEWEE, VALID_DATE,
                        VALID_INTERVIEW_LOCATION);
        String expectedMessage = InterviewTitle.MESSAGE_TITLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullInterviewTitle_throwsIllegalValueException() {
        XmlAdaptedInterview interview = new XmlAdaptedInterview(null, VALID_INTERVIEWEE,
                VALID_DATE, VALID_INTERVIEW_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, InterviewTitle.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_invalidInterviewee_throwsIllegalValueException() {
        XmlAdaptedInterview interview =
                new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, INVALID_INTERVIEWEE, VALID_DATE,
                        VALID_INTERVIEW_LOCATION);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullInterviewee_throwsIllegalValueException() {
        XmlAdaptedInterview interview = new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, null, VALID_DATE,
                VALID_INTERVIEW_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedInterview interview =
                new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, VALID_INTERVIEWEE, INVALID_DATE,
                        VALID_INTERVIEW_LOCATION);
        String expectedMessage = Date.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedInterview interview = new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, VALID_INTERVIEWEE,
                null, VALID_INTERVIEW_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_invalidInterviewLocation_throwsIllegalValueException() {
        XmlAdaptedInterview interview =
                new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, VALID_INTERVIEWEE, VALID_DATE,
                        INVALID_INTERVIEW_LOCATION);
        String expectedMessage = InterviewLocation.MESSAGE_LOCATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

    @Test
    public void toModelType_nullInterviewLocation_throwsIllegalValueException() {
        XmlAdaptedInterview interview = new XmlAdaptedInterview(VALID_INTERVIEW_TITLE, VALID_INTERVIEWEE, VALID_DATE,
                null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, InterviewLocation.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, interview::toModelType);
    }

}
```
###### \java\seedu\address\testutil\InterviewBuilder.java
``` java
package seedu.address.testutil;

import seedu.address.model.interview.Date;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.InterviewLocation;
import seedu.address.model.interview.InterviewTitle;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building Person objects.
 */
public class InterviewBuilder {

    public static final String DEFAULT_INTERVIEW_TITLE = "Internship Interview";
    public static final String DEFAULT_INTERVIEWEE = "David";
    public static final String DEFAULT_INNTERVIEW_LOCATION = "NUS";
    public static final String DEFAULT_DATE = "01.01.2018";

    private Name interviewee;
    private InterviewTitle interviewTitle;
    private InterviewLocation interviewLocation;
    private Date date;

    public InterviewBuilder() {
        interviewee = new Name(DEFAULT_INTERVIEWEE);
        interviewTitle = new InterviewTitle(DEFAULT_INTERVIEW_TITLE);
        interviewLocation = new InterviewLocation(DEFAULT_INNTERVIEW_LOCATION);
        date = new Date(DEFAULT_DATE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public InterviewBuilder(Interview interviewToCopy) {
        interviewTitle = interviewToCopy.getInterviewTitle();
        interviewee = interviewToCopy.getInterviewee();
        interviewLocation = interviewToCopy.getInterviewLocation();
        date = interviewToCopy.getDate();
    }

    /**
     * Sets the {@code Interviewee} of the {@code Interview} that we are building.
     */
    public InterviewBuilder withInterviewee(String name) {
        this.interviewee = new Name(name);
        return this;
    }


    /**
     * Sets the {@code InterviewLocation} of the {@code Person} that we are building.
     */
    public InterviewBuilder withInterviewLocation(String interviewLocation) {
        this.interviewLocation = new InterviewLocation(interviewLocation);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Interview} that
     * we are building.
     */
    public InterviewBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code InterviewTitle} of the {@code Interview} that we are building.
     */
    public InterviewBuilder withInterviewTitle(String interviewTitle) {
        this.interviewTitle = new InterviewTitle(interviewTitle);
        return this;
    }

    public Interview build() {
        return new Interview(interviewTitle, interviewee, date, interviewLocation);
    }

}
//author @deeheenguyen
```
###### \java\seedu\address\testutil\TypicalInterviews.java
``` java
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;

/**
 * A utility class containing a list of {@code Interview} objects to be used in tests.
 */
public class TypicalInterviews {

    public static final Interview SE_INTERVIEW = new InterviewBuilder().withInterviewTitle("SE interview")
            .withInterviewee("Kelvin").withDate("01.02.2018")
            .withInterviewLocation("NUS").build();
    public static final Interview FINANCE_INTERVIEW = new InterviewBuilder().withInterviewTitle("Finance interview")
            .withInterviewee("Bob").withDate("01.03.2018")
            .withInterviewLocation("SOC").build();
    public static final Interview TECH_INTERVIEW = new InterviewBuilder().withInterviewTitle("Tech interview")
            .withInterviewee("Lucian").withDate("01.04.2018")
            .withInterviewLocation("Science").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalInterviews() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Interview interview : getTypicalInterviews()) {
            try {
                ab.addInterview(interview);
            } catch (DuplicateInterviewException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Interview> getTypicalInterviews() {
        return new ArrayList<>(Arrays.asList(SE_INTERVIEW, FINANCE_INTERVIEW, TECH_INTERVIEW));
    }
}
```
