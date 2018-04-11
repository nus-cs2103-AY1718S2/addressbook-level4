//@@author chenchongsong
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
