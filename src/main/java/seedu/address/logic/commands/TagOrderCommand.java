//@@author ZhangYijiong
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Halal;
import seedu.address.model.person.Name;
import seedu.address.model.person.Order;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vegetarian;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Tag an existing order in the order queue with given word
 */

public class TagOrderCommand extends Command {
    public static final String COMMAND_WORD = "tag";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Tags the order identified by the index number with given tag\n"
            + "Parameters: String (description)\n"
            + "Example: " + COMMAND_WORD + " Delivering";

    public static final String MESSAGE_TAGGED_ORDER_SUCCESS = "Order tagged: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This order already exists in the processing queue";
    public static final String MESSAGE_ONE_TAG_ONLY = "Please enter one tag at a time";

    private Index targetIndex;
    private String tagWord;

    /**
     * @param index of the order in the filtered order list to edit
     * @param tagWord word the user wants to tag on the order
     */
    public TagOrderCommand(Index index, String tagWord) {
        requireNonNull(index);
        requireNonNull(tagWord);

        this.targetIndex = index;
        this.tagWord = tagWord;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        // labels person with tag "Processing"
        Person editedPerson = createNewTaggedPerson(personToEdit, tagWord);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_TAGGED_ORDER_SUCCESS, editedPerson));
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagOrderCommand // instanceof handles nulls
                && tagWord.equals(((TagOrderCommand) other).tagWord))
                && targetIndex.equals(((TagOrderCommand) other).targetIndex);
    }

    /**
     * @param personToEdit
     * @param tag word to be tagged on the person
     * @return a updated person with tag attached
     */
    protected Person createNewTaggedPerson(Person personToEdit, String tag) {
        assert personToEdit != null;

        Address updatedAddress = personToEdit.getAddress();
        Halal updatedHalal = personToEdit.getHalal();
        Vegetarian updatedVegetarian = personToEdit.getVegetarian();
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Order updatedOrder = personToEdit.getOrder();
        UniqueTagList updatedTags = new UniqueTagList(personToEdit.getTags());

        try {
            updatedTags.add(new Tag(tag));
        } catch (UniqueTagList.DuplicateTagException dte) {
            //does not add tag "processing" if already exists
        }
        return new Person(updatedName, updatedPhone, updatedOrder, updatedAddress,
                updatedHalal, updatedVegetarian, updatedTags);
    }
}

