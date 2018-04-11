# chenchongsong
###### /java/seedu/address/logic/parser/SplitCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SplitCommand;

public class SplitCommandParserTest {

    private static String validIndex = "1";
    private static String validIndices1 = "1 2 3";
    private static String validIndices2 = "1 1 1 2";
    private static String invalidIndex1 = "a";
    private static String invalidIndex2 = "0";
    private static String validBill1 = PREFIX_MONEY + "100.00";
    private static String validBill2 = PREFIX_MONEY + "-0.12";

    private SplitCommandParser parser = new SplitCommandParser();

    @Test
    public void parse_validArgsSingleIndex_returnsSplitCommand() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, validIndex + " " + validBill1,
                new SplitCommand(indices, 100.00));
    }

    @Test
    public void parse_validArgsMultipleIndex_returnsSplitCommand() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);
        indices.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, validIndices1 + " " + validBill2,
                new SplitCommand(indices, -0.12));

        // In this case, the first person would take 3/4 of that bill
        // and the second person would take 1/4 of that bill
        indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, validIndices2 + " " + validBill2,
                new SplitCommand(indices, -0.12));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, invalidIndex1 + " " + validBill1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));

        assertParseFailure(parser, invalidIndex2 + " " + validBill2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));
    }

}
```
###### /java/seedu/address/logic/commands/RemoveTagCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.RemoveTagCommand.createEditedPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration test (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class RemoveTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(0);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(personToEdit).build();
        RemoveTagCommand removeTagCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        Person tagRemovedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getMoney(),
                new HashSet<>()
        );
        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagRemovedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personToEdit, tagRemovedPerson);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
        // lastPerson contains original info

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person tagRemovedPerson = personInList
                .withName(lastPerson.getName().toString())
                .withPhone(lastPerson.getPhone().toString())
                .withEmail(lastPerson.getEmail().toString())
                .withAddress(lastPerson.getAddress().toString())
                .withMoney(lastPerson.getMoney().toString())
                .withoutTags().build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTags(lastPerson.getTags()).build();
        RemoveTagCommand removeTagCommand = prepareCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(RemoveTagCommand.MESSAGE_REMOVE_TAG_SUCCESS, tagRemovedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, tagRemovedPerson);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTags(VALID_TAG_HUSBAND).build();
        RemoveTagCommand removeTagCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Remove Tags from filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemoveTagCommand removeTagCommand = prepareCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build());

        assertCommandFailure(removeTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(personToEdit).build();
        // descriptor contains all tags to be removed

        Person tagRemovedPerson = createEditedPerson(personToEdit, descriptor);

        RemoveTagCommand removeTagCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // remove tags -> first person tags removed
        removeTagCommand.execute();
        undoRedoStack.push(removeTagCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person's tag removed again
        expectedModel.updatePerson(personToEdit, tagRemovedPerson);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(0);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(personToEdit).build();

        final RemoveTagCommand standardCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(descriptor);
        RemoveTagCommand commandWithSameValues = prepareCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemoveTagCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemoveTagCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemoveTagCommand prepareCommand(Index index, EditPersonDescriptor descriptor) {
        RemoveTagCommand removeTagCommand = new RemoveTagCommand(index, descriptor);
        removeTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeTagCommand;
    }

}
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_BILL = " " + PREFIX_MONEY + "100.00";
    public static final String INVALID_BILL = " " + PREFIX_MONEY + "100k";
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Copy a Tag Set {@code toCopy} and set the copy into the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(Set<Tag> toCopy) {
        descriptor.setTags(toCopy);
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Set an empty set {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withoutTags() {
        this.tags = SampleDataUtil.getTagSet();
        return this;
    }
```
