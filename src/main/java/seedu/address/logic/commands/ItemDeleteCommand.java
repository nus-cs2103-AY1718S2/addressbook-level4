//@@author chenchongsong
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
