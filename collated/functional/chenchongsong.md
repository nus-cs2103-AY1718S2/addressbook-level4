# chenchongsong
###### /java/seedu/address/logic/parser/ItemAddCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ItemAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ItemAddCommand object
 */
public class ItemAddCommandParser implements Parser<ItemAddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ItemAddCommand
     * and returns an ItemAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ItemAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MONEY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MONEY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemAddCommand.MESSAGE_USAGE));
        }

        try {
            return new ItemAddCommand(index,
                    argMultimap.getValue(PREFIX_NAME).get(), argMultimap.getValue(PREFIX_MONEY).get());
        } catch (IllegalArgumentException iae) {
            throw new ParseException(ItemAddCommand.MESSAGE_INVALID_ARGUMENT);
        }
    }

}
```
###### /java/seedu/address/logic/parser/ItemDeleteCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ItemDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ItemDeleteCommand object.
 * {@code indexPerson} represents the index of the person whose item the user want to delete.
 * {@code indexItem} represents the index of the item that the user want to delete.
 */
public class ItemDeleteCommandParser implements Parser<ItemDeleteCommand> {

    private Index indexPerson;
    private Index indexItem;

    /**
     * Parses the given {@code String} of arguments in the context of the ItemDeleteCommand
     * and returns an ItemDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ItemDeleteCommand parse(String args) throws ParseException {
        try {
            parsePersonItemIndices(args);
            return new ItemDeleteCommand(indexPerson, indexItem);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemDeleteCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of two indices into indexPerson and indexItem.
     * @param args A string of two indices separated by a whitespace
     * @throws IllegalValueException
     */
    private void parsePersonItemIndices(String args) throws IllegalValueException {
        String[] indices = args.trim().split(" ");
        if (indices.length != 2) {
            throw new IllegalValueException(ParserUtil.MESSAGE_INVALID_INDEX);
        }
        indexPerson = ParserUtil.parseIndex(indices[0]);
        indexItem = ParserUtil.parseIndex(indices[1]);
    }

}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case RemoveTagCommand.COMMAND_WORD:
            return new RemoveTagCommandParser().parse(arguments);

        case RemoveTagCommand.COMMAND_SHORTCUT:
            return new RemoveTagCommandParser().parse(arguments);

        case SplitCommand.COMMAND_WORD:
            return new SplitCommandParser().parse(arguments);

        case SplitCommand.COMMAND_SHORTCUT:
            return new SplitCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case SortCommand.COMMAND_SHORTCUT:
            return new SortCommandParser().parse(arguments);

        case ItemShowCommand.COMMAND_WORD:
            return new ItemShowCommandParser().parse(arguments);

        case ItemShowCommand.COMMAND_SHORTCUT:
            return new ItemShowCommandParser().parse(arguments);

        case ItemAddCommand.COMMAND_WORD:
            return new ItemAddCommandParser().parse(arguments);

        case ItemAddCommand.COMMAND_SHORTCUT:
            return new ItemAddCommandParser().parse(arguments);

        case ItemDeleteCommand.COMMAND_WORD:
            return new ItemDeleteCommandParser().parse(arguments);

        case ItemDeleteCommand.COMMAND_SHORTCUT:
            return new ItemDeleteCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses {@code oneBasedIndices} into an ArrayList of {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static ArrayList<Index> parseIndices(String oneBasedIndices) throws IllegalValueException {
        String[] splittedIndices = oneBasedIndices.trim().split(" ");
        ArrayList<Index> indices = new ArrayList<>();
        for (String indexString : splittedIndices) {
            if (!StringUtil.isNonZeroUnsignedInteger(indexString)) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }
            indices.add(Index.fromOneBased(Integer.parseInt(indexString)));
        }
        return indices;
    }

    /**
     * Parses {@code args} into an {@code sortKey} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified SortKey is invalid (not non-zero unsigned integer).
     */
    public static String parseSortKey(String args) throws IllegalValueException {
        String[] splittedArgs = args.trim().split("/");
        String sortKey = splittedArgs[0] + "/";
        if (splittedArgs.length != 2) {
            throw new IllegalValueException(MESSAGE_INVALID_ARGS);
        }
        if (!sortKey.equals(PREFIX_NAME.getPrefix())
                && !sortKey.equals(PREFIX_PHONE.getPrefix())
                && !sortKey.equals(PREFIX_EMAIL.getPrefix())
                && !sortKey.equals(PREFIX_ADDRESS.getPrefix())
                && !sortKey.equals(PREFIX_TAG.getPrefix())
                && !sortKey.equals(PREFIX_MONEY.getPrefix())) {
            throw new IllegalValueException(MESSAGE_INVALID_ARGS);
        }
        return sortKey;
    }

    /**
     * Parses {@code args} into an {@code sortOrder} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified SortOrder is invalid (not non-zero unsigned integer).
     */
    public static String parseSortOrder(String args) throws IllegalValueException {
        String[] splittedArgs = args.trim().split("/");
        String sortKey = splittedArgs[1];
        if (splittedArgs.length != 2) {
            throw new IllegalValueException(MESSAGE_INVALID_ARGS);
        }
        if ((!sortKey.equals(SORT_ORDER_ASCENDING) && !sortKey.equals(SORT_ORDER_DESCENDING))) {
            throw new IllegalValueException(MESSAGE_INVALID_ARGS);
        }
        return sortKey;
    }
```
###### /java/seedu/address/logic/parser/ItemShowCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ItemShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ItemShowCommand object
 */
