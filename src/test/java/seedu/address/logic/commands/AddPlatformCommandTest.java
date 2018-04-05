package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.SMP_MAP_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SMP_MAP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.smplatform.Facebook;
import seedu.address.model.smplatform.Link;
import seedu.address.model.smplatform.SocialMediaPlatform;
import seedu.address.model.smplatform.SocialMediaPlatformBuilder;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.PersonBuilder;

//@@author Nethergale
public class AddPlatformCommandTest {
    public static final String LINK_STUB = "www.facebook.com/carl.kz";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPlatformLink_failure() {
        String invalidLink = "www.google.com";
        Map<String, SocialMediaPlatform> smpMap = new HashMap<>();
        smpMap.put(Link.FACEBOOK_LINK_TYPE, new Facebook(new Link(invalidLink)));

        AddPlatformCommand addPlatformCommand = prepareCommand(INDEX_THIRD_PERSON, smpMap);

        assertCommandFailure(addPlatformCommand, model, SocialMediaPlatformBuilder.MESSAGE_BUILD_ERROR);
    }

    @Test
    public void execute_addPlatformUnfilteredList_success() throws Exception {
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(thirdPerson).withPlatforms(LINK_STUB).build();

        AddPlatformCommand addPlatformCommand =
                prepareCommand(INDEX_THIRD_PERSON, editedPerson.getSocialMediaPlatformMap());

        String expectedMessage = String.format(AddPlatformCommand.MESSAGE_ADD_PLATFORM_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(thirdPerson, editedPerson);

        assertCommandSuccess(addPlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_retainAndAddPlatformsUnfilteredList_success() throws Exception {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Set<String> linkSet = new LinkedHashSet<>();
        linkSet.add(LINK_STUB);
        for (String key : secondPerson.getSocialMediaPlatformMap().keySet()) {
            linkSet.add(secondPerson.getSocialMediaPlatformMap().get(key).getLink().value);
        }
        Person editedPerson = new PersonBuilder(secondPerson)
                .withPlatforms(linkSet.toArray(new String[linkSet.size()])).build();

        AddPlatformCommand addPlatformCommand =
                prepareCommand(INDEX_SECOND_PERSON, editedPerson.getSocialMediaPlatformMap());

        String expectedMessage = String.format(AddPlatformCommand.MESSAGE_ADD_PLATFORM_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(secondPerson, editedPerson);

        assertCommandSuccess(addPlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearAllPlatformsUnfilteredList_success() throws Exception {
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(thirdPerson).withPlatforms("").build();

        AddPlatformCommand addPlatformCommand =
                prepareCommand(INDEX_THIRD_PERSON, editedPerson.getSocialMediaPlatformMap());

        String expectedMessage =
                String.format(AddPlatformCommand.MESSAGE_ADD_PLATFORM_CLEAR_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(thirdPerson, editedPerson);

        assertCommandSuccess(addPlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addPlatformFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_THIRD_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withPlatforms(LINK_STUB).build();

        AddPlatformCommand addPlatformCommand =
                prepareCommand(INDEX_FIRST_PERSON, editedPerson.getSocialMediaPlatformMap());

        String expectedMessage = String.format(AddPlatformCommand.MESSAGE_ADD_PLATFORM_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(firstPerson, editedPerson);

        assertCommandSuccess(addPlatformCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPlatformCommand addPlatformCommand = prepareCommand(outOfBoundIndex, SMP_MAP_BOB);

        assertCommandFailure(addPlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book.
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        //Ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddPlatformCommand addPlatformCommand = prepareCommand(outOfBoundIndex, SMP_MAP_AMY);

        assertCommandFailure(addPlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPlatforms(LINK_STUB).build();
        AddPlatformCommand addPlatformCommand =
                prepareCommand(INDEX_FIRST_PERSON, editedPerson.getSocialMediaPlatformMap());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // addplatform -> first person platforms changed
        addPlatformCommand.execute();
        undoRedoStack.push(addPlatformCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person modified again
        expectedModel.updatePerson(personToEdit, editedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPlatformCommand addPlatformCommand = prepareCommand(outOfBoundIndex, SMP_MAP_AMY);

        // execution failed -> addPlatformCommand not pushed into undoRedoStack
        assertCommandFailure(addPlatformCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Modifies a person's social media platform from a filtered list.
     * 2. Undo the modification.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously modified person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the modification. This ensures {@code RedoCommand} modifies the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonModified() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<String> linkSet = new LinkedHashSet<>();
        linkSet.add(LINK_STUB);
        for (String key : firstPerson.getSocialMediaPlatformMap().keySet()) {
            linkSet.add(firstPerson.getSocialMediaPlatformMap().get(key).getLink().toString());
        }

        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        AddPlatformCommand addPlatformCommand = prepareCommand(INDEX_FIRST_PERSON,
                SampleDataUtil.getSocialMediaPlatformMap(linkSet.toArray(new String[linkSet.size()])));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit)
                .withPlatforms(linkSet.toArray(new String[linkSet.size()])).build();

        // addplatform -> modifies second person in unfiltered person list / first person in filtered person list
        addPlatformCommand.execute();
        undoRedoStack.push(addPlatformCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updatePerson(personToEdit, editedPerson);
        assertNotEquals(personToEdit, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> modifies same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        Map<String, Link> amyLinkMap = new HashMap<>();
        Map<String, Link> bobLinkMap = new HashMap<>();
        for (String key : SMP_MAP_AMY.keySet()) {
            amyLinkMap.put(key, SMP_MAP_AMY.get(key).getLink());
        }
        for (String key : SMP_MAP_BOB.keySet()) {
            amyLinkMap.put(key, SMP_MAP_BOB.get(key).getLink());
        }

        final AddPlatformCommand standardCommand = new AddPlatformCommand(INDEX_FIRST_PERSON, amyLinkMap);

        // same values -> returns true
        AddPlatformCommand commandWithSameValues = new AddPlatformCommand(INDEX_FIRST_PERSON, amyLinkMap);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddPlatformCommand(INDEX_SECOND_PERSON, amyLinkMap)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new AddPlatformCommand(INDEX_FIRST_PERSON, bobLinkMap)));
    }

    /**
     * Returns an {@code AddPlatformCommand} with parameters {@code index} and {@code Map<String, SocialMediaPlatform>}.
     */
    private AddPlatformCommand prepareCommand(Index index, Map<String, SocialMediaPlatform> smpMap) {
        Map<String, Link> linkMap = new HashMap<>();
        for (String key : smpMap.keySet()) {
            linkMap.put(key, smpMap.get(key).getLink());
        }
        AddPlatformCommand addPlatformCommand = new AddPlatformCommand(index, linkMap);
        addPlatformCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPlatformCommand;
    }
}
