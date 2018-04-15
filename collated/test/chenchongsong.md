# chenchongsong
###### /java/seedu/address/logic/parser/SortCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {


    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {

        assertParseSuccess(parser, PREFIX_ADDRESS + SortCommand.SORT_ORDER_ASCENDING,
                new SortCommand(PREFIX_ADDRESS.toString(), SortCommand.SORT_ORDER_ASCENDING));

        assertParseSuccess(parser, PREFIX_PHONE + SortCommand.SORT_ORDER_DESCENDING,
                new SortCommand(PREFIX_PHONE.toString(), SortCommand.SORT_ORDER_DESCENDING));

        assertParseSuccess(parser, PREFIX_EMAIL + SortCommand.SORT_ORDER_ASCENDING,
                new SortCommand(PREFIX_EMAIL.toString(), SortCommand.SORT_ORDER_ASCENDING));

        assertParseSuccess(parser, PREFIX_NAME + SortCommand.SORT_ORDER_DESCENDING,
                new SortCommand(PREFIX_NAME.toString(), SortCommand.SORT_ORDER_DESCENDING));

        assertParseSuccess(parser, PREFIX_MONEY + SortCommand.SORT_ORDER_ASCENDING,
                new SortCommand(PREFIX_MONEY.toString(), SortCommand.SORT_ORDER_ASCENDING));

        assertParseSuccess(parser, PREFIX_TAG + SortCommand.SORT_ORDER_DESCENDING,
                new SortCommand(PREFIX_TAG.toString(), SortCommand.SORT_ORDER_DESCENDING));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // duplicate
        assertParseFailure(parser, "n/asc e/asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // typo
        assertParseFailure(parser, "e/ascc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // typo
        assertParseFailure(parser, "tt/desc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
```
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

    private static final String VALID_INDEX = "1";
    private static final String VALID_INDICES_1 = "1 2 3";
    private static final String VALID_INDICES_2 = "1 1 1 2";
    private static final String INVALID_INDEX_1 = "a";
    private static final String INVALID_INDEX_2 = "0";
    private static final String VALID_BILL_1 = PREFIX_MONEY + "100.00";
    private static final String VALID_BILL_2 = PREFIX_MONEY + "0.12";

    private SplitCommandParser parser = new SplitCommandParser();

    @Test
    public void parse_validArgsSingleIndex_returnsSplitCommand() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, VALID_INDEX + " " + VALID_BILL_1,
                new SplitCommand(indices, 100.00));
    }

    @Test
    public void parse_validArgsMultipleIndex_returnsSplitCommand() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);
        indices.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, VALID_INDICES_1 + " " + VALID_BILL_2,
                new SplitCommand(indices, 0.12));

        // In this case, the first person would take 3/4 of that bill
        // and the second person would take 1/4 of that bill
        indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, VALID_INDICES_2 + " " + VALID_BILL_2,
                new SplitCommand(indices, 0.12));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INDEX_1 + " " + VALID_BILL_1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));

        assertParseFailure(parser, INVALID_INDEX_2 + " " + VALID_BILL_2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));
    }

}
```
###### /java/seedu/address/logic/parser/ItemShowCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ItemShowCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class ItemShowCommandParserTest {

    private ItemShowCommandParser parser = new ItemShowCommandParser();

    @Test
    public void parse_validArgs_returnsItemShowCommand() {
        assertParseSuccess(parser, "1", new ItemShowCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemShowCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/ItemDeleteCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ItemDeleteCommand;

public class ItemDeleteCommandParserTest {

    private ItemDeleteCommandParser parser = new ItemDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsItemDeleteCommand() {
        assertParseSuccess(parser, "1 1",
                new ItemDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_ITEM));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/ItemAddCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ItemAddCommand;

public class ItemAddCommandParserTest {

    public static final String VALID_ITEM_NAME = "Taxi Fare";
    public static final String VALID_ITEM_VALUE = "10.23";
    public static final String INVALID_ITEM_NAME = "Taxi*&(Fare)";
    public static final String INVALID_ITEM_VALUE = "10k";


    private ItemAddCommandParser parser = new ItemAddCommandParser();

    @Test
    public void parse_validArgs_returnsItemAddCommand() {
        assertParseSuccess(parser, "1 " + PREFIX_NAME + VALID_ITEM_NAME + " " + PREFIX_MONEY + VALID_ITEM_VALUE,
                new ItemAddCommand(INDEX_FIRST_PERSON, VALID_ITEM_NAME, VALID_ITEM_VALUE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // 0 as targetIndex
        assertParseFailure(parser, "0 " + PREFIX_NAME + VALID_ITEM_NAME + " " + PREFIX_MONEY + VALID_ITEM_VALUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));

        // no prefix "n/"
        assertParseFailure(parser, "1 " + VALID_ITEM_NAME + " " + PREFIX_MONEY + VALID_ITEM_VALUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));

        // no prefix "m/"
        assertParseFailure(parser, "1 " + PREFIX_NAME + VALID_ITEM_NAME + " " + VALID_ITEM_VALUE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));

        // invalid item name
        assertParseFailure(parser, "1 " + PREFIX_NAME + INVALID_ITEM_NAME + " " + PREFIX_MONEY + VALID_ITEM_VALUE,
                ItemAddCommand.MESSAGE_INVALID_ARGUMENT);

        // invalid item value
        assertParseFailure(parser, "1 " + PREFIX_NAME + VALID_ITEM_NAME + " " + PREFIX_MONEY + INVALID_ITEM_VALUE,
                ItemAddCommand.MESSAGE_INVALID_ARGUMENT);
    }
}
```
###### /java/seedu/address/logic/parser/RemoveTagCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

public class RemoveTagCommandParserTest {

    public static final String TAG_NAME_1 = "friends";
    public static final String TAG_NAME_2 = "owesMoney";

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_validArgsSingleTag_returnsRemoveTagCommand() {
        Set<Tag> tagsToRemoved = new HashSet<>();
        tagsToRemoved.add(new Tag(TAG_NAME_1));

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setTags(tagsToRemoved);

        assertParseSuccess(parser, "1 " + PREFIX_TAG + TAG_NAME_1,
                new RemoveTagCommand(INDEX_FIRST_PERSON, editPersonDescriptor));
    }

    @Test
    public void parse_validArgsMultipleTags_returnsRemoveTagCommand() {
        Set<Tag> tagsToRemoved = new HashSet<>();
        tagsToRemoved.add(new Tag(TAG_NAME_1));
        tagsToRemoved.add(new Tag(TAG_NAME_2));

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setTags(tagsToRemoved);

        assertParseSuccess(parser, "1 " + PREFIX_TAG + TAG_NAME_1 + " " + PREFIX_TAG + TAG_NAME_2,
                new RemoveTagCommand(INDEX_FIRST_PERSON, editPersonDescriptor));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no tags
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));

        // no index
        assertParseFailure(parser, "t/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));

        // 0 index
        assertParseFailure(parser, "0 t/friends",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
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
###### /java/seedu/address/logic/commands/SplitCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SplitCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executes_splitBalance() throws Exception {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);

        double expectedBalance1 = model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()).getMoney().toDouble() + 50.0;
        double expectedBalance2 = model.getFilteredPersonList()
                .get(INDEX_SECOND_PERSON.getZeroBased()).getMoney().toDouble() + 50.0;

        SplitCommand splitCommand = new SplitCommand(indices, 100.0);
        splitCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        splitCommand.execute();

        assertEquals(expectedBalance1,
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getMoney().toDouble(), 0.001);

        assertEquals(expectedBalance2,
                model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()).getMoney().toDouble(), 0.001);
    }
}
```
###### /java/seedu/address/model/item/ItemTest.java
``` java
package seedu.address.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ItemTest {

    private static final String VALID_ITEM_NAME_1 = "Taxi Fare Split";
    private static final String VALID_ITEM_NAME_2 = "Taxi_Fare_Split";
    private static final String VALID_ITEM_NAME_3 = "Taxi  Fare  Split";
    private static final String VALID_ITEM_NAME_4 = "Taxi__Fare__Split";
    private static final String INVALID_ITEM_NAME = "";
    private static final String INVALID_ITEM_VALUE_1 = "";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Item(null, null));
    }

    @Test
    public void constructor_invalidItemName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Item(INVALID_ITEM_NAME, INVALID_ITEM_VALUE_1));
    }

    @Test
    public void isValidItemName() throws Exception {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Item.isValidItemName(null));

        // invalid name
        assertFalse(Item.isValidItemName("")); // empty string
        assertFalse(Item.isValidItemName("^")); // only non-alphanumeric characters
        assertFalse(Item.isValidItemName("item*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Item.isValidItemName("someitemname")); // alphabets only
        assertTrue(Item.isValidItemName("12345")); // numbers only
        assertTrue(Item.isValidItemName("some item name 123456")); // alphanumeric characters
        assertTrue(Item.isValidItemName("Some Item Name")); // with capital letters
        assertTrue(Item.isValidItemName(VALID_ITEM_NAME_1)); // separated by space
        assertTrue(Item.isValidItemName(VALID_ITEM_NAME_2)); // separated by _
        assertTrue(Item.isValidItemName(VALID_ITEM_NAME_3)); // separated by two spaces
        assertTrue(Item.isValidItemName(VALID_ITEM_NAME_4)); // separated by __
    }

    @Test
    public void isValidItemValue() throws Exception {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Item.isValidItemValue(null));

        // valid item value
        assertTrue(Item.isValidItemValue("0")); // numbers only
        assertTrue(Item.isValidItemValue("123456")); // multiple digits
        assertTrue(Item.isValidItemValue("10.2345")); // with multiple decimal places
        assertTrue(Item.isValidItemValue("12345678978978987978987987987987")); // long digits
        assertTrue(Item.isValidItemValue("123456.123E8")); // scientific representation of floating point numbers

        // invalid item value
        assertFalse(Item.isValidItemValue(""));
        assertFalse(Item.isValidItemValue("10."));
        assertFalse(Item.isValidItemValue("123E8E6"));
        assertFalse(Item.isValidItemValue("10k"));
    }

}
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
###### /java/seedu/address/testutil/TypicalIndexes.java
``` java
    public static final Index INDEX_FIRST_ITEM = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_ITEM = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_ITEM = Index.fromOneBased(3);
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
