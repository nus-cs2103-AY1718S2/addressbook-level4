package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.smplatform.Link;
import seedu.address.testutil.PersonBuilder;

//@@author Nethergale
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RemovePlatformCommand}.
 */
public class RemovePlatformCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removePlatformsWithPlatformFields_success() throws Exception {
        Set<String> platformSet = new HashSet<>();
        platformSet.add(Link.FACEBOOK_LINK_TYPE);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, platformSet);

        String expectedMessage =
                String.format(RemovePlatformCommand.MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(removePlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removePlatformsWithPlatformFieldsDifferentCasing_success() throws Exception {
        Set<String> platformSet = new HashSet<>();
        platformSet.add(Link.FACEBOOK_LINK_TYPE.toUpperCase());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, platformSet);

        String expectedMessage =
                String.format(RemovePlatformCommand.MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(removePlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removePlatformsWithSomeUnrecognisedPlatformFields_success() throws Exception {
        Set<String> platformSet = new HashSet<>();
        platformSet.add("random");
        platformSet.add(Link.FACEBOOK_LINK_TYPE);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, platformSet);

        String expectedMessage =
                String.format(RemovePlatformCommand.MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(removePlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removePlatformsWithAllUnrecognisedPlatformFields_failure() {
        Set<String> platformSet = new HashSet<>();
        platformSet.add("");
        platformSet.add("hello");
        platformSet.add("tester");
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, platformSet);

        assertCommandFailure(removePlatformCommand, model,RemovePlatformCommand.MESSAGE_PLATFORM_MAP_NOT_EDITED);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, new HashSet<>());

        String expectedMessage =
                String.format(RemovePlatformCommand.MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(removePlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemovePlatformCommand removePlatformCommand = prepareCommand(outOfBoundIndex, new HashSet<>());

        assertCommandFailure(removePlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, new HashSet<>());

        String expectedMessage =
                String.format(RemovePlatformCommand.MESSAGE_REMOVE_PLATFORM_SUCCESS, personToEdit.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        //Update expectedModel to be filtered
        String[] splitName = personToEdit.getName().fullName.split("\\s+");
        Predicate<Person> predicate = new NameContainsKeywordsPredicate(Arrays.asList(splitName[0]));
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(removePlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemovePlatformCommand removePlatformCommand = prepareCommand(outOfBoundIndex, new HashSet<>());

        assertCommandFailure(removePlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, new HashSet<>());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // removeplatform -> first person's platforms removed
        removePlatformCommand.execute();
        undoRedoStack.push(removePlatformCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person's platforms deleted again
        expectedModel.updatePerson(personToEdit, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemovePlatformCommand removePlatformCommand = prepareCommand(outOfBoundIndex, new HashSet<>());

        // execution failed -> removePlatformCommand not pushed into undoRedoStack
        assertCommandFailure(removePlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies a {@code Person} from a filtered list by removing the stated social media platform.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonModified() throws Exception {
        Set<String> platformSet = new HashSet<>();
        platformSet.add(Link.TWITTER_LINK_TYPE);
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        RemovePlatformCommand removePlatformCommand = prepareCommand(INDEX_FIRST_PERSON, platformSet);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms().build();
        // removeplatform -> removes the Twitter platform from the second person in unfiltered person list /
        // first person in filtered person list
        removePlatformCommand.execute();
        undoRedoStack.push(removePlatformCommand);

        // undo -> reverts address book back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToEdit, editedPerson);
        assertNotEquals(personToEdit, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> edits the same second person in unfiltered person list and removing the Twitter platform
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        RemovePlatformCommand removePlatformFirstCommand = prepareCommand(INDEX_FIRST_PERSON, new HashSet<>());
        RemovePlatformCommand removePlatformSecondCommand = prepareCommand(INDEX_SECOND_PERSON, new HashSet<>());

        // same object -> returns true
        assertTrue(removePlatformFirstCommand.equals(removePlatformFirstCommand));

        // same values -> returns true
        RemovePlatformCommand removePlatformFirstCommandCopy = prepareCommand(INDEX_FIRST_PERSON, new HashSet<>());
        assertTrue(removePlatformFirstCommand.equals(removePlatformFirstCommandCopy));

        // different types -> returns false
        assertFalse(removePlatformFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removePlatformFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(removePlatformFirstCommand.equals(removePlatformSecondCommand));
    }

    /**
     * Returns a {@code RemovePlatformCommand} with the parameters {@code index} and {@code platformSet}.
     */
    private RemovePlatformCommand prepareCommand(Index index, Set<String> platformSet) {
        RemovePlatformCommand removePlatformCommand = new RemovePlatformCommand(index, platformSet);
        removePlatformCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removePlatformCommand;
    }

}