public class ItemShowCommandParser implements Parser<ItemShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ItemShowCommand
     * and returns an ItemShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ItemShowCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ItemShowCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ItemShowCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/RemoveTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            parseTagsForRemove(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new RemoveTagCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForRemove(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
```
###### /java/seedu/address/logic/parser/SplitCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;

import java.util.ArrayList;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SplitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SplitCommand object
 */
public class SplitCommandParser implements Parser<SplitCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SplitCommand
     * and returns a SplitCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SplitCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MONEY);

        ArrayList<Index> indices;
        try {
            indices = ParserUtil.parseIndices(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));
        }

        double bill;
        try {
            Optional<String> money = argMultimap.getValue(PREFIX_MONEY);
            bill = Double.parseDouble(money.get());
        } catch (Exception e) {
            throw new ParseException("A correct number(money) needs to be provided for the Bill!");
        }

        return new SplitCommand(indices, bill);
    }

}
```
###### /java/seedu/address/logic/parser/SortCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            String sortKey = ParserUtil.parseSortKey(args);
            String sortOrder = ParserUtil.parseSortOrder(args); // either "asc" or "desc"
            return new SortCommand(sortKey, sortOrder);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/commands/ItemAddCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.item.Item;
import seedu.address.model.money.Money;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Attach a new item to a specified person
 */
public class ItemAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "itemadd";
    public static final String COMMAND_SHORTCUT = "ia";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Attaching a new item to a specified person. "
            + "Parameters: INDEX "
            + PREFIX_NAME + "ITEM_NAME "
            + PREFIX_MONEY + "MONEY\n"
            + "Example: " + COMMAND_WORD + " 2 "
            + PREFIX_NAME + "taxiFare "
            + PREFIX_MONEY + "30\n";

    public static final String MESSAGE_ADD_ITEM_SUCCESS = "Item Added for Person %1$s.\n"
            + "To view all items, use \"itemshow\" command!";
    public static final String MESSAGE_INVALID_ARGUMENT = "The Argument is Invalid!";

    private final Index targetIndex;
    private final Item item;
    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of person in the filtered person list to whom a new item will be attached
     */
    public ItemAddCommand(Index index, String itemName, String itemValue) {
        requireNonNull(index);
        this.targetIndex = index;
        this.item = new Item(itemName, itemValue);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target person cannot be duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_ITEM_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToEdit = lastShownList.get(targetIndex.getZeroBased());
        editedPerson = getEditedPerson(personToEdit);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code person}
     * but with a updated item list
     */
    private Person getEditedPerson(Person person) {
        assert person != null;

        // references the original objects
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();
        Money money = person.getMoney();
        Set<Tag> tags = person.getTags();
        ArrayList<Item> items = getAppendedItemList(person.getItems());

        // returns a new Person based mainly on references to original information
        return new Person(name, phone, email, address, money, tags, items);
    }

    /**
     * Create and returns an updated Item List
     * The new item would be appended to the end of the original Item List
     */
    private ArrayList<Item> getAppendedItemList(ArrayList<Item> items) {
        assert items != null;
        ArrayList<Item> appendedItemList = new ArrayList<>(items);
        appendedItemList.add(this.item);
        return appendedItemList;
    }

}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
package seedu.address.logic.commands;

/**
 * Sort all persons in address book in order.
 * Keywords will be given by user through arguments.
 * Both ascending and descending order is supported.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_SHORTCUT = "so";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons in ascendingly or descendingly, "
            + "ordering by the specified keywords.\n"
            + "Parameters: KEYWORD_PREFIX/ORDER ...\n"
            + "Example1: " + COMMAND_WORD + " n/desc\n"
            + "Example2: " + COMMAND_WORD + " m/asc";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    public static final String SORT_ORDER_ASCENDING = "asc";
    public static final String SORT_ORDER_DESCENDING = "desc";

    public final String sortKey;
    public final String sortOrder;

    public SortCommand(String key, String order) {
        this.sortKey = key;
        this.sortOrder = order;
    }

    @Override
    public CommandResult execute() {
        model.sortUniquePersonList(sortKey, sortOrder);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/ItemDeleteCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.item.Item;
import seedu.address.model.money.Money;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Delete an item from a specified person
 */
public class ItemDeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "itemdelete";
    public static final String COMMAND_SHORTCUT = "id";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deleting an item from a specified person. "
            + "Parameters: PERSON_INDEX ITEM_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 2\n"
            + "This example command deletes the second item from the first person.\n";

    public static final String MESSAGE_ADD_ITEM_SUCCESS = "Items Deleted for Person %1$s.\n";
    public static final String MESSAGE_INVALID_ARGUMENT = "The Argument is Invalid!";

    private final Index indexPerson;
    private final Index indexItem;
    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param indexPerson The index of person in the filtered person list whose item the user wants to delete
     * @param indexItem The index of item the user wants to delete
     */
    public ItemDeleteCommand(Index indexPerson, Index indexItem) {
        requireNonNull(indexPerson);
        requireNonNull(indexItem);
        this.indexPerson = indexPerson;
        this.indexItem = indexItem;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target person cannot be duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_ITEM_SUCCESS, indexPerson.getOneBased()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (indexPerson.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        personToEdit = lastShownList.get(indexPerson.getZeroBased());
        editedPerson = getEditedPerson(personToEdit);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code person}
     * but with an updated item list
     */
    private Person getEditedPerson(Person person) {
        assert person != null;

        // references the original objects
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Address address = person.getAddress();
        Money money = person.getMoney();
        Set<Tag> tags = person.getTags();
        ArrayList<Item> items = getItemRemovedItemList(person.getItems());

        // returns a new Person based mainly on references to original information, but with an updated item list
        return new Person(name, phone, email, address, money, tags, items);
    }

    /**
     * Create and returns an updated Item List
     * where the target Item will be removed
     */
    private ArrayList<Item> getItemRemovedItemList(ArrayList<Item> items) {
        assert items != null;
        ArrayList<Item> itemRemovedItemList = new ArrayList<>(items);
        itemRemovedItemList.remove(indexItem.getZeroBased());
        return itemRemovedItemList;
    }

}
```
###### /java/seedu/address/logic/commands/ItemShowCommand.java
``` java
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.person.Person;

/**
 * Show all items related to a specified person.
 * The person is identified using it's last displayed index from the TravleBanker.
 */
public class ItemShowCommand extends Command {

    public static final String COMMAND_WORD = "itemshow";
    public static final String COMMAND_SHORTCUT = "is";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Show all items related to a person (specified by INDEX).\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHOW_ITEM_SUCCESS = "Items Showed for Person: %d.\n"
            + "Money Due to Unknown Items: %.2f\n";

    private final Index targetIndex;

    public ItemShowCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(targetIndex.getZeroBased());
        UniqueItemList items = targetPerson.getUniqueItemList();
        Double reasonUnknownAmount = targetPerson.getReasonUnknownAmount();
        return new CommandResult(getResultString(items, reasonUnknownAmount));
    }

    private String getResultString(UniqueItemList items, Double reasonUnknownAmount) {
        return String.format(MESSAGE_SHOW_ITEM_SUCCESS, targetIndex.getOneBased(), reasonUnknownAmount)
                + items.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemShowCommand // instanceof handles nulls
                && this.targetIndex.equals(((ItemShowCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/SplitCommand.java
``` java
package seedu.address.logic.commands;

import static java.lang.Math.round;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.money.Money;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Split a bill evenly among selected people.
 */
public class SplitCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "split";
    public static final String COMMAND_SHORTCUT = "sp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Split a bill evenly among specified people. "
            + "Parameters: INDEX1 [INDEX2...] "
            + PREFIX_MONEY + "MONEY\n"
            + "Example1: " + COMMAND_WORD + " 1 2 "
            + PREFIX_MONEY + "200\n"
            + "Example2: " + COMMAND_SHORTCUT + " 1 2 3 "
            + PREFIX_MONEY + "400.00";

    public static final String MESSAGE_SPLIT_BILL_SUCCESS = "Bill Split Successfully Among Selected People!";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate Indices Found in Parameters!";

    private final ArrayList<Index> indices;
    private ArrayList<Person> peopleToEdit;
    private ArrayList<Person> editedPeople;
    private final double bill;

    /**
     * @param indices of people in the filtered person list to settle the bill
     */
    public SplitCommand(ArrayList<Index> indices, double bill) {
        requireNonNull(indices);
        this.indices = indices;
        peopleToEdit = new ArrayList<>();
        editedPeople = new ArrayList<>();
        this.bill = bill;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            for (int i = 0; i < indices.size(); i++) {
                model.updatePerson(peopleToEdit.get(i), editedPeople.get(i));
            }
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target people cannot be duplicate");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target people cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SPLIT_BILL_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {

        if (!CollectionUtil.elementsAreUnique(indices.stream()
                .map(Index->Index.getZeroBased())
                .collect(Collectors.toList()))) {
            throw new CommandException(MESSAGE_DUPLICATE_INDEX);
        }

        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index index : indices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person person = lastShownList.get(index.getZeroBased());
            peopleToEdit.add(person);
            editedPeople.add(getSettledPerson(person));
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * but with a updated balance
     */
    private Person getSettledPerson(Person personToEdit) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Money money = getSettledMoney(personToEdit.getMoney());
        Set<Tag> tags = personToEdit.getTags();

        return new Person(name, phone, email, address, money, tags);
    }

    /**
     * Create and returns an updated Money Info
     * The updated Money would be rounded to 2 decimal places
     */
    private Money getSettledMoney(Money moneyToEdit) {
        assert moneyToEdit != null;
        double updatedBalance = moneyToEdit.toDouble() + bill / indices.size();
        updatedBalance = round(updatedBalance * 100.00) / 100.00;
        return new Money(Double.toString(updatedBalance));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SplitCommand)) {
            return false;
        }

        // state check
        SplitCommand o = (SplitCommand) other;

        return bill == o.bill
                && indices.equals(o.indices);
    }

}
```
###### /java/seedu/address/logic/commands/RemoveTagCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.money.Money;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";
    public static final String COMMAND_SHORTCUT = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the specific tags of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "owesMoney "
            + PREFIX_TAG + "friends";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Remove Tags for Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_TAG_NOT_EXIST = "Your Input Contains Non-existent Tag(s)!";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public RemoveTagCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        try {
            personToEdit = lastShownList.get(index.getZeroBased());
            editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
        } catch (IllegalArgumentException iae) {
            throw new CommandException(MESSAGE_TAG_NOT_EXIST);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * Remove all tags within {@code editPersonDescriptor} from the original tag list of personToEdit
     */
    public static Person createEditedPerson(
            Person personToEdit, EditPersonDescriptor toBeRemovedTagsDescriptor) throws IllegalArgumentException {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Money updatedMoney = personToEdit.getMoney();

        Set<Tag> toBeRemovedTags = toBeRemovedTagsDescriptor.getTags().orElse(personToEdit.getTags());
        Set<Tag> originalTags = personToEdit.getTags();
        checkArgument(allTagsExistOriginally(toBeRemovedTags, originalTags), MESSAGE_TAG_NOT_EXIST);

        Set<Tag> updatedTags = getUpdatedTags(toBeRemovedTags, originalTags);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedMoney, updatedTags);
    }

    public static Set<Tag> getUpdatedTags(Set<Tag> toBeRemovedTags, Set<Tag> originalTags) {
        Set<Tag> updatedTags = new HashSet<>();
        for (Tag t: originalTags) {
            if (!toBeRemovedTags.contains(t)) {
                updatedTags.add(t);
            }
        }
        return updatedTags;
    }

    /**
     * make sure all tags originally exist in the person info
     * @param toBeRemovedTags
     * @param originalTags
     * @return
     */
    private static boolean allTagsExistOriginally(Set<Tag> toBeRemovedTags, Set<Tag> originalTags) {
        for (Tag tag: toBeRemovedTags) {
            if (!originalTags.contains(tag)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }
        // state check
        RemoveTagCommand e = (RemoveTagCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }

}
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
    @XmlElement
    private List<XmlAdaptedItem> items = new ArrayList<>();
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        final List<Item> personItems = new ArrayList<>();
        for (XmlAdaptedItem item : items) {
            personItems.add(item.toModelType());
        }
```
###### /java/seedu/address/storage/XmlAdaptedPerson.java
``` java
        final ArrayList<Item> items = new ArrayList<>(personItems);
        return new Person(name, phone, email, address, balance, tags, items);
```
###### /java/seedu/address/storage/XmlAdaptedItem.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Item;

/**
 * JAXB-friendly adapted version of the Item.
 */
public class XmlAdaptedItem {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String value;

    /**
     * Constructs an XmlAdaptedItem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedItem() {}

    /**
     * Constructs a {@code XmlAdaptedItem} with the given {@code itemName}.
     */
    public XmlAdaptedItem(String itemName, String itemValue) {
        this.name = itemName;
        this.value = itemValue;
    }

    /**
     * Converts a given item into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedItem(Item source) {
        name = source.getItemName();
        value = source.getItemValue();
    }

    /**
     * Converts this jaxb-friendly adapted item object into the model's Item object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Item toModelType() throws IllegalValueException {
        if (!Item.isValidItemName(name)) {
            throw new IllegalValueException(Item.MESSAGE_ITEMNAME_CONSTRAINTS);
        }
        if (!Item.isValidItemValue(value)) {
            throw new IllegalValueException(Item.MESSAGE_ITEMVALUE_CONSTRAINTS);
        }
        return new Item(name, value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedItem)) {
            return false;
        }
        return name.equals(((XmlAdaptedItem) other).name);
    }

}
```
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Sorts {@code internalList} by keyword ascendingly or descendingly
     */
    public void sortPersons(String sortKey, String sortOrder) {
        Comparator<Person> comparator = Person.createComparator(sortKey, sortOrder);
        internalList.sort(comparator);
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    /**
     * Every field must be present and not null.
     * @param items must be provided
     */
    public Person(Name name, Phone phone, Email email, Address address, Money balance,
                  Set<Tag> tags, ArrayList<Item>items) {
        requireAllNonNull(name, phone, email, address, tags, items);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.money = balance;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.items = new UniqueItemList(items);
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    /**
     * Returns the amount of money due to unknown reasons/items
     * @return
     */
    public Double getReasonUnknownAmount() {
        return money.toDouble() - items.getValueSum();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public ArrayList<Item> getItems() {
        return items.toArrayList();
    }

    public UniqueItemList getUniqueItemList() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items.setItems(items);
    }

    public void clearItems() {
        this.items.setItems(new ArrayList<>());
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    /**
     * Create comparator for sorting person list
     * @param sortKey
     * @param sortOrder either "asc" or "desc"
     * @return comparator
     */
    public static Comparator<Person> createComparator(String sortKey, String sortOrder) {

        Comparator<Person> comparator;
        if (sortKey.equals(PREFIX_NAME.getPrefix())) {
            comparator = (person1, person2) -> +person1.getName().compareTo(person2.getName());
        } else if (sortKey.equals(PREFIX_PHONE.getPrefix())) {
            comparator = (person1, person2) -> +person1.getPhone().compareTo(person2.getPhone());
        } else if (sortKey.equals(PREFIX_EMAIL.getPrefix())) {
            comparator = (person1, person2) -> +person1.getEmail().compareTo(person2.getEmail());
        } else if (sortKey.equals(PREFIX_ADDRESS.getPrefix())) {
            comparator = (person1, person2) -> +person1.getAddress().compareTo(person2.getAddress());
        } else if (sortKey.equals(PREFIX_MONEY.getPrefix())) {
            comparator = (person1, person2) -> +person1.getMoney().compareTo(person2.getMoney());
        } else if (sortKey.equals(PREFIX_TAG.getPrefix())) {
            comparator = (person1, person2) -> +Integer.compare(person1.getTags().size(), person2.getTags().size());
        } else {
            // sort name by default
            comparator = (person1, person2) -> +person1.getName().toString()
                    .compareToIgnoreCase(person2.getName().toString());
        }

        if (sortOrder.equals(SORT_ORDER_DESCENDING)) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public void sortPersons(String sortKey, String sortOrder) {
        persons.sortPersons(sortKey, sortOrder);
    }
```
###### /java/seedu/address/model/item/UniqueItemList.java
``` java
package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of items that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 */
public class UniqueItemList implements Iterable<Item> {

    private ArrayList<Item> internalList = new ArrayList<>();

    /**
     * Constructs empty ItemList.
     */
    public UniqueItemList() {}

    /**
     * Creates a UniqueItemList using given items.
     * Enforces no nulls.
     */
    public UniqueItemList(ArrayList<Item> items) {
        requireAllNonNull(items);
        internalList.addAll(items);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all items in this list as a ArrayList.
     * This ArrayList is mutable and change-insulated against the internal list.
     */
    public ArrayList<Item> toArrayList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new ArrayList<>(internalList);
    }

    /**
     * Returns all items in this list as a String.
     */
    public String toString() {
        assert CollectionUtil.elementsAreUnique(internalList);
        final StringBuilder builder = new StringBuilder();
        builder.append("Items: ");
        for (int i = 0; i < internalList.size(); i++) {
            builder.append("\nItem No." + Integer.toString(i + 1) + "    ||    ");
            builder.append(internalList.get(i).toString());
        }
        return builder.toString();
    }

    /**
     * Replaces the Items in this list with those in the argument Item list.
     */
    public void setItems(ArrayList<Item> newItemList) {
        requireAllNonNull(newItemList);
        internalList = new ArrayList<>(newItemList);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every item in the argument list exists in this object.
     */
    public void mergeFrom(UniqueItemList from) {
        final ArrayList<Item> alreadyInside = this.toArrayList();
        from.internalList.stream()
                .filter(item -> !alreadyInside.contains(item))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Item as the given argument.
     */
    public boolean contains(Item toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Item to the list.
     *
     * @throws DuplicateItemException if the Item to add is a duplicate of an existing Item in the list.
     */
    public void add(Item toAdd) throws DuplicateItemException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Item> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueItemList // instanceof handles nulls
                && this.internalList.equals(((UniqueItemList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueItemList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * returns the sum of all items in the internalList
     * @return
     */
    public double getValueSum() {
        double sum = 0.0;
        for (Item item: internalList) {
            sum += Double.parseDouble(item.getItemValue());
        }
        return sum;
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateItemException extends DuplicateDataException {
        protected DuplicateItemException() {
            super("Operation would result in duplicate items");
        }
    }

}
```
###### /java/seedu/address/model/item/Item.java
``` java
package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Item in the TravelBanker.
 * Guarantees: immutable; name is valid as declared in {@link #isValidItemName(String)}
 */
public class Item {

    public static final String MESSAGE_ITEMNAME_CONSTRAINTS = "ItemNames should be alphanumeric with whitespaces or _";
    public static final String MESSAGE_ITEMVALUE_CONSTRAINTS = "Item Values should be a floating point number!";
    public static final String ITEM_NAME_VALIDATION_REGEX = "[\\p{Alnum}_\\s]+";
    public static final String ITEM_VALUE_VALIDATION_REGEX = "-?\\d+(\\.\\d+)?(E-?\\d+)?";

    private final String itemName;
    private final String itemValue;

    /**
     * Constructs a {@code Item}.
     *
     * @param itemName A valid item name.
     */
    public Item(String itemName, String itemValue) {
        requireNonNull(itemName);
        requireNonNull(itemValue);
        checkArgument(isValidItemName(itemName), MESSAGE_ITEMNAME_CONSTRAINTS);
        checkArgument(isValidItemValue(itemValue), MESSAGE_ITEMVALUE_CONSTRAINTS);
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    /**
     * Returns true if a given string is a valid item name.
     */
    public static boolean isValidItemName(String test) {
        return test.matches(ITEM_NAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid item value.
     */
    public static boolean isValidItemValue(String test) {
        return test.matches(ITEM_VALUE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Item // instanceof handles nulls
                && this.itemName.equals(((Item) other).itemName)
                && this.itemValue.equals(((Item) other).itemValue)); // state check
    }

    @Override
    public int hashCode() {
        return itemName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "ItemName  [ " + itemName + " ]    ||    ItemValue [ " + itemValue + " ]";
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void sortUniquePersonList(String sortKey, String sortOrder) {
        addressBook.sortPersons(sortKey, sortOrder);
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Sorts the person list by a given keyword {}
     * The person list would be sorted ascendingly or descendingly, depending on {}
     */
    void sortUniquePersonList(String sortKey, String sortOrder);
```
