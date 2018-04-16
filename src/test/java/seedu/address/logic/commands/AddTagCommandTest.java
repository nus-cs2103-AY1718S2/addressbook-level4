package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalAddressBook;

//@@author dezhanglee
/**
 * Contains integration tests (interaction with the Model) and unit tests for AddTagCommand.
 */
public class AddTagCommandTest {

    private static final String VALID_TAG_1 = "NUS";
    private static final String VALID_TAG_2 = "CS2103";
    private static final ArrayList<String> toAdd = new ArrayList<String>(Arrays.asList(VALID_TAG_1, VALID_TAG_2));

    private Model model = new ModelManager(TypicalAddressBook.getTypicalAddressBook(), getTypicalEventBook(),
            getTypicalTaskBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        Person editedPerson = new Person(lastPerson);

        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        editedPerson.addTags(tags);

        AddTagCommand addTagCommand = prepareCommand(indexLastPerson, tags);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new TaskBook(model.getTaskBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, editedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new Person(personInFilteredList);

        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, tags);
        editedPerson.addTags(tags);

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new EventBook(model.getEventBook()), new TaskBook(model.getTaskBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTagUnfilteredList_failure() throws Exception {

        Person firstPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, firstPerson.getTags());

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_TAGS_MUST_NOT_EXIST);
    }

    @Test
    public void execute_duplicateTagFilteredList_failure() throws Exception {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        AddTagCommand addTagCommand = prepareCommand(INDEX_FIRST_PERSON, personInList.getTags());

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_TAGS_MUST_NOT_EXIST);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        AddTagCommand addTagCommand = prepareCommand(outOfBoundIndex, tags);

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        AddTagCommand addTagCommand = prepareCommand(outOfBoundIndex, tags);
        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() throws Exception {

        Set<Tag> tags = ParserUtil.parseTags(toAdd);
        Set<Tag> moreTags = ParserUtil.parseTags(Arrays.asList("qwerty", "uiop"));

        final AddTagCommand standardCommand = new AddTagCommand(INDEX_FIRST_PERSON, tags);

        // same values -> returns true
        Set<Tag> copyTags = tags;
        AddTagCommand commandWithSameValues = new AddTagCommand(INDEX_FIRST_PERSON, copyTags);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_SECOND_PERSON, tags)));

        // different tags -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_FIRST_PERSON, moreTags)));
    }

    /**
     * Returns an {@code AddTagCommand} with parameters {@code index} and {@code tags}
     */
    private AddTagCommand prepareCommand(Index index, Set<Tag> tags) {
        AddTagCommand addTagCommand = new AddTagCommand(index, tags);
        addTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addTagCommand;
    }

}
